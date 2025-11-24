package use_case.make_invitation;

import entity.Invitation;
import entity.User;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;

public class MakeInvitationInteractor implements MakeInvitationInputBoundary {

    private final MakeInvitationDataAccessInterface invitationDAO;
    private final MakeInvitationOutputBoundary userPresenter;
    private final CurrentUserGateway currentUser;

    public MakeInvitationInteractor(MakeInvitationDataAccessInterface invitationDAO,
                                    MakeInvitationOutputBoundary userPresenter,
                                    CurrentUserGateway currentUser) {
        this.invitationDAO = invitationDAO;
        this.userPresenter = userPresenter;
        this.currentUser = currentUser;
    }

    @Override
    public void execute(MakeInvitationInputData r) {
        try {
            LocalDate date = r.getDate();
            LocalTime start = r.getStartTime();
            LocalTime end = r.getEndTime();

            if (r.getCourse() == null || r.getCourse().isBlank())
                throw new IllegalArgumentException("Course required");

            if (r.getDescription() != null){
                String trimmed =  r.getDescription().trim();
                if (trimmed.length() > 150){
                    throw new IllegalArgumentException(
                            "Description must be at most 150 characters long");
                }
            }

            if (date == null) throw new IllegalArgumentException("Date required");

            // check valid date/time
            if (start == null || end == null) {
                throw new IllegalArgumentException("Start/End required");
            }

            if (!start.isBefore(end)){
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
            if (r.getOccupancy() != null){
                capacity = r.getOccupancy().intValue();
            }
            if (capacity <= 2){
                throw new IllegalArgumentException("Capacity must be greater than 2");
            }
            User owner = currentUser.getCurrentUser();
            if (owner == null) {
                throw new IllegalStateException("Not logged in");
            }

            if (invitationDAO.ownerHasOverlap(owner, date, start, end)) {
                throw new IllegalArgumentException("You already have another invitation in this time slot.");
            }

            Invitation inv = Invitation.builder()
                    .course(r.getCourse())
                    .description(r.getDescription())
                    .date(date)
                    .startTime(start)
                    .endTime(end)
                    .mode(r.getMode())              // "ONLINE" / "IN_PERSON"
                    .location(r.getLocation())
                    .capacity(capacity)
                    .owner(owner)
                    .build();

            // save to DB
            if (invitationDAO.existsOverlap(r.getCourse(), date, start, end)) {
                throw new IllegalArgumentException("An invitation at this time already exists.");
            }
            invitationDAO.save(inv);

            userPresenter.presentSuccess(new MakeInvitationOutputData(inv, "Invitation created."));
        } catch (Exception ex) {
            userPresenter.presentFailure(ex.getMessage());
        }
    }
}