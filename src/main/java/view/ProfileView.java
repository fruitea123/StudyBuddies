package view;

import interface_adapter.filter.FilterController;
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
    private JLabel iconLabel;
    private FilterController filterController;

    public ProfileView(ProfileViewModel profileViewModel) {
        this.profileViewModel = profileViewModel;
        this.profileViewModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createLineBorder(Color.black));

        usernameLabel = new JLabel();
        nameLabel = new JLabel();
        programsLabel = new JLabel();
        descriptionLabel = new JLabel();
        iconLabel = new JLabel();

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(iconLabel);
        panel.add(usernameLabel);
        panel.add(nameLabel);
        panel.add(programsLabel);
        panel.add(descriptionLabel);

        add(panel, BorderLayout.CENTER);

        update(profileViewModel.getState());

        JButton homeButton = new JButton("Home");
        panel.add(homeButton, BorderLayout.SOUTH);
        homeButton.addActionListener(e -> filterController.movetohome());

    }

    private void update(ProfileState state) {
        if (state.getIcon() != null) {
            iconLabel.setIcon(new ImageIcon(state.getIcon()));
        }

        usernameLabel.setText("username: " +  state.getUsername());
        nameLabel.setText("Name: " + state.getName());
        programsLabel.setText("Programs: " + state.getPrograms());
        descriptionLabel.setText("Description: " + state.getDescription());
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

    public void setFilterController(FilterController filterController) {
        this.filterController = filterController;
    }
}
