package view;

import interface_adapter.makeinvitation.MakeInvitationController;
import interface_adapter.makeinvitation.MakeInvitationState;
import interface_adapter.makeinvitation.MakeInvitationViewModel;
import interface_adapter.makeinvitation.MakeInvitationBackController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class MakeInvitationView extends JPanel implements PropertyChangeListener {

    private final String viewName = "make invitation";

    private final MakeInvitationViewModel viewModel;
    private MakeInvitationController controller;
    private MakeInvitationBackController backController;

    private final JTextField courseField        = new JTextField(20);
    private final JTextField descriptionField   = new JTextField(30);
    private final JSpinner   dateSpinner        = new JSpinner();
    private final JSpinner   startSpinner       = new JSpinner();
    private final JSpinner   endSpinner         = new JSpinner();
    private final JRadioButton onlineRadioButton    = new JRadioButton("Online");
    private final JRadioButton inPersonRadioButton  = new JRadioButton("In person");
    private final JTextField   locationField    = new JTextField(20);
    private final JSpinner     capacitySpinner  = new JSpinner();
    private final JLabel       messageLabel     = new JLabel(" ");
    private final JButton backButton = new JButton("Back to Profile");
    private final JButton confirmButton = new JButton("Create Invitation");

    public MakeInvitationView(MakeInvitationViewModel viewModel) {
        this.viewModel = viewModel;
        this.viewModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel title = new JLabel("Create Your Invitation");
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.add(title);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(backButton, BorderLayout.WEST);
        topPanel.add(titlePanel, BorderLayout.CENTER);

        JPanel centerColumn = new JPanel();
        centerColumn.setLayout(new BoxLayout(centerColumn, BoxLayout.Y_AXIS));

        JPanel coursePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        coursePanel.add(new JLabel("Course:"));
        coursePanel.add(courseField);

        JPanel descPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        descPanel.add(new JLabel("Description (optional):"));
        descPanel.add(descriptionField);

        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        datePanel.add(new JLabel("Date:"));
        datePanel.add(dateSpinner);

        JPanel timePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        timePanel.add(new JLabel("Start:"));
        timePanel.add(startSpinner);
        timePanel.add(new JLabel("End:"));
        timePanel.add(endSpinner);

        JPanel modePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ButtonGroup modeGroup = new ButtonGroup();
        modeGroup.add(onlineRadioButton);
        modeGroup.add(inPersonRadioButton);
        onlineRadioButton.setSelected(true);
        locationField.setEnabled(false);

        modePanel.add(new JLabel("Mode:"));
        modePanel.add(onlineRadioButton);
        modePanel.add(inPersonRadioButton);
        modePanel.add(new JLabel("Location:"));
        modePanel.add(locationField);

        JPanel capacityPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        capacityPanel.add(new JLabel("Max occupancy:"));
        capacityPanel.add(capacitySpinner);

        centerColumn.add(coursePanel);
        centerColumn.add(descPanel);
        centerColumn.add(datePanel);
        centerColumn.add(timePanel);
        centerColumn.add(modePanel);
        centerColumn.add(capacityPanel);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(confirmButton);

        JPanel messagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        messagePanel.add(messageLabel);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.add(buttonPanel);
        bottomPanel.add(messagePanel);

        add(topPanel, BorderLayout.NORTH);
        add(centerColumn, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        setupSpinners();
        setupModeToggle();

        wireEvents();
        wireBackButton();
    }

    private void setupSpinners() {
        //date spinner
        dateSpinner.setModel(new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH));
        dateSpinner.setEditor(new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd"));

        //time spinner
        startSpinner.setModel(new SpinnerDateModel(new Date(), null, null, Calendar.MINUTE));
        startSpinner.setEditor(new JSpinner.DateEditor(startSpinner, "HH:mm"));


        endSpinner.setModel(new SpinnerDateModel(new Date(), null, null, Calendar.MINUTE));
        endSpinner.setEditor(new JSpinner.DateEditor(endSpinner, "HH:mm"));

        capacitySpinner.setModel(new SpinnerNumberModel(2, 2, 50, 1));
    }

    private void setupModeToggle() {
        onlineRadioButton.addActionListener(e -> locationField.setEnabled(false));
        inPersonRadioButton.addActionListener(e -> locationField.setEnabled(true));
    }

    private void wireEvents() {
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (controller == null) {
                    return;
                }
                messageLabel.setText(" ");

                ZoneId zone = ZoneId.systemDefault();
                LocalDate date  = ((Date) dateSpinner.getValue())
                        .toInstant().atZone(zone).toLocalDate();
                LocalTime start = ((Date) startSpinner.getValue())
                        .toInstant().atZone(zone).toLocalTime();
                LocalTime end   = ((Date) endSpinner.getValue())
                        .toInstant().atZone(zone).toLocalTime();

                String mode = onlineRadioButton.isSelected() ? "ONLINE"
                        : (inPersonRadioButton.isSelected() ? "IN_PERSON" : "");

                //pass the input to controller
                // System.out.println("[VIEW→CTRL] course=" + course + ", date=" + date + ", start=" + start + ", end=" + end);

                controller.onConfirm(
                        courseField.getText(),
                        descriptionField.getText(),
                        date, start, end,
                        mode, locationField.getText(),
                        (Integer) capacitySpinner.getValue()
                );
            }
        });
    }

    private void wireBackButton() {
        backButton.addActionListener(e -> {
            if (backController != null) {
                backController.onBack();
            }
        });
    }

    public void setMakeInvitationController(MakeInvitationController controller) {
        this.controller = controller;
    }

    public void setBackController(MakeInvitationBackController backController) {
        this.backController = backController;
    }

    public String getViewName() {

        return viewModel.getViewName();
    }

    // ViewModel -> update view
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (!"state".equals(evt.getPropertyName())) {
            return;
        }
        MakeInvitationState state = viewModel.getState();

        String ok  = state.getSuccessMessage();
        String bad = state.getErrorMessage();

        if (ok != null && !ok.isBlank()) {
            messageLabel.setForeground(new Color(0, 128, 0));
            messageLabel.setText(ok);
        } else if (bad != null && !bad.isBlank()) {
            messageLabel.setForeground(Color.RED);
            messageLabel.setText(bad);
        } else {
            messageLabel.setText(" ");
        }
    }
}