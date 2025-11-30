package app;

import data_access.InMemoryInvitationDataAccessObject;
import entity.User;
import interface_adapter.make_invitation.MakeInvitationController;
import interface_adapter.make_invitation.MakeInvitationPresenter;
import interface_adapter.make_invitation.MakeInvitationViewModel;
import interface_adapter.make_invitation.SessionCurrentUserGateway;
import use_case.make_invitation.*;
import view.MakeInvitationView;

import javax.swing.*;

public class MakeInvitationDemoMain {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            InMemoryInvitationDataAccessObject invitationDAO =
                    new InMemoryInvitationDataAccessObject();

            SessionCurrentUserGateway currentUserGateway =
                    new SessionCurrentUserGateway();
            currentUserGateway.setCurrentUser(new User("alice", "password"));

            MakeInvitationViewModel vm = new MakeInvitationViewModel();

            MakeInvitationOutputBoundary presenter =
                    new MakeInvitationPresenter(vm);

            MakeInvitationInputBoundary interactor =
                    new MakeInvitationInteractor(
                            invitationDAO,
                            presenter,
                            currentUserGateway);

            MakeInvitationController controller =
                    new MakeInvitationController(interactor);

            MakeInvitationView view = new MakeInvitationView(vm);
            view.setMakeInvitationController(controller);

            JFrame frame = new JFrame("Make Invitation");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(view);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}