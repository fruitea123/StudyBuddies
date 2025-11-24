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
            // 1. DAO：用内存实现
            InMemoryInvitationDataAccessObject invitationDAO =
                    new InMemoryInvitationDataAccessObject();

            // 2. 当前用户 gateway，并设置一个假用户
            SessionCurrentUserGateway currentUserGateway =
                    new SessionCurrentUserGateway();
            currentUserGateway.setCurrentUser(new User("alice", "password"));

            // 3. ViewModel
            MakeInvitationViewModel vm = new MakeInvitationViewModel();

            // 4. Presenter
            MakeInvitationOutputBoundary presenter =
                    new MakeInvitationPresenter(vm);

            // 5. Interactor
            MakeInvitationInputBoundary interactor =
                    new MakeInvitationInteractor(
                            invitationDAO,
                            presenter,
                            currentUserGateway);

            // 6. Controller
            MakeInvitationController controller =
                    new MakeInvitationController(interactor);

            // 7. View（注意：构造函数只传 vm）
            MakeInvitationView view = new MakeInvitationView(vm);
            // 再单独把 controller 塞进去（和 SignupView 一样的模式）
            view.setMakeInvitationController(controller);

            // 8. 放进 JFrame 里展示出来
            JFrame frame = new JFrame("Make Invitation Demo");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(view);
            frame.pack();
            frame.setLocationRelativeTo(null); // 居中
            frame.setVisible(true);
        });
    }
}