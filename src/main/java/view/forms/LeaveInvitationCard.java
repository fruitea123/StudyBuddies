package view.forms;

import javax.swing.*;

public class LeaveInvitationCard extends InvitationCardBase{
    private JPanel InvitationCard;
    private JLabel titleLabel;
    private JTextPane infoTextPane;
    private JButton leaveButton;


    public JPanel getPanel() {
        return InvitationCard;
    }

    public void setTitle(String title) {
        titleLabel.setText(title);
    }

    public void setInfo(String info) {
        infoTextPane.setText(info);
    }

    public JButton getActionButton() {
        return leaveButton;
    }
}
