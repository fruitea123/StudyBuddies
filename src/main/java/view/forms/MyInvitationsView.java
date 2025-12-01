package view.forms;

import data_access.InvitationDAO;
import entity.Invitation;
import use_case.myinvitations.MyInvitations;

import javax.swing.*;
import java.util.List;


public class MyInvitationsView extends JFrame {
    private JButton createInvitationButton;
    private JButton leaveButton;
    private JButton leaveButton1;
    private JButton deleteButton;
    private JButton deleteButton1;
    private JTextPane info1TextPane;
    private JTextPane info2TextPane;
    private JTextPane info3TextPane;
    private JTextPane info4TextPane;
    private JPanel PagePanel;
    private JButton myInvitationsHomeButton;
    private JButton studyPoolButton;
    private JButton profileButton;
    private JPanel ParticipatingInvitations;
    private JPanel OwnedInvitations;
    private JLabel OwnedInvitationsHeader;
    private JLabel ParticipatingInvitationsHeader;

    public MyInvitationsView() { //Constructor method
        //default setup
        setContentPane(PagePanel);
        setTitle("My Invitations");
        setSize(500, 500);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void loadInvitations(InvitationDAO dao, String currentUser) {

        MyInvitations myInvitations = new MyInvitations(dao);

        List<Invitation> owned = myInvitations.FilterByOwned(currentUser);
        List<Invitation> participating = myInvitations.FilterByParticipant(currentUser);

        // clears existing cards
        ParticipatingInvitations.removeAll();
        OwnedInvitations.removeAll();

        ParticipatingInvitations.setLayout(
                new BoxLayout(ParticipatingInvitations, BoxLayout.Y_AXIS)
        );
        OwnedInvitations.setLayout(
                new BoxLayout(OwnedInvitations, BoxLayout.Y_AXIS)
        );

        OwnedInvitations.add(OwnedInvitationsHeader);
        ParticipatingInvitations.add(ParticipatingInvitationsHeader);

        // card builder
        for (Invitation inv : participating) {

            LeaveInvitationCard card = new LeaveInvitationCard();

            card.setTitle(inv.getCourse());          // top label
            card.setInfo(inv.getDescription());      // text pane

            // here goes leave logic
            card.getActionButton().addActionListener(e ->
                    System.out.println("Clicked leave for: " + inv.getCourse())
            );

            ParticipatingInvitations.add(card.getPanel());

            }

        for (Invitation inv : owned) {

            DeleteInvitationCard card = new DeleteInvitationCard();

            card.setTitle(inv.getCourse());          // top label
            card.setInfo(inv.getDescription());      // text pane

            // here goes leave logic
            card.getActionButton().addActionListener(e ->
                    System.out.println("Clicked leave for: " + inv.getCourse())
            );

            OwnedInvitations.add(card.getPanel());

        }

        // refresh UI
        ParticipatingInvitations.revalidate();
        ParticipatingInvitations.repaint();
        OwnedInvitations.revalidate();
        OwnedInvitations.repaint();
    }

    public static void main(String[] args) {
        //initializer, for testing purposes
        MyInvitationsView myInvitationsView = new MyInvitationsView();
        myInvitationsView.setVisible(true);
    }

}
