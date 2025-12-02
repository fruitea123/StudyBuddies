package app;

import data_access.*;
import entity.UserFactory;
import interface_adapter.ViewManagerModel;
//import interface_adapter.logged_in.ChangePasswordController;
//import interface_adapter.logged_in.ChangePasswordPresenter;
//import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.accept.AcceptInvitationController;
import interface_adapter.filter.FilterController;
import interface_adapter.filter.FilterPresenter;
import interface_adapter.filter.FilterViewModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginPresenter;
import interface_adapter.login.LoginViewModel;
//import interface_adapter.logout.LogoutController;
//import interface_adapter.logout.LogoutPresenter;
import interface_adapter.profile.ProfileViewModel;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupPresenter;
import interface_adapter.signup.SignupViewModel;
//import use_case.change_password.ChangePasswordInputBoundary;
//import use_case.change_password.ChangePasswordInteractor;
//import use_case.change_password.ChangePasswordOutputBoundary;
import interface_adapter.study_pool.StudyPoolViewModel;
import use_case.filter.FilterInputBoundary;
import use_case.filter.FilterInteractor;
import use_case.filter.FilterOutputBoundary;
import use_case.login.LoginInputBoundary;
import use_case.login.LoginInteractor;
import use_case.login.LoginOutputBoundary;
//import use_case.logout.LogoutInputBoundary;
//import use_case.logout.LogoutInteractor;
//import use_case.logout.LogoutOutputBoundary;
import use_case.signup.SignupInputBoundary;
import use_case.signup.SignupInteractor;
import use_case.signup.SignupOutputBoundary;
//import view.LoggedInView;
import view.LoginView;
import view.SignUpView;
import view.ViewManager;
import interface_adapter.makeinvitation.*;
import use_case.makeinvitation.*;
//import data_access.InMemoryInvitationDataAccessObject; // change after implemented MongoDB
import view.*;


import interface_adapter.notifications.NotificationsController;
import interface_adapter.notifications.NotificationsPresenter;
import interface_adapter.notifications.NotificationsViewModel;

import use_case.notifications.ViewNotificationsInputBoundary;
import use_case.notifications.ViewNotificationsInteractor;
import use_case.notifications.ViewNotificationsOutputBoundary;
import use_case.notifications.CurrentUserIdProvider;

import view.NotificationsView;


import javax.swing.*;
import java.awt.*;

public class AppBuilder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    final UserFactory userFactory = new UserFactory();
    final ViewManagerModel viewManagerModel = new ViewManagerModel();
    ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);

    // set which data access implementation to use, can be any
    // of the classes from the data_access package

    // DAO version using local file storage
    final SignupDAO signupUserDataAccessObject = new SignupDAO();

    // DAO version using a shared external database
    // final DBUserDataAccessObject userDataAccessObject = new DBUserDataAccessObject(userFactory);

    private SignUpView signupView;
    private SignupViewModel signupViewModel;
    private LoginViewModel loginViewModel;
    private ProfileViewModel profileViewModel;
    //    private LoggedInView loggedInView;
    private LoginView loginView;
    private MakeInvitationViewModel makeInvitationViewModel;
    private MakeInvitationView makeInvitationView;

    private final MongoInvitationDataAccessObject invitationDataAccessObject =
            new MongoInvitationDataAccessObject();
    private final InvitationDAO invitationDAO =
            new InvitationDAO(DBAccess.getDatabase());
    private final NotificationDataAccessObject notificationDataAccessObject =
            new InMemoryNotificationDataAccessObject();

    private final SessionCurrentUserGateway sessionCurrentUserGateway =
            new SessionCurrentUserGateway();
    private NotificationsView notificationsView;
    private NotificationsViewModel notificationsViewModel;

    private FilterView filterView;
    private FilterViewModel filterViewModel;

    private StudyPoolView studyPoolView;
    private StudyPoolViewModel studyPoolViewModel;
    private AcceptInvitationController acceptController;
    private FilterController filterController;

    private ProfileView profileView;
    private ProfileViewModel poolViewModel;




    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
    }

    public AppBuilder addSignupView() {
        signupViewModel = new SignupViewModel();
//        signupView = new SignUpView(signupViewModel);
        signupView = new SignUpView(signupViewModel);
//        cardPanel.add(signupView, signupView.getViewName());
        return this;
    }

    public AppBuilder addLoginView() {
        loginViewModel = new LoginViewModel();
        loginView = new LoginView(loginViewModel);
        cardPanel.add(loginView, loginView.getViewName());
        return this;
    }

//    public AppBuilder addLoggedInView() {
//        loggedInViewModel = new LoggedInViewModel();
//        loggedInView = new LoggedInView(loggedInViewModel);
//        cardPanel.add(loggedInView, loggedInView.getViewName());
//        return this;
//    }

    public AppBuilder addMakeInvitationView() {
        makeInvitationViewModel = new MakeInvitationViewModel();
        makeInvitationView = new MakeInvitationView(makeInvitationViewModel);
        cardPanel.add(makeInvitationView, makeInvitationView.getViewName());
        return this;
    }

    public AppBuilder addMakeInvitationNavigation() {
        MakeInvitationBackController backController =
                new MakeInvitationBackController(viewManagerModel, profileViewModel);
        makeInvitationView.setBackController(backController);
        return this;
    }
    public AppBuilder addNotificationsView() {
        notificationsViewModel = new NotificationsViewModel();
        notificationsView = new NotificationsView(notificationsViewModel);
        cardPanel.add(notificationsView, notificationsView.getViewName());
        return this;
    }


    public AppBuilder addFilterView() {
        filterViewModel = new FilterViewModel();
        filterView = new FilterView(filterViewModel);
        cardPanel.add(filterView, filterView.getViewName());
        return this;
    }


    public AppBuilder addStudyPoolView() {
        studyPoolViewModel = new StudyPoolViewModel();
        profileViewModel = new ProfileViewModel();
//        acceptController = new AcceptInvitationController();
//        filterController = new FilterController();

        studyPoolView = new StudyPoolView(studyPoolViewModel, profileViewModel);
        cardPanel.add(studyPoolView, studyPoolViewModel.getViewName());
        return this;
    }

    public AppBuilder addProfileView() {
        poolViewModel = new ProfileViewModel();
        profileView = new ProfileView(profileViewModel);
        cardPanel.add(profileView, profileView.getViewName());
        return this;
    }








    public AppBuilder addSignupUseCase() {
        final SignupOutputBoundary signupOutputBoundary = new SignupPresenter(viewManagerModel,
                signupViewModel, profileViewModel);
        final SignupInputBoundary userSignupInteractor = new SignupInteractor(
                signupUserDataAccessObject, signupOutputBoundary, userFactory);

        SignupController controller = new SignupController(userSignupInteractor);
//        signupView.setSignupController(controller);
        return this;
    }

    public AppBuilder addLoginUseCase() {
        final LoginOutputBoundary loginOutputBoundary = new LoginPresenter(viewManagerModel,
                profileViewModel, loginViewModel);
        final LoginInputBoundary loginInteractor = new LoginInteractor(
                signupUserDataAccessObject, loginOutputBoundary, sessionCurrentUserGateway);

        LoginController loginController = new LoginController(loginInteractor, viewManagerModel, signupViewModel);
        loginView.setLoginController(loginController);
        return this;
    }

//    public AppBuilder addChangePasswordUseCase() {
//        final ChangePasswordOutputBoundary changePasswordOutputBoundary = new ChangePasswordPresenter(viewManagerModel,
//                loggedInViewModel);
//
//        final ChangePasswordInputBoundary changePasswordInteractor =
//                new ChangePasswordInteractor(userDataAccessObject, changePasswordOutputBoundary, userFactory);
//
//        ChangePasswordController changePasswordController = new ChangePasswordController(changePasswordInteractor);
//        loggedInView.setChangePasswordController(changePasswordController);
//        return this;
//    }

    /**
     * Adds the Logout Use Case to the application.
     * @return this builder
     */
//    public AppBuilder addLogoutUseCase() {
//        final LogoutOutputBoundary logoutOutputBoundary = new LogoutPresenter(viewManagerModel,
//                loggedInViewModel, loginViewModel);
//
//        final LogoutInputBoundary logoutInteractor =
//                new LogoutInteractor(userDataAccessObject, logoutOutputBoundary);
//
//        final LogoutController logoutController = new LogoutController(logoutInteractor);
//        loggedInView.setLogoutController(logoutController);
//        return this;
//    }

    public AppBuilder addMakeInvitationUseCase() {
        // updates MakeInvitationViewModel
        MakeInvitationOutputBoundary invitationOutputBoundary =
                new MakeInvitationPresenter (makeInvitationViewModel);
        // add parameter viewManagerModel, loggedInViewModel so presenter can go back to profile later

        MakeInvitationInputBoundary invitationInteractor =
                new MakeInvitationInteractor(
                        invitationDataAccessObject,
                        invitationOutputBoundary,
                        sessionCurrentUserGateway
                );

        MakeInvitationController makeInvitationController =
                new MakeInvitationController(invitationInteractor);

        makeInvitationView.setMakeInvitationController(makeInvitationController);

        return this;
    }

    public AppBuilder addFilterUseCase() {
        FilterOutputBoundary filterOutputBoundary;
        filterOutputBoundary = new FilterPresenter(filterViewModel,
                studyPoolViewModel,
                viewManagerModel,
                profileViewModel);


        FilterInputBoundary filterInteractor =
                new FilterInteractor(
                        invitationDAO,
                        filterOutputBoundary
                );

        FilterController filterController =
                new FilterController(filterInteractor);

        filterView.setFilterController(filterController);

        return this;
    }




    public AppBuilder addNotificationsUseCase() {
        // 1. Presenter：
        final ViewNotificationsOutputBoundary notificationsOutputBoundary =
                new NotificationsPresenter(notificationsViewModel);

        // 2. 当前用户 ID 提供者
        CurrentUserIdProvider currentUserIdProvider = new CurrentUserIdProvider() {
            @Override
            public String getCurrentUserId() {
                return profileViewModel.getState().getUsername();
            }
        };

        // 3. Interactor：假设构造函数是
        //    ViewNotificationsInteractor(NotificationDataAccessObject dao,
        //                                CurrentUserIdProvider currentUserIdProvider,
        //                                ViewNotificationsOutputBoundary presenter)
        final ViewNotificationsInputBoundary notificationsInteractor =
                new ViewNotificationsInteractor(
                        notificationDataAccessObject,
                        currentUserIdProvider,
                        notificationsOutputBoundary);

        // 4. Controller
        final NotificationsController notificationsController =
                new NotificationsController(notificationsInteractor);

        // 5. 把 controller 塞进 view
        notificationsView.setNotificationsController(notificationsController);
        return this;
    }



    public JFrame build() {
        final JFrame application = new JFrame("User Login Example");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        application.add(cardPanel);

        viewManagerModel.setState(filterView.getViewName());
        viewManagerModel.firePropertyChange();

        return application;
    }


}
