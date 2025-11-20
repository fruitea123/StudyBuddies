package view;

import interface_adapter.make_invitation.MakeInvitationController;
import interface_adapter.make_invitation.MakeInvitationState;
import interface_adapter.make_invitation.MakeInvitationViewModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class MakeInvitationView extends JPanel implements PropertyChangeListener {

    private final MakeInvitationController controller;
    private final MakeInvitationViewModel vm;

    private JTextField courseField;
    private JTextField descriptionField;
    private JSpinner dateSpinner;
    private JSpinner startSpinner;
    private JSpinner endSpinner;
    private JRadioButton onlineRadioButton;
    private JRadioButton inPersonRadioButton;
    private JTextField locationField;
    private JSpinner capacitySpinner;
    private JButton confirmButton;
    private JLabel messageLabel;

    public MakeInvitationView(MakeInvitationController controller, MakeInvitationViewModel vm) {
        this.controller = controller;
        this.vm = vm;
        this.vm.addPropertyChangeListener(this);

        buildUI();
        wireEvents();
    }

    private void buildUI() {
        setLayout(new BorderLayout());
        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8, 12, 8, 12);
        c.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        courseField = new JTextField(24);
        addRow(form, c, row++, new JLabel("Course"), courseField);

        descriptionField = new JTextField(24);
        addRow(form, c, row++, new JLabel("Description"), descriptionField);

        dateSpinner = new JSpinner(new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH));
        dateSpinner.setEditor(new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd"));
        addRow(form, c, row++, new JLabel("Date"), dateSpinner);

        startSpinner = new JSpinner(new SpinnerDateModel(new Date(), null, null, Calendar.MINUTE));
        startSpinner.setEditor(new JSpinner.DateEditor(startSpinner, "HH:mm"));
        addRow(form, c, row++, new JLabel("Start time"), startSpinner);

        endSpinner = new JSpinner(new SpinnerDateModel(new Date(), null, null, Calendar.MINUTE));
        endSpinner.setEditor(new JSpinner.DateEditor(endSpinner, "HH:mm"));
        addRow(form, c, row++, new JLabel("End time"), endSpinner);

        onlineRadioButton = new JRadioButton("Online", true);
        inPersonRadioButton = new JRadioButton("In person");
        ButtonGroup g = new ButtonGroup();
        g.add(onlineRadioButton);
        g.add(inPersonRadioButton);

        JPanel modePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        locationField = new JTextField(16);
        locationField.setEnabled(false);
        modePanel.add(onlineRadioButton);
        modePanel.add(inPersonRadioButton);
        modePanel.add(new JLabel("Location:"));
        modePanel.add(locationField);
        addRow(form, c, row++, new JLabel("Mode/Location"), modePanel);

        capacitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 50, 1));
        addRow(form, c, row++, new JLabel("Max occupancy"), capacitySpinner);

        confirmButton = new JButton("Confirm");
        c.gridx = 0; c.gridy = row; c.gridwidth = 2;
        form.add(confirmButton, c);

        add(form, BorderLayout.CENTER);

        messageLabel = new JLabel(" ");
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(messageLabel, BorderLayout.SOUTH);
    }

    private void addRow(JPanel panel, GridBagConstraints c, int row, JComponent label, JComponent comp) {
        c.gridx = 0; c.gridy = row; c.gridwidth = 1; c.weightx = 0.0;
        panel.add(label, c);
        c.gridx = 1; c.gridy = row; c.gridwidth = 1; c.weightx = 1.0;
        panel.add(comp, c);
    }

    private void wireEvents() {
        onlineRadioButton.addActionListener(e -> locationField.setEnabled(false));
        inPersonRadioButton.addActionListener(e -> locationField.setEnabled(true));

        confirmButton.addActionListener(e -> {
            ZoneId zone = ZoneId.systemDefault();

            Date d = (Date) dateSpinner.getValue();
            LocalDate date = d.toInstant().atZone(zone).toLocalDate();

            Date s = (Date) startSpinner.getValue();
            LocalTime start = s.toInstant().atZone(zone).toLocalTime();

            Date t = (Date) endSpinner.getValue();
            LocalTime end = t.toInstant().atZone(zone).toLocalTime();

            String mode = onlineRadioButton.isSelected() ? "ONLINE" : "IN_PERSON";
            Integer occupancy = (Integer) capacitySpinner.getValue();

            controller.onConfirm(
                    courseField.getText(),
                    descriptionField.getText(),
                    date, start, end,
                    mode, locationField.getText(),
                    occupancy
            );
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (!"state".equals(evt.getPropertyName())) return;
        MakeInvitationState s = vm.getState();

        if (!s.successMessage.isBlank()) {
            messageLabel.setForeground(new Color(0,128,0));
            messageLabel.setText(s.successMessage);
        } else if (!s.errorMessage.isBlank()) {
            messageLabel.setForeground(Color.RED);
            messageLabel.setText(s.errorMessage);
        } else {
            messageLabel.setText(" ");
        }

    }
}