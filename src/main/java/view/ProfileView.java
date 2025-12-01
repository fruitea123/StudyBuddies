package view;

import interface_adapter.profile.ProfileViewModel;
import interface_adapter.profile.ProfileState;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ProfileView extends JPanel implements PropertyChangeListener {

    private final ProfileViewModel profileViewModel;

    private final String viewName = "profile";

    private JLabel usernameLabel;
    private JLabel nameLabel;
    private JLabel programsLabel;
    private JLabel descriptionLabel;

    public ProfileView(ProfileViewModel profileViewModel) {
        this.profileViewModel = profileViewModel;
        this.profileViewModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createLineBorder(Color.black));

        usernameLabel = new JLabel();
        nameLabel = new JLabel();
        programsLabel = new JLabel();
        descriptionLabel = new JLabel();

        JPanel panel = new JPanel(new GridBagLayout());
        panel.add(usernameLabel);
        panel.add(nameLabel);
        panel.add(programsLabel);
        panel.add(descriptionLabel);

        add(panel, BorderLayout.CENTER);

        update(profileViewModel.getState());

    }
// make list of programs p1,p2,p3
    private void update(ProfileState state) {
        usernameLabel.setText(ProfileViewModel.USERNAME_LABEL + state.getUsername());
        nameLabel.setText(ProfileViewModel.NAME_LABEL + state.getName());
        programsLabel.setText(ProfileViewModel.PROGRAM_LABEL + state.getPrograms());
        descriptionLabel.setText(ProfileViewModel.DESCRIPTION_LABEL + state.getDescription());
    }

    @Override
    public void propertyChange(PropertyChangeEvent e) {
        update(profileViewModel.getState());
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Profile");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new ProfileView(new ProfileViewModel()));
        frame.setSize(450, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public String getViewName() {
        return viewName;
    }
}
