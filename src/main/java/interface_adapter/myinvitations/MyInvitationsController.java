package interface_adapter.myinvitations;

import interface_adapter.ViewManagerModel;
import interface_adapter.makeinvitation.MakeInvitationViewModel;
import use_case.calendar.InsertUserStudySessionsInteractor;
import use_case.cancel.CancelInvitation;
import use_case.myinvitations.MyInvitations;

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
    private final ViewManagerModel viewManagerModel;


    public MyInvitationsController(MyInvitations interactor,
                                   MyInvitationsPresenter presenter,
                                   String currentUserEmail,
                                   CancelInvitation cancelUseCase,
                                   InsertUserStudySessionsInteractor calendarUseCase,
                                   ViewManagerModel viewManagerModel) {
        this.interactor = interactor;
        this.presenter = presenter;
        this.currentUserEmail = currentUserEmail;
        this.cancelUseCase = cancelUseCase;
        this.calendarUseCase = calendarUseCase;
        this.viewManagerModel = viewManagerModel;
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
        viewManagerModel.setState(MakeInvitationViewModel.VIEW_NAME);
        viewManagerModel.firePropertyChange();

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
