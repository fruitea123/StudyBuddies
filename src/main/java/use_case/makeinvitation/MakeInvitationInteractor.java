package use_case.makeinvitation;

import entity.Invitation;
import entity.User;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;

/**
 * Executes the make invitation use case for the given input.
 * Validates the input, checks for overlaps and saves the invitation
 * if all conditions are satisfied.
 */
public class MakeInvitationInteractor implements MakeInvitationInputBoundary {

  private final MakeInvitationDataAccessInterface invitationDao;
  private final MakeInvitationOutputBoundary userPresenter;
  private final CurrentUserGateway currentUser;

  /**
   * Create e new make invitation interactor to execute.
   *
   * @param invitationDao data access interface for invitation
   * @param userPresenter presenter for message
   * @param currentUser owner for invitation
   */
  public MakeInvitationInteractor(MakeInvitationDataAccessInterface invitationDao,
                                  MakeInvitationOutputBoundary userPresenter,
                                  CurrentUserGateway currentUser) {
    this.invitationDao = invitationDao;
    this.userPresenter = userPresenter;
    this.currentUser = currentUser;
  }

  /**
   * Execute method for creating a new invitation.
   *
   * @param r packed input data
   */
  @Override
  public void execute(MakeInvitationInputData r) {
    try {

      if (r.getCourse() == null || r.getCourse().isBlank()) {
        throw new IllegalArgumentException("Course required");
      }

      if (r.getDescription() != null) {
        String trimmed = r.getDescription().trim();
        if (trimmed.length() > 150) {
          throw new IllegalArgumentException(
              "Description must be at most 150 characters");
        }
      }

      LocalDate date = r.getDate();
      LocalTime start = r.getStartTime();
      LocalTime end = r.getEndTime();

      if (date == null) {
        throw new IllegalArgumentException("Date required");
      }

      // check valid date/time
      if (start == null || end == null) {
        throw new IllegalArgumentException("Start/End required");
      }

      if (!start.isBefore(end)) {
        throw new IllegalArgumentException("Start must be before end");
      }

      ZoneId zone = ZoneId.systemDefault();
      LocalDate today = LocalDate.now(zone);

      if (date.isBefore(today)) {
        throw new IllegalArgumentException("Date cannot be in the past");
      }
      if (date.equals(today) && start.isBefore(LocalTime.now(zone))) {
        throw new IllegalArgumentException("Start time cannot be in the past");
      }

      // occupancy convert to capacity if needed
      int capacity = 2;
      if (r.getOccupancy() != null) {
        capacity = r.getOccupancy().intValue();
      }
      if (capacity < 2) {
        throw new IllegalArgumentException("Capacity must be at least 2");
      }
      if ("IN_PERSON".equals(r.getMode())) {
        if (r.getLocation() == null || r.getLocation().isBlank()) {
          throw new IllegalArgumentException("Location is required for in-person invitations");
        }
      }
      User owner = currentUser.getCurrentUser();
      if (owner == null) {
        throw new IllegalStateException("Not logged in");
      }

      if (invitationDao.ownerHasOverlap(owner, date, start, end)) {
        throw new IllegalArgumentException(
            "You already have another invitation in this time slot.");
      }

      Invitation inv = Invitation.builder()
          .course(r.getCourse())
          .description(r.getDescription())
          .date(date)
          .startTime(start)
          .endTime(end)
          .mode(r.getMode())
          .location(r.getLocation())
          .capacity(capacity)
          .owner(owner)
          .build();


      if (invitationDao.existsOverlap(r.getCourse(), date, start, end)) {
        throw new IllegalArgumentException("An invitation at this time already exists.");
      }
      invitationDao.save(inv);

      userPresenter.presentSuccess(new MakeInvitationOutputData(inv, "Invitation created."));
    } catch (Exception ex) {
      userPresenter.presentFailure(ex.getMessage());
    }
  }
}