package view;

import entity.Invitation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InvitationCardAcceptPanel extends JPanel implements ActionListener {

    private final Invitation invitation;
    private final JTextArea description_area;
    private final JLabel title;
    private final JButton accept;

    public InvitationCardAcceptPanel(Invitation invitation) {
        this.invitation = invitation;

        this.description_area = new JTextArea(invitation.getDescription());

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
                            invitation.setCourse("csc207");    //placeholder
                        }
                    }
                }
        );

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(title);
        this.add(lower);


    }
    public void actionPerformed(ActionEvent evt) {
        JOptionPane.showMessageDialog(this, "Cancel not implemented yet.");
    }

}
