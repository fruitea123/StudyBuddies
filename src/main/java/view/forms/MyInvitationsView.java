package view.forms;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//written by jessica, i apologize for all the weirdness.

public class MyInvitationsView extends JFrame {
    // this constructor class will be revamped to have loops to generate all
    // of the stuff below, but for now it's a bunch of samples.
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

    public void ParticipatingInvitationGenerator() {
        //a false constructor/generator I started for no reason other than to remind myself that I need one later, disregard
    }

    public MyInvitationsView() { //Constructor method
        //default setup
        setContentPane(PagePanel);
        setTitle("My Invitations");
        setSize(500, 500);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //actions for leave
        leaveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(MyInvitationsView.this, "hello world");
            }
        });

        leaveButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(MyInvitationsView.this, "hello world");
            }
        });

        //actions for delete
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(MyInvitationsView.this, "hello world");
            }
        });

        deleteButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(MyInvitationsView.this, "hello world");
            }
        });

        // could add more actions, but it's kinda pointless for me to make some rn, 'cause they'd lead to nothing :/
    }

    public static void main(String[] args) {
        //initializer, for testing purposes
        MyInvitationsView myInvitationsView = new MyInvitationsView();
        myInvitationsView.setVisible(true);
    }

}
