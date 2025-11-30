package app;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        AppBuilder appBuilder = new AppBuilder();
        JFrame application = appBuilder
                .addLoginView()
                .addSignupView()
                .addLoggedInView()
                .addNotificationsView()
                .addSignupUseCase()
                .addLoginUseCase()
                .addLogoutUseCase()
                .addChangePasswordUseCase()
                .addNotificationsUseCase()
                .build();

        application.pack();
        application.setLocationRelativeTo(null);
        application.setVisible(true);
    }
}