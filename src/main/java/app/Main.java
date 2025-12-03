package app;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        AppBuilder appBuilder = new AppBuilder();
        JFrame application = appBuilder
// add views and use cases for all
                .addLoginView()
                .addSignupView()
                .addMakeInvitationView()
                .addNotificationsView()
                .addFilterView()
                .addStudyPoolView()
                .addProfileView()
                .addMyInvitationsView()
                .addMakeInvitationNavigation()
                .addSignupUseCase()
                .addLoginUseCase()
                .addMakeInvitationUseCase()
                .addNotificationsUseCase()
                .addFilterUseCase()
                .addCalendarUseCase()
                .addMyInvitationsUseCase()
                .build();


        application.pack();
        application.setLocationRelativeTo(null);
        application.setVisible(true);
    }
}