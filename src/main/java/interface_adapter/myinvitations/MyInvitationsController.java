package interface_adapter.myinvitations;

import entity.Invitation;
import use_case.myinvitations.MyInvitations;

import java.util.List;

/**
 * Controller for the MyInvitations screen.
 * Talks to the use case and presenter, but not to Swing directly.
 */
public class MyInvitationsController {

    private final MyInvitations interactor;
    private final MyInvitationsPresenter presenter;
    private final String currentUserEmail;

    public MyInvitationsController(MyInvitations interactor,
                                   MyInvitationsPresenter presenter,
                                   String currentUserEmail) {
        this.interactor = interactor;
        this.presenter = presenter;
        this.currentUserEmail = currentUserEmail;
    }

    /** Load + present all invitations for the current user. */
    public void refresh() {
        List<Invitation> owned = interactor.FilterByOwned(currentUserEmail);
        List<Invitation> participating = interactor.FilterByParticipant(currentUserEmail);
        presenter.present(owned, participating);
    }

    /** Called when the user clicks "Leave" on a participating invitation. */
    public void onLeaveClicked(String invitationId) {
        System.out.println("Leave clicked for invitation: " + invitationId);
        // TODO: hook up Harish's "leave invitation" use case here.
        // e.g. leaveInvitationUseCase.execute(invitationId, currentUserEmail);
        refresh();
    }

    /** Called when the user clicks "Delete" on an owned invitation. */
    public void onDeleteClicked(String invitationId) {
        System.out.println("Delete clicked for invitation: " + invitationId);
        // TODO: hook up Harish's "delete invitation" use case here.
        // e.g. deleteInvitationUseCase.execute(invitationId);
        refresh();
    }
}
