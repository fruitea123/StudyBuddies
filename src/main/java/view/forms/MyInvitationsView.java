package view.forms;

import interface_adapter.myinvitations.InvitationItemViewModel;
import interface_adapter.myinvitations.MyInvitationsController;
import interface_adapter.myinvitations.MyInvitationsViewModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyInvitationsView extends JPanel {

    // Swing
    private JPanel PagePanel;
    private JButton createInvitationButton;
    private JButton myInvitationsHomeButton;
    private JButton studyPoolButton;
    private JButton profileButton;
    private JButton calendarButton;

    private JPanel ParticipatingInvitations;
    private JPanel OwnedInvitations;
    private JLabel OwnedInvitationsHeader;
    private JLabel ParticipatingInvitationsHeader;
    private JButton notificationButton;

    // Controller injected by AppBuilder
    private MyInvitationsController controller;

    public static final String VIEW_NAME = "MyInvitations";

    public MyInvitationsView() {

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(PagePanel);

        ParticipatingInvitations.setLayout(
                new BoxLayout(ParticipatingInvitations, BoxLayout.Y_AXIS)
        );
        OwnedInvitations.setLayout(
                new BoxLayout(OwnedInvitations, BoxLayout.Y_AXIS)
        );

        // action listeners
        createInvitationButton.addActionListener(e -> {
            if (controller != null) {
                controller.onCreateInvitation();
            }
        });

        calendarButton.addActionListener(e -> {
            if (controller != null) {
                controller.onCalendarClicked();
            }
        });

        // page navigation
        studyPoolButton.addActionListener(e -> {
            if (controller != null) {
                controller.onStudyPoolClicked();
            }
        });

        profileButton.addActionListener(e -> {
            if (controller != null) {
                controller.onProfileClicked();
            }
        });

        myInvitationsHomeButton.addActionListener(e -> {
            if (controller != null) {
                controller.onMyInvitationClicked();
            }
        });

        notificationButton.addActionListener(e -> {
            if (controller != null) {
                controller.onNotificationsClicked();
            }
        });
    }

    public static String getViewName() {
        return VIEW_NAME;
    }

    public void setController(MyInvitationsController controller) {
        this.controller = controller;
        controller.load();
    }

    public void update(MyInvitationsViewModel viewModel) {

        // old content cleared
        ParticipatingInvitations.removeAll();
        OwnedInvitations.removeAll();

        // sets headers
        OwnedInvitations.add(OwnedInvitationsHeader);
        ParticipatingInvitations.add(ParticipatingInvitationsHeader);

        // loads participating invitations
        for (InvitationItemViewModel item : viewModel.getParticipatingInvitations()) {

            LeaveInvitationCard card = new LeaveInvitationCard();
            card.setTitle(item.getTitle());
            card.setInfo(item.getDescription());

            card.getActionButton().addActionListener(e -> {
                if (controller != null) {
                    controller.onLeaveClicked(item.getInvitationId());
                }
            });

            ParticipatingInvitations.add(card.getPanel());
        }

        // loads owned invitations
        for (InvitationItemViewModel item : viewModel.getOwnedInvitations()) {

            DeleteInvitationCard card = new DeleteInvitationCard();
            card.setTitle(item.getTitle());
            card.setInfo(item.getDescription());

            card.getActionButton().addActionListener(e -> {
                if (controller != null) {
                    controller.onDeleteClicked(item.getInvitationId());
                }
            });

            OwnedInvitations.add(card.getPanel());
        }

        // refresh UI
        ParticipatingInvitations.revalidate();
        ParticipatingInvitations.repaint();
        OwnedInvitations.revalidate();
        OwnedInvitations.repaint();
    }
}
