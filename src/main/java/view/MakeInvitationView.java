package view;

import interface_adapter.make_invitation.MakeInvitationController;
import interface_adapter.make_invitation.MakeInvitationViewModel;
import interface_adapter.make_invitation.MakeInvitationState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class MakeInvitationView extends JPanel implements PropertyChangeListener {

    private JPanel rootPanel;
    private JTextField courseField;
    private JTextField descriptionField;
    private JSpinner   dateSpinner;
    private JSpinner   startSpinner;
    private JSpinner   endSpinner;
    private JRadioButton onlineRadioButton;
    private JRadioButton inPersonRadioButton;
    private JTextField   locationField;
    private JSpinner     capacitySpinner;
    private JButton      confirmButton;
    private JLabel       messageLabel;

    private final MakeInvitationViewModel vm;

    public MakeInvitationView(MakeInvitationController controller,
                              MakeInvitationViewModel vm) {
        this.vm = vm;

        setLayout(new BorderLayout());
        add(rootPanel, BorderLayout.CENTER);

        setupSpinners();
        setupModeToggle();

        vm.addPropertyChangeListener(this);

        wireEvents(controller);
    }

    private void wireEvents(MakeInvitationController controller) {
        confirmButton.addActionListener(run(e -> {
            messageLabel.setText(" ");

            ZoneId zone = ZoneId.systemDefault();
            LocalDate date  = ((Date) dateSpinner.getValue()).toInstant().atZone(zone).toLocalDate();
            LocalTime start = ((Date) startSpinner.getValue()).toInstant().atZone(zone).toLocalTime();
            LocalTime end   = ((Date) endSpinner.getValue()).toInstant().atZone(zone).toLocalTime();

            String mode = onlineRadioButton.isSelected() ? "ONLINE"
                    : (inPersonRadioButton.isSelected() ? "IN_PERSON" : "");

            controller.onConfirm(
                    courseField.getText(),
                    descriptionField.getText(),
                    date, start, end,
                    mode, locationField.getText(),
                    (Integer) capacitySpinner.getValue()
            );
        }));
    }

    private void setupSpinners() {
        dateSpinner.setModel(new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH));
        dateSpinner.setEditor(new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd"));

        startSpinner.setModel(new SpinnerDateModel(new Date(), null, null, Calendar.MINUTE));
        startSpinner.setEditor(new JSpinner.DateEditor(startSpinner, "HH:mm"));

        endSpinner.setModel(new SpinnerDateModel(new Date(), null, null, Calendar.MINUTE));
        endSpinner.setEditor(new JSpinner.DateEditor(endSpinner, "HH:mm"));

        capacitySpinner.setModel(new SpinnerNumberModel(2, 2, 50, 1));
    }

    private void setupModeToggle() {
        ButtonGroup g = new ButtonGroup();
        g.add(onlineRadioButton);
        g.add(inPersonRadioButton);
        onlineRadioButton.setSelected(true);
        locationField.setEnabled(false);

        onlineRadioButton.addActionListener(e -> locationField.setEnabled(false));
        inPersonRadioButton.addActionListener(e -> locationField.setEnabled(true));
    }

    private ActionListener run(ActionListener a) { return a; }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (!MakeInvitationViewModel.STATE_PROPERTY.equals(evt.getPropertyName())) {
            return;
        }
        MakeInvitationState s = vm.getState();

        String ok = s.getSuccessMessage();
        String bad = s.getErrorMessage();

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