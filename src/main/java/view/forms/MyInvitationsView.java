package view.forms;

import interface_adapter.myinvitations.InvitationItemViewModel;
import interface_adapter.myinvitations.MyInvitationsController;
import interface_adapter.myinvitations.MyInvitationsViewModel;

import javax.swing.*;

public class MyInvitationsView extends JFrame {

    // === Swing components from your .form ===
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

    // Controller injected by AppBuilder
    private MyInvitationsController controller;


    // ============================================================
    // Constructor — keeps EXACT style of your original version
    // ============================================================
    public MyInvitationsView() {
        setContentPane(PagePanel);
        setTitle("My Invitations");
        setSize(500, 500);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Layouts for dynamic invitation sections
        ParticipatingInvitations.setLayout(
                new BoxLayout(ParticipatingInvitations, BoxLayout.Y_AXIS)
        );
        OwnedInvitations.setLayout(
                new BoxLayout(OwnedInvitations, BoxLayout.Y_AXIS)
        );

        // Button hooks (view delegates → controller)
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

        // Other navigation buttons (stub)
        studyPoolButton.addActionListener(e -> {
            if (controller != null) controller.onStudyPoolClicked();
        });

        profileButton.addActionListener(e -> {
            if (controller != null) controller.onProfileClicked();
        });

        myInvitationsHomeButton.addActionListener(e -> {
            // stays on this screen – no controller call needed
        });
    }


    // ============================================================
    // Allow AppBuilder to set controller
    // ============================================================
    public void setController(MyInvitationsController controller) {
        this.controller = controller;
    }


    // ============================================================
    // The new MVP update() method
    // Replaces your old loadInvitations(dao, user)
    // ============================================================
    public void update(MyInvitationsViewModel viewModel) {

        // Clear old content
        ParticipatingInvitations.removeAll();
        OwnedInvitations.removeAll();

        // Keep your headers
        OwnedInvitations.add(OwnedInvitationsHeader);
        ParticipatingInvitations.add(ParticipatingInvitationsHeader);

        // -------------------------
        // Participating Invitations
        // -------------------------
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

        // -------------------------
        // Owned Invitations
        // -------------------------
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

        // Refresh UI
        ParticipatingInvitations.revalidate();
        ParticipatingInvitations.repaint();
        OwnedInvitations.revalidate();
        OwnedInvitations.repaint();
    }
}
