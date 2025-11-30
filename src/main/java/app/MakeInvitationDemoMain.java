//package app;
//
//import data_access.InMemoryInvitationDataAccessObject;
//import entity.User;
//import interface_adapter.make_invitation.MakeInvitationController;
//import interface_adapter.make_invitation.MakeInvitationPresenter;
//import interface_adapter.make_invitation.MakeInvitationViewModel;
//import interface_adapter.make_invitation.SessionCurrentUserGateway;
//import use_case.make_invitation.*;
//import view.MakeInvitationView;
//
//import javax.swing.*;
//
//public class MakeInvitationDemoMain {
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            // DAO：用内存实现
//            InMemoryInvitationDataAccessObject invitationDAO =
//                    new InMemoryInvitationDataAccessObject();
//
//            // 当前用户 gateway，并设置一个假用户
//            SessionCurrentUserGateway currentUserGateway =
//                    new SessionCurrentUserGateway();
//            currentUserGateway.setCurrentUser(new User("alice", "password"));
//
//            // ViewModel
//            MakeInvitationViewModel vm = new MakeInvitationViewModel();
//
//            // Presenter
//            MakeInvitationOutputBoundary presenter =
//                    new MakeInvitationPresenter(vm);
//
//            // Interactor
//            MakeInvitationInputBoundary interactor =
//                    new MakeInvitationInteractor(
//                            invitationDAO,
//                            presenter,
//                            currentUserGateway);
//
//            // Controller
//            MakeInvitationController controller =
//                    new MakeInvitationController(interactor);
//
//            // View（注意：构造函数只传 vm）
//            MakeInvitationView view = new MakeInvitationView(vm);
//            // 再单独把 controller 塞进去（和 SignupView 一样的模式）
//            view.setMakeInvitationController(controller);
//
//            // 放进 JFrame 里展示出来
//            JFrame frame = new JFrame("Make Invitatio");
//            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            frame.setContentPane(view);
//            frame.pack();
//            frame.setLocationRelativeTo(null);
//            frame.setVisible(true);
//        });
//    }
//}