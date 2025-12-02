package interface_adapter.myinvitations;

import entity.Invitation;
import interface_adapter.make_invitation.MakeInvitationState;
import interface_adapter.make_invitation.MakeInvitationViewModel;
import use_case.calendar.InsertUserStudySessionsInteractor;
import use_case.cancel.CancelInvitation;
import use_case.myinvitations.MyInvitations;
import view.MakeInvitationView;

import java.util.List;

/**
 * Controller for the MyInvitations screen.
 * Talks to the use case and presenter, but not to Swing directly.
 */
public class MyInvitationsController {

    private final MyInvitations interactor;
    private final MyInvitationsPresenter presenter;
    private final CancelInvitation cancelUseCase;
    private final String currentUserEmail;
    private final InsertUserStudySessionsInteractor calendarUseCase;
    private final MakeInvitationViewModel viewModel;


    public MyInvitationsController(MyInvitations interactor,
                                   MyInvitationsPresenter presenter,
                                   String currentUserEmail,
                                   CancelInvitation cancelUseCase,
                                   InsertUserStudySessionsInteractor calendarUseCase, MakeInvitationViewModel viewModel) {
        this.interactor = interactor;
        this.presenter = presenter;
        this.currentUserEmail = currentUserEmail;
        this.cancelUseCase = cancelUseCase;
        this.calendarUseCase = calendarUseCase;
        this.viewModel = viewModel;
    }


    public void refresh() {
        var owned = interactor.FilterByOwned(currentUserEmail);
        var participating = interactor.FilterByParticipant(currentUserEmail);
        presenter.present(owned, participating);
    }

    public void onLeaveClicked(String invitationId) {
        System.out.println("[MyInvitationsController] Leaving invitation: " + invitationId);

        cancelUseCase.setUsername(currentUserEmail);
        cancelUseCase.leave();

        refresh();
    }

    public void onDeleteClicked(String invitationId) {
        System.out.println("[MyInvitationsController] Deleting invitation: " + invitationId);

        cancelUseCase.setOwnerName(currentUserEmail);
        cancelUseCase.delete();

        refresh();
    }

    public void onCreateInvitation() {
        System.out.println("Navigate: Create Invitation");
    }

    public void onCalendarClicked() {
        System.out.println("[MyInvitationsController] Calendar button clicked");

        try {
            calendarUseCase.execute(currentUserEmail);
            System.out.println("Study sessions inserted into calendar for user: " + currentUserEmail);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Calendar operation failed: " + e.getMessage());
        }
    }

    public void onStudyPoolClicked() {
        System.out.println("Navigate: Study Pool");
        // TODO: viewManager.goTo("study_pool")
    }

    public void onProfileClicked() {
        System.out.println("Navigate: Profile");
        // TODO: viewManager.goTo("profile")
    }
}
