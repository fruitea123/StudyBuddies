package use_case.make_invitation;

import entity.Invitation;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;

public class MakeInvitationInteractor implements MakeInvitationInputBoundary {

    private final MakeInvitationDataAccessInterface invitationDAO;
    private final MakeInvitationOutputBoundary userPresenter;

    public MakeInvitationInteractor(MakeInvitationDataAccessInterface invitationDAO,
                                    MakeInvitationOutputBoundary userPresenter) {
        this.invitationDAO = invitationDAO;
        this.userPresenter = userPresenter;
    }

    @Override
    public void execute(MakeInvitationInputData r) {
        try {
            if (r.getCourse() == null || r.getCourse().isBlank())
                throw new IllegalArgumentException("Course required");
            LocalDate date = r.getDate();
            LocalTime start = r.getStartTime();
            LocalTime end = r.getEndTime();
            if (date == null) throw new IllegalArgumentException("Date required");

            // check valid date/time
            ZoneId zone = ZoneId.systemDefault();
            LocalDate today = LocalDate.now(zone);
            if (date.isBefore(today)) {
                throw new IllegalArgumentException("Date cannot be in the past");
            }
            if (date.equals(today) && start.isBefore(LocalTime.now(zone))) {
                throw new IllegalArgumentException("Start time cannot be in the past");
            }
            if (start == null || end == null) {
                throw new IllegalArgumentException("Start/End required");
            }
            if (!start.isBefore(end)){
                throw new IllegalArgumentException("Start must be before end");
            }

            // occupancy convert to capacity if needed
            int capacity = 2;
            if (r.getOccupancy() != null){
                capacity = r.getOccupancy().intValue();
            }
            if (capacity <= 2){
                throw new IllegalArgumentException("Capacity must be greater than 2");
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