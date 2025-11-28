package view;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import java.awt.*;

public class SignUpView extends JPanel {
    public SignUpView() {

        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        add(scrollPane, BorderLayout.CENTER);

        JLabel emailLabel = requiredLabel("Email");
        emailLabel.setBounds(50, 30, 200, 25);
        mainPanel.add(emailLabel);
        JTextField emailField = new JTextField();
        emailField.setBounds(50, 55, 300, 30);
        mainPanel.add(emailField);

        JLabel passwordLabel = requiredLabel("Password");
        passwordLabel.setBounds(50, 105, 200, 25);
        mainPanel.add(passwordLabel);
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(50, 130, 140, 30);
        mainPanel.add(passwordField);

        JLabel repeatPasswordLabel = requiredLabel("Repeat Password");
        repeatPasswordLabel.setBounds(210, 105, 200, 25);
        mainPanel.add(repeatPasswordLabel);
        JPasswordField repeatPasswordField = new JPasswordField();
        repeatPasswordField.setBounds(210, 130, 140, 30);
        mainPanel.add(repeatPasswordField);

        JLabel firstNameLabel = requiredLabel("First Name");
        firstNameLabel.setBounds(50, 180, 200, 25);
        mainPanel.add(firstNameLabel);
        JTextField firstNameField = new JTextField();
        firstNameField.setBounds(50, 205, 140, 30);
        mainPanel.add(firstNameField);

        JLabel lastNameLabel = requiredLabel("Last Name");
        lastNameLabel.setBounds(210, 180, 200, 25);
        mainPanel.add(lastNameLabel);
        JTextField lastNameField = new JTextField();
        lastNameField.setBounds(210, 205, 140, 30);
        mainPanel.add(lastNameField);

        JLabel birthdayLabel = requiredLabel("Birthday");
        birthdayLabel.setBounds(50, 255, 200, 25);
        mainPanel.add(birthdayLabel);

        String[] days = new String[31];
        for (int i = 1; i <= 31; i++) days[i - 1] = String.valueOf(i);
        JComboBox<String> dayBox = new JComboBox<>(days);
        dayBox.setBounds(50, 280, 80, 30);
        mainPanel.add(dayBox);

        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        JComboBox<String> monthBox = new JComboBox<>(months);
        monthBox.setBounds(140, 280, 80, 30);
        mainPanel.add(monthBox);

        String[] years = new String[2015 - 1960 + 1];
        for (int i = 1960; i <= 2015; i++) years[i - 1960] = String.valueOf(i);
        JComboBox<String> yearBox = new JComboBox<>(years);
        yearBox.setBounds(230, 280, 80, 30);
        mainPanel.add(yearBox);

        JLabel numProgramsLabel = new JLabel("Number of Programs Enrolled *");
        numProgramsLabel.setBounds(50, 330, 300, 25);
        mainPanel.add(numProgramsLabel);

        JComboBox<String> numProgramsBox = new JComboBox<>(new String[]{"1", "2", "3"});
        numProgramsBox.setBounds(50, 355, 80, 30);
        mainPanel.add(numProgramsBox);

        JLabel p1Label = new JLabel("1st Program *");
        p1Label.setBounds(50, 400, 200, 25);
        mainPanel.add(p1Label);
        JTextField p1Field = new JTextField();
        p1Field.setBounds(50, 425, 140, 30);
        mainPanel.add(p1Field);

        JLabel p2Label = new JLabel("2nd Program *");
        p2Label.setBounds(210, 400, 200, 25);
        mainPanel.add(p2Label);
        JTextField p2Field = new JTextField();
        p2Field.setBounds(210, 425, 140, 30);
        mainPanel.add(p2Field);

        JLabel p3Label = new JLabel("3rd Program *");
        p3Label.setBounds(370, 400, 200, 25);
        mainPanel.add(p3Label);
        JTextField p3Field = new JTextField();
        p3Field.setBounds(370, 425, 140, 30);
        mainPanel.add(p3Field);

        p1Field.setEnabled(false);
        p2Field.setEnabled(false);
        p3Field.setEnabled(false);

        numProgramsBox.addActionListener(e -> {
            int n = Integer.parseInt((String) numProgramsBox.getSelectedItem());
            p1Field.setEnabled(n >= 1);
            p2Field.setEnabled(n >= 2);
            p3Field.setEnabled(n >= 3);
        });

        JLabel iconLabel = new JLabel("Choose an icon (optional)");
        iconLabel.setBounds(50, 480, 300, 25);
        mainPanel.add(iconLabel);

        String[] iconNames = {"cat", "chicken", "gorilla", "meerkat", "panda", "rabbit"};
        JComboBox<String> iconBox = new JComboBox<>(iconNames);
        iconBox.setBounds(50, 505, 200, 30);
        mainPanel.add(iconBox);

        JLabel descLabel = new JLabel("Description (optional)");
        descLabel.setBounds(50, 550, 300, 25);
        mainPanel.add(descLabel);

        JTextArea descArea = new JTextArea();
        JScrollPane descScroll = new JScrollPane(descArea);
        descScroll.setBounds(50, 575, 450, 150);
        mainPanel.add(descScroll);

        JButton createButton = new JButton("Create Account");
        createButton.setBounds(50, 740, 150, 35);
        mainPanel.add(createButton);

        JLabel loginLabel = new JLabel("Already have an account? ");
        loginLabel.setBounds(50, 780, 200, 25);
        mainPanel.add(loginLabel);

        JLabel loginLink = new JLabel(" log in");
        loginLink.setForeground(Color.BLUE);
        loginLink.setBounds(200, 780, 100, 25);
        mainPanel.add(loginLink);

        mainPanel.setPreferredSize(new Dimension(700, 850));
    }

    private JLabel requiredLabel(String text) {
        JLabel label = new JLabel("<html>" + text + " <font color='red'>*</font></html>");
        label.setForeground(Color.BLACK);
        return label;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Sign Up Form");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new SignUpView());
        frame.setSize(450, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}













//package view;
//
//import javax.swing.*;
//import java.awt.*;
//
//public class SignUpView extends JPanel {
//
//    public SignUpView() {
//
//        setLayout(new BorderLayout());
//
//        // Panel that will HOLD all fields
//        JPanel contentPanel = new JPanel();
//        contentPanel.setLayout(new GridBagLayout());
//        contentPanel.setBackground(Color.WHITE);
//
//        // Wrap the content panel in a scrollpane
//        JScrollPane scrollPane = new JScrollPane(contentPanel);
//        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // smoother scrolling
//        scrollPane.setBorder(null);
//        scrollPane.getViewport().setBackground(Color.WHITE);
//
//        add(scrollPane, BorderLayout.CENTER);
//
//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.insets = new Insets(10, 10, 10, 10);
//        gbc.fill = GridBagConstraints.HORIZONTAL;
//        gbc.gridx = 0;
//        gbc.gridy = 0;
//
//        // Fields
//        JLabel emailLabel = requiredLabel("Email");
//        JTextField emailField = new JTextField(20);
//
//        JLabel passwordLabel = requiredLabel("Password");
//        JPasswordField passwordField = new JPasswordField(20);
//
//        JLabel firstNameLabel = requiredLabel("First Name");
//        JTextField firstNameField = new JTextField(20);
//
//        JLabel lastInitialLabel = requiredLabel("Last Initial");
//        JTextField lastInitialField = new JTextField(2);
//
//        JLabel programLabel = requiredLabel("Program");
//        JTextField programField = new JTextField(20);
//
//        JLabel descLabel = new JLabel("Description (optional):");
//        descLabel.setForeground(Color.BLACK);
//        JTextArea descriptionArea = new JTextArea(4, 20);
//        descriptionArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
//
//        JLabel avatarLabel = new JLabel("Profile Avatar (optional):");
//        avatarLabel.setForeground(Color.BLACK);
//        JButton chooseAvatarBtn = new JButton("Choose Avatar");
//
//        JButton submitBtn = new JButton("Sign Up");
//
//        // Add email row
//        contentPanel.add(emailLabel, gbc);
//        gbc.gridx = 1; contentPanel.add(emailField, gbc);
//
//        // Password row
//        gbc.gridx = 0; gbc.gridy++;
//        contentPanel.add(passwordLabel, gbc);
//        gbc.gridx = 1; contentPanel.add(passwordField, gbc);
//
//        // First name row
//        gbc.gridx = 0; gbc.gridy++;
//        contentPanel.add(firstNameLabel, gbc);
//        gbc.gridx = 1; contentPanel.add(firstNameField, gbc);
//
//        // Last initial row
//        gbc.gridx = 0; gbc.gridy++;
//        contentPanel.add(lastInitialLabel, gbc);
//        gbc.gridx = 1; contentPanel.add(lastInitialField, gbc);
//
//        // Program row
//        gbc.gridx = 0; gbc.gridy++;
//        contentPanel.add(programLabel, gbc);
//        gbc.gridx = 1; contentPanel.add(programField, gbc);
//
//        // Description row
//        gbc.gridx = 0; gbc.gridy++;
//        contentPanel.add(descLabel, gbc);
//        gbc.gridx = 1; contentPanel.add(new JScrollPane(descriptionArea), gbc);
//
//        // Avatar row
//        gbc.gridx = 0; gbc.gridy++;
//        contentPanel.add(avatarLabel, gbc);
//        gbc.gridx = 1; contentPanel.add(chooseAvatarBtn, gbc);
//
//        // Submit button
//        gbc.gridx = 0; gbc.gridy++;
//        gbc.gridwidth = 2;
//        gbc.anchor = GridBagConstraints.CENTER;
//        contentPanel.add(submitBtn, gbc);
//    }
//
//    // Helper method for required field labels
//    private JLabel requiredLabel(String text) {
//        JLabel label = new JLabel("<html>" + text + " <font color='red'>*</font></html>");
//        label.setForeground(Color.BLACK);
//        return label;
//    }
//
//    // Test frame
//    public static void main(String[] args) {
//        JFrame frame = new JFrame("Sign Up Form");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.add(new SignUpView());
//        frame.setSize(450, 600);
//        frame.setLocationRelativeTo(null);
//        frame.setVisible(true);
//    }
//}
