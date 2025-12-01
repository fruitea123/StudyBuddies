package app;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        AppBuilder appBuilder = new AppBuilder();
        JFrame application = appBuilder
// add views and use cases for all
                .addLoginView()
                .addSignupView()
//                .addLoggedInView()
                .addMakeInvitationView()
                .addNotificationsView()
                .addSignupUseCase()
                .addLoginUseCase()
//                .addLogoutUseCase()
//                .addChangePasswordUseCase()
                .addMakeInvitationUseCase()
                .addMakeInvitationNavigation()
                .addNotificationsUseCase()
                .build();

        application.pack();
        application.setLocationRelativeTo(null);
        application.setVisible(true);
    }
}