package view.forms;

import javax.swing.*;

public class DeleteInvitationCard extends InvitationCardBase{

    private JButton deleteButton;
    private JTextPane infoTextPane;
    private JLabel titleLabel;
    private JPanel InvitationCard;

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
        return deleteButton;
    }
}
