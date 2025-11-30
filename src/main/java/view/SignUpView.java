package view;

import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupViewModel;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import java.awt.*;

public class SignUpView extends JPanel {

    private SignupController signupController;
    private SignupViewModel signupViewModel;
    private final String viewName = "sign up";

    public SignUpView(SignupController signupController, SignupViewModel signupViewModel) {
        this.signupController = signupController;
        this.signupViewModel = signupViewModel;
    }

    public SignUpView() {

        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setPreferredSize(new Dimension(600, 1000));

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        add(scrollPane, BorderLayout.CENTER);

        JLabel emailLabel = requiredLabel("Email");
        emailLabel.setBounds(50, 30, 200, 25);
        mainPanel.add(emailLabel);
        JTextField emailField = new JTextField();
        emailField.setBounds(50, 55, 450, 30);
        mainPanel.add(emailField);

        JLabel passwordLabel = requiredLabel("Password");
        passwordLabel.setBounds(50, 105, 200, 25);
        mainPanel.add(passwordLabel);
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(50, 130, 220, 30);
        mainPanel.add(passwordField);

        JLabel repeatPasswordLabel = requiredLabel("Repeat Password");
        repeatPasswordLabel.setBounds(280, 105, 200, 25);
        mainPanel.add(repeatPasswordLabel);
        JPasswordField repeatPasswordField = new JPasswordField();
        repeatPasswordField.setBounds(280, 130, 220, 30);
        mainPanel.add(repeatPasswordField);

        JLabel firstNameLabel = requiredLabel("First Name");
        firstNameLabel.setBounds(50, 180, 200, 25);
        mainPanel.add(firstNameLabel);
        JTextField firstNameField = new JTextField();
        firstNameField.setBounds(50, 205, 220, 30);
        mainPanel.add(firstNameField);

        JLabel lastNameLabel = requiredLabel("Last Name");
        lastNameLabel.setBounds(280, 180, 200, 25);
        mainPanel.add(lastNameLabel);
        JTextField lastNameField = new JTextField();
        lastNameField.setBounds(280, 205, 220, 30);
        mainPanel.add(lastNameField);

        JLabel birthdayLabel = requiredLabel("Birthday");
        birthdayLabel.setBounds(50, 255, 200, 25);
        mainPanel.add(birthdayLabel);

        String[] days = new String[32];
        days[0] = "Day";
        for (int i = 1; i <= 31; i++) days[i] = String.valueOf(i);
        JComboBox<String> dayBox = new JComboBox<>(days);
        dayBox.setBounds(50, 280, 140, 30);
        mainPanel.add(dayBox);

        String[] months = {"Month", "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        JComboBox<String> monthBox = new JComboBox<>(months);
        monthBox.setBounds(200, 280, 140, 30);
        mainPanel.add(monthBox);

        String[] years = new String[(2015 - 1960 + 1) + 1];  // +1 for "Year"
        years[0] = "Year";
        int index = 1;
        for (int i = 1960; i <= 2015; i++) {
            years[index++] = String.valueOf(i);
        }
        JComboBox<String> yearBox = new JComboBox<>(years);
        yearBox.setBounds(350, 280, 140, 30);
        mainPanel.add(yearBox);

        JLabel numProgramsLabel = requiredLabel("Number of Programs Enrolled");
        numProgramsLabel.setBounds(50, 330, 300, 25);
        mainPanel.add(numProgramsLabel);

        JComboBox<String> numProgramsBox = new JComboBox<>(new String[]{" ", "1", "2", "3"});
        numProgramsBox.setBounds(50, 355, 80, 30);
        mainPanel.add(numProgramsBox);

        JLabel p1Label = requiredLabel("1st Program");
        p1Label.setBounds(50, 400, 200, 25);
        mainPanel.add(p1Label);
        JTextField p1Field = new JTextField();
        p1Field.setBounds(50, 425, 140, 30);
        mainPanel.add(p1Field);

        JLabel p2Label = requiredLabel("2nd Program");
        p2Label.setBounds(210, 400, 200, 25);
        mainPanel.add(p2Label);
        JTextField p2Field = new JTextField();
        p2Field.setBounds(210, 425, 140, 30);
        mainPanel.add(p2Field);

        JLabel p3Label = requiredLabel("3rd Program");
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

        String[] iconNames = {" ", "cat", "chicken", "gorilla", "meerkat", "panda", "rabbit"};
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
        createButton.setBounds(200, 740, 150, 35);
        mainPanel.add(createButton);

        JLabel haveAccountLabel = new JLabel("Already have an account?");
        haveAccountLabel.setBounds(170, 780, 200, 25);
        mainPanel.add(haveAccountLabel);

        JButton loginButton = new JButton("log in");
        loginButton.setForeground(Color.BLUE);
        loginButton.setBounds(290, 780, 100, 25);
        loginButton.setBorderPainted(false);
        loginButton.setContentAreaFilled(false);
        loginButton.setFocusPainted(false);
        mainPanel.add(loginButton);

        createButton.addActionListener(e -> {
            System.out.println("Creating account...");
            System.out.println("Email: " + emailField.getText());
            System.out.println("First Name: " + firstNameField.getText());
        });
        loginButton.addActionListener(e -> {
            System.out.println("Switching to Login View...");
        });
    }

    private JLabel requiredLabel(String text) {
        JLabel label = new JLabel("<html>" + text + " <font color='red'>*</font></html>");
        return label;
    }

    public String getViewName() {
        return viewName;
    }

    public void setSignupController(SignupController controller) {
        this.signupController = controller;
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
