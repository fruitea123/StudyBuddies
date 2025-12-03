package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupState;
import interface_adapter.signup.SignupViewModel;
import use_case.signup.SignupInputBoundary;
import use_case.signup.SignupInputData;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;

/** Signup View
 */

public class SignUpView extends JPanel implements PropertyChangeListener {

    private SignupController signupController;
    private SignupViewModel signupViewModel;

    private final String viewName = "sign up";

    private final JTextField emailField;
    private final JPasswordField passwordField;
    private final JPasswordField repeatPasswordField;
    private final JTextField firstNameField;
    private final JTextField lastNameField;

    private final JComboBox<String> dayBox;
    private final JComboBox<String> monthBox;
    private final JComboBox<String> yearBox;

    private final JComboBox<String> numProgramsBox;
    private final JTextField p1Field;
    private final JTextField p2Field;
    private final JTextField p3Field;

    private JComboBox<String> iconBox;
    private JTextArea descArea;

    private JLabel errorLabel;

    public SignUpView(SignupViewModel signupViewModel) {

        this.signupViewModel = signupViewModel;
        this.signupViewModel.addPropertyChangeListener(this);

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
        emailField = new JTextField();
        emailField.setBounds(50, 55, 450, 30);
        mainPanel.add(emailField);

        JLabel passwordLabel = requiredLabel("Password");
        passwordLabel.setBounds(50, 105, 200, 25);
        mainPanel.add(passwordLabel);
        passwordField = new JPasswordField();
        passwordField.setBounds(50, 130, 220, 30);
        mainPanel.add(passwordField);

        JLabel repeatPasswordLabel = requiredLabel("Repeat Password");
        repeatPasswordLabel.setBounds(280, 105, 200, 25);
        mainPanel.add(repeatPasswordLabel);
        repeatPasswordField = new JPasswordField();
        repeatPasswordField.setBounds(280, 130, 220, 30);
        mainPanel.add(repeatPasswordField);

        JLabel firstNameLabel = requiredLabel("First Name");
        firstNameLabel.setBounds(50, 180, 200, 25);
        mainPanel.add(firstNameLabel);
        firstNameField = new JTextField();
        firstNameField.setBounds(50, 205, 220, 30);
        mainPanel.add(firstNameField);

        JLabel lastNameLabel = requiredLabel("Last Name");
        lastNameLabel.setBounds(280, 180, 200, 25);
        mainPanel.add(lastNameLabel);
        lastNameField = new JTextField();
        lastNameField.setBounds(280, 205, 220, 30);
        mainPanel.add(lastNameField);

        JLabel birthdayLabel = requiredLabel("Birthday");
        birthdayLabel.setBounds(50, 255, 200, 25);
        mainPanel.add(birthdayLabel);

        String[] days = new String[32];
        days[0] = "Day";
        for (int i = 1; i <= 31; i++) {days[i] = String.valueOf(i);}
        dayBox = new JComboBox<>(days);
        dayBox.setBounds(50, 280, 140, 30);
        mainPanel.add(dayBox);

        String[] months = {"Month", "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        monthBox = new JComboBox<>(months);
        monthBox.setBounds(200, 280, 140, 30);
        mainPanel.add(monthBox);

        String[] years = new String[(2015 - 1960 + 1) + 1];  // +1 for "Year"
        years[0] = "Year";
        int index = 1;
        for (int i = 1960; i <= 2015; i++) {
            years[index++] = String.valueOf(i);
        }
        yearBox = new JComboBox<>(years);
        yearBox.setBounds(350, 280, 140, 30);
        mainPanel.add(yearBox);

        JLabel numProgramsLabel = requiredLabel("Number of Programs Enrolled");
        numProgramsLabel.setBounds(50, 330, 300, 25);
        mainPanel.add(numProgramsLabel);

        numProgramsBox = new JComboBox<>(new String[]{" ", "1", "2", "3"});
        numProgramsBox.setBounds(50, 355, 80, 30);
        mainPanel.add(numProgramsBox);

        JLabel p1Label = requiredLabel("1st Program");
        p1Label.setBounds(50, 400, 200, 25);
        mainPanel.add(p1Label);
        p1Field = new JTextField();
        p1Field.setBounds(50, 425, 140, 30);
        mainPanel.add(p1Field);

        JLabel p2Label = requiredLabel("2nd Program");
        p2Label.setBounds(210, 400, 200, 25);
        mainPanel.add(p2Label);
        p2Field = new JTextField();
        p2Field.setBounds(210, 425, 140, 30);
        mainPanel.add(p2Field);

        JLabel p3Label = requiredLabel("3rd Program");
        p3Label.setBounds(370, 400, 200, 25);
        mainPanel.add(p3Label);
        p3Field = new JTextField();
        p3Field.setBounds(370, 425, 140, 30);
        mainPanel.add(p3Field);

        // disable because not all users are enrolled in three courses
        p1Field.setEnabled(false);
        p2Field.setEnabled(false);
        p3Field.setEnabled(false);

        numProgramsBox.addActionListener(e -> {
            String val = (String) numProgramsBox.getSelectedItem();
            int n = val.equals(" ") ? 0 : Integer.parseInt(val);
            p1Field.setEnabled(n >= 1);
            p2Field.setEnabled(n >= 2);
            p3Field.setEnabled(n >= 3);
        });

        JLabel iconLabel = new JLabel("Choose an icon (optional)");
        iconLabel.setBounds(50, 480, 300, 25);
        mainPanel.add(iconLabel);

        String[] iconNames = {" ", "cat", "chicken", "gorilla", "meerkat", "panda", "rabbit"};
        iconBox = new JComboBox<>(iconNames);
        iconBox.setBounds(50, 505, 200, 30);
        mainPanel.add(iconBox);

        JLabel descLabel = new JLabel("Description (optional)");
        descLabel.setBounds(50, 550, 300, 25);
        mainPanel.add(descLabel);

        descArea = new JTextArea();
        JScrollPane descScroll = new JScrollPane(descArea);
        descScroll.setBounds(50, 575, 450, 150);
        mainPanel.add(descScroll);

        errorLabel = new JLabel("");
        errorLabel.setForeground(Color.RED);
        errorLabel.setBounds(50, 5, 500, 25);
        mainPanel.add(errorLabel);

        JButton createButton = new JButton("Create Account");
        createButton.setBounds(200, 740, 150, 35);
        mainPanel.add(createButton);

        createButton.addActionListener(e -> handleCreateAccount());

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


        loginButton.addActionListener(e -> {
            if (signupController != null) {
                signupController.switchToLoginView();
            }
        });

    }


    private void handleCreateAccount() {

        SignupState state = signupViewModel.getState();

        state.setEmail(emailField.getText());
        state.setPassword(new String(passwordField.getPassword()));
        state.setRepeatPassword(new String(repeatPasswordField.getPassword()));
        state.setFirstName(firstNameField.getText());
        state.setLastName(lastNameField.getText());

        try {
            int day = Integer.parseInt((String) dayBox.getSelectedItem());
            int month = monthBox.getSelectedIndex();
            int year = Integer.parseInt((String) yearBox.getSelectedItem());
            state.setDateOfBirth(LocalDate.of(year, month, day));
        } catch (Exception exception) {
            state.setDateOfBirth(null);
        }

        int num = parseIntOrZero((String) numProgramsBox.getSelectedItem());
        state.setNumPrograms(num);

        state.setProgram1(p1Field.getText());
        state.setProgram2(p2Field.getText());
        state.setProgram3(p3Field.getText());

        // Icon + description
        state.setIcon((String) iconBox.getSelectedItem());
        state.setDescription(descArea.getText());

        // Push updated state to ViewModel
        signupViewModel.setState(state);

        // Call controller
        if (signupController != null) {
            signupController.execute(state);
        }
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        SignupState state = signupViewModel.getState();

        String[] errors = {
                state.getEmailError(),
                state.getPasswordError(),
                state.getRepeatPasswordError(),
                state.getFirstNameError(),
                state.getLastNameError(),
                state.getDateOfBirthError(),
                state.getNumProgramsError(),
                state.getProgram1Error(),
                state.getProgram2Error(),
                state.getProgram3Error(),
                state.getIconError(),
                state.getDescriptionError()
        };

        String error = "";

        for (String err : errors) {
            if (err != null && !err.isEmpty()) {
                error = err;
                break;
            }
        }
        errorLabel.setText(error);
    }

    private int parseIntOrZero(String s) {
        try { return Integer.parseInt(s.trim()); }
        catch (Exception e) { return 0; }
    }

    private JLabel requiredLabel(String text) {
        return new JLabel("<html>" + text + " <font color='red'>*</font></html>");
    }

    public String getViewName() {
        return viewName;
    }

    public void setSignupController(SignupController controller) {
        this.signupController = controller;
    }

    public static void main(String[] args) {

        SignupViewModel signupViewModel = new SignupViewModel();

        JFrame frame = new JFrame("Sign Up Form");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        SignUpView view = new SignUpView(signupViewModel);

        frame.add(view);
        frame.setSize(450, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
