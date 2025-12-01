package view;

import interface_adapter.login.LoginController;
import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * The View for when the user is logging into the program.
 */
public class LoginView extends JPanel implements ActionListener, PropertyChangeListener {

    private final String viewName = "log in";
    private final LoginViewModel loginViewModel;

    private final JTextField usernameInputField = new JTextField(15);
    private final JLabel usernameErrorField = new JLabel();

    private final JPasswordField passwordInputField = new JPasswordField(15);
    private final JLabel passwordErrorField = new JLabel();

    private final JButton logIn;
    private final JButton cancel;
    private final JButton signUpButton; // 新增：Sign up 按钮

    private LoginController loginController = null;

    public LoginView(LoginViewModel loginViewModel) {

        this.loginViewModel = loginViewModel;
        this.loginViewModel.addPropertyChangeListener(this);


        final JLabel title = new JLabel("Log in");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 24f));


        final LabelTextPanel usernameInfo = new LabelTextPanel(
                new JLabel("Username"), usernameInputField);
        final LabelTextPanel passwordInfo = new LabelTextPanel(
                new JLabel("Password"), passwordInputField);

        usernameErrorField.setForeground(Color.RED);
        passwordErrorField.setForeground(Color.RED);

        // ===== 按钮区域 =====
        final JPanel buttons = new JPanel();
        logIn = new JButton("Log in");
        cancel = new JButton("Cancel");
        buttons.add(logIn);
        buttons.add(cancel);

        // ===== 底部 Sign up 区域 =====
        signUpButton = new JButton("Sign up");
        final JPanel signUpPanel = new JPanel();
        signUpPanel.add(new JLabel("Don't have an account?"));
        signUpPanel.add(signUpButton);

        // ===== 表单整体卡片布局 =====
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(new EmptyBorder(20, 40, 20, 40)); // 上下左右空白

        formPanel.add(title);
        formPanel.add(Box.createVerticalStrut(20));

        formPanel.add(usernameInfo);
        formPanel.add(usernameErrorField);
        formPanel.add(Box.createVerticalStrut(10));

        formPanel.add(passwordInfo);
        formPanel.add(passwordErrorField);
        formPanel.add(Box.createVerticalStrut(15));

        formPanel.add(buttons);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(signUpPanel);


        this.setLayout(new GridBagLayout());
        this.add(formPanel, new GridBagConstraints());

        logIn.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(logIn) && loginController != null) {
                            final LoginState currentState = loginViewModel.getState();

                            loginController.execute(
                                    currentState.getUsername(),
                                    currentState.getPassword()
                            );
                        }
                    }
                }
        );

        cancel.addActionListener(this);

        // 新增：Sign up 按钮 → 交给 LoginController 切换到 SignupView
        signUpButton.addActionListener(e -> {
            if (loginController != null) {
                loginController.switchToSignupView();
            }
        });

        // 输入改变时更新 ViewModel 中的 LoginState：沿用你原来的逻辑
        usernameInputField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final LoginState currentState = loginViewModel.getState();
                currentState.setUsername(usernameInputField.getText());
                loginViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                documentListenerHelper();
            }
        });

        passwordInputField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final LoginState currentState = loginViewModel.getState();
                currentState.setPassword(new String(passwordInputField.getPassword()));
                loginViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                documentListenerHelper();
            }
        });
    }

    /**
     * React to a button click that results in evt.
     * @param evt the ActionEvent to react to
     */
    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final LoginState state = (LoginState) evt.getNewValue();
        setFields(state);
        usernameErrorField.setText(state.getLoginError());

    }

    private void setFields(LoginState state) {
        usernameInputField.setText(state.getUsername());

        passwordInputField.setText(state.getPassword());
    }

    public String getViewName() {
        return viewName;
    }

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }
}