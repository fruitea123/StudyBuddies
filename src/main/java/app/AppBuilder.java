package app;

import data_access.FileUserDataAccessObject;
import data_access.PasswordHasher;
import entity.UserFactory;
import interface_adapter.ViewManagerModel;
import interface_adapter.logged_in.ChangePasswordController;
import interface_adapter.logged_in.ChangePasswordPresenter;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginPresenter;
import interface_adapter.login.LoginViewModel;
import interface_adapter.logout.LogoutController;
import interface_adapter.logout.LogoutPresenter;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupPresenter;
import interface_adapter.signup.SignupViewModel;
import usecase.change_password.ChangePasswordInputBoundary;
import usecase.change_password.ChangePasswordInteractor;
import usecase.change_password.ChangePasswordOutputBoundary;
import usecase.login.LoginInputBoundary;
import usecase.login.LoginInteractor;
import usecase.login.LoginOutputBoundary;
import usecase.logout.LogoutInputBoundary;
import usecase.logout.LogoutInteractor;
import usecase.logout.LogoutOutputBoundary;
import usecase.signup.SignupInputBoundary;
import usecase.signup.SignupInteractor;
import usecase.signup.SignupOutputBoundary;
import usecase.signup.SignupPasswordHasher;
import view.LoggedInView;
import view.LoginView;
import view.SignupView;
import view.ViewManager;


import interface_adapter.notifications.NotificationsController;
import interface_adapter.notifications.NotificationsPresenter;
import interface_adapter.notifications.NotificationsViewModel;

import usecase.notifications.ViewNotificationsInputBoundary;
import usecase.notifications.ViewNotificationsInteractor;
import usecase.notifications.ViewNotificationsOutputBoundary;
import usecase.notifications.CurrentUserIdProvider;

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
    final FileUserDataAccessObject userDataAccessObject = new FileUserDataAccessObject("users.csv", userFactory);

    // DAO version using a shared external database
    // final DBUserDataAccessObject userDataAccessObject = new DBUserDataAccessObject(userFactory);

    private SignupView signupView;
    private SignupViewModel signupViewModel;
    private LoginViewModel loginViewModel;
    private LoggedInViewModel loggedInViewModel;
    private LoggedInView loggedInView;
    private LoginView loginView;
    private NotificationsView notificationsView;
    private NotificationsViewModel notificationsViewModel;


    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
    }

    public AppBuilder addSignupView() {
        signupViewModel = new SignupViewModel();
        signupView = new SignupView(signupViewModel);
        cardPanel.add(signupView, signupView.getViewName());
        return this;
    }

    public AppBuilder addLoginView() {
        loginViewModel = new LoginViewModel();
        loginView = new LoginView(loginViewModel);
        cardPanel.add(loginView, loginView.getViewName());
        return this;
    }

    public AppBuilder addLoggedInView() {
        loggedInViewModel = new LoggedInViewModel();
        loggedInView = new LoggedInView(loggedInViewModel);
        cardPanel.add(loggedInView, loggedInView.getViewName());
        return this;
    }

    public AppBuilder addNotificationsView() {
        notificationsViewModel = new NotificationsViewModel();
        notificationsView = new NotificationsView(notificationsViewModel);
        cardPanel.add(notificationsView, notificationsView.getViewName());
        return this;
    }



    public AppBuilder addSignupUseCase() {
        final SignupOutputBoundary signupOutputBoundary =
                new SignupPresenter(viewManagerModel, signupViewModel, loginViewModel);

        final SignupPasswordHasher passwordHasher = new PasswordHasher();
        final SignupInputBoundary userSignupInteractor =
                new SignupInteractor(
                        userDataAccessObject,
                        passwordHasher,
                        signupOutputBoundary,
                        userFactory
                );
        SignupController controller = new SignupController(userSignupInteractor);
        signupView.setSignupController(controller);
        return this;
    }

    public AppBuilder addLoginUseCase() {
        final LoginOutputBoundary loginOutputBoundary = new LoginPresenter(viewManagerModel,
                loggedInViewModel, loginViewModel);
        final LoginInputBoundary loginInteractor = new LoginInteractor(
                userDataAccessObject, loginOutputBoundary);

        LoginController loginController = new LoginController(loginInteractor, viewManagerModel, signupView);
        loginView.setLoginController(loginController);
        return this;
    }

    public AppBuilder addChangePasswordUseCase() {
        final ChangePasswordOutputBoundary changePasswordOutputBoundary = new ChangePasswordPresenter(viewManagerModel,
                loggedInViewModel);

        final ChangePasswordInputBoundary changePasswordInteractor =
                new ChangePasswordInteractor(userDataAccessObject, changePasswordOutputBoundary, userFactory);

        ChangePasswordController changePasswordController = new ChangePasswordController(changePasswordInteractor);
        loggedInView.setChangePasswordController(changePasswordController);
        return this;
    }

    /**
     * Adds the Logout Use Case to the application.
     * @return this builder
     */
    public AppBuilder addLogoutUseCase() {
        final LogoutOutputBoundary logoutOutputBoundary = new LogoutPresenter(viewManagerModel,
                loggedInViewModel, loginViewModel);

        final LogoutInputBoundary logoutInteractor =
                new LogoutInteractor(userDataAccessObject, logoutOutputBoundary);

        final LogoutController logoutController = new LogoutController(logoutInteractor);
        loggedInView.setLogoutController(logoutController);
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
                return loggedInViewModel.getUsername();
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

        viewManagerModel.setState(signupView.getViewName());
        viewManagerModel.firePropertyChange();

        return application;
    }
}
