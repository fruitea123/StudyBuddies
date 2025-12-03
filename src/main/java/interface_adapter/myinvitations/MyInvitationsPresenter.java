package interface_adapter.myinvitations;

import entity.Invitation;
import use_case.myinvitations.MyInvitationsOutputBoundary;
import use_case.myinvitations.MyInvitationsOutputData;
import view.forms.MyInvitationsView;

import java.util.ArrayList;
import java.util.List;

/**
 * Presenter for the MyInvitations use case.
 * Converts Invitation entities into InvitationItemViewModel objects
 * and updates the Swing view.
 */
public class MyInvitationsPresenter implements MyInvitationsOutputBoundary {

    private final MyInvitationsViewModel viewModel;
    private final MyInvitationsView view;

    public MyInvitationsPresenter(MyInvitationsViewModel viewModel,
                                  MyInvitationsView view) {
        this.viewModel = viewModel;
        this.view = view;
    }

    @Override
    public void presentMyInvitations(MyInvitationsOutputData myInvitationsOutputData) {

        List<Invitation> owned = myInvitationsOutputData.getOwned();
        List<Invitation> participating = myInvitationsOutputData.getParticipating()

        List<InvitationItemViewModel> ownedVM = new ArrayList<>();
        for (Invitation inv : owned) {
            InvitationItemViewModel item = new InvitationItemViewModel();
            item.setInvitationId(inv.getInvitationID());
            item.setTitle(inv.getCourse());
            item.setDescription(inv.getDescription());
            item.setOwned(true);
            ownedVM.add(item);
        }

        List<InvitationItemViewModel> partVM = new ArrayList<>();
        for (Invitation inv : participating) {
            InvitationItemViewModel item = new InvitationItemViewModel();
            item.setInvitationId(inv.getInvitationID());
            item.setTitle(inv.getCourse());
            item.setDescription(inv.getDescription());
            item.setOwned(false);
            partVM.add(item);
        }

        viewModel.setOwnedInvitations(ownedVM);
        viewModel.setParticipatingInvitations(partVM);

        // Push the new state into the view
        view.update(viewModel);
    }
}
