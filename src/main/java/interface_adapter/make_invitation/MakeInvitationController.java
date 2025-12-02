package interface_adapter.make_invitation;

import usecase.make_invitation.MakeInvitationInputBoundary;
import usecase.make_invitation.MakeInvitationInputData;

import java.time.LocalDate;
import java.time.LocalTime;


public class MakeInvitationController{
    private final MakeInvitationInputBoundary interactor;

    public MakeInvitationController(MakeInvitationInputBoundary interactor) {
        this.interactor = interactor;
    }
    public void onConfirm(String course,
                          String description,
                          LocalDate date,
                          LocalTime start,
                          LocalTime end,
                          String mode,
                          String location,
                          Integer occupancy) {
        MakeInvitationInputData req = new MakeInvitationInputData(
                course, description, date, start, end, mode, location, occupancy
        );
        interactor.execute(req);
    }
}

