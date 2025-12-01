package view.forms;

import javax.swing.*;

public abstract class InvitationCardBase {
    protected JPanel mainPanel;
    protected JLabel titleLabel;
    protected JTextPane infoTextPane;

    public JPanel getPanel() {
        return mainPanel;
    }

    public void setTitle(String title) {
        titleLabel.setText(title);
    }

    public void setInfo(String info) {
        infoTextPane.setText(info);
    }

    // Each card type must return ITS button
    public abstract JButton getActionButton();
}
