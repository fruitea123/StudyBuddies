package view;

import entity.Invitation;
import interface_adapter.accept.AcceptInvitationController;
import interface_adapter.profile.ProfileState;
import interface_adapter.profile.ProfileViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InvitationCardAcceptPanel extends JPanel implements ActionListener {

    private final Invitation invitation;
    private final JTextArea description_area;
    private final JLabel title;
    private final JButton accept;
    private final ProfileViewModel profileViewModel;
    private final AcceptInvitationController acceptController;

    public InvitationCardAcceptPanel(Invitation invitation, ProfileViewModel profileViewModel, AcceptInvitationController acceptController) {
        this.invitation = invitation;

        this.description_area = new JTextArea(invitation.getDescription());
        this.profileViewModel = profileViewModel;
        this.acceptController = acceptController;

        String title_string = "(" + invitation.getCourse() + ")" + " " + "(" + invitation.getDate().toString() + ")" +
                " " + "(" + invitation.getStartTime().toString() + " - " + invitation.getEndTime().toString() + ")";

        this.title = new JLabel(title_string);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.accept = new JButton("Accept");


        final JPanel lower = new JPanel();
        lower.add(description_area);
        lower.add(accept);

        accept.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (e.getSource().equals(accept)) {
                            final ProfileState currentState = profileViewModel.getState();
                            acceptController.acceptInvitation(invitation.getInvitationID(),
                                    currentState.getUsername());
                        }
                    }
                }
        );

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(title);
        this.add(lower);


    }
    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }

}
