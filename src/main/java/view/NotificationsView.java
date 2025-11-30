package view;

import interface_adapter.notifications.NotificationItemViewModel;
import interface_adapter.notifications.NotificationsController;
import interface_adapter.notifications.NotificationsViewModel;
import use_case.notifications.NotificationFilter;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class NotificationsView extends JPanel implements PropertyChangeListener {

    private final NotificationsViewModel viewModel;
    private NotificationsController controller;

    private final String viewName = "notifications"; // 用来给 ViewManager 切换


    private JLabel titleLabel;
    private JLabel infoLabel;
    private JLabel errorLabel;
    private JButton allFilterButton;
    private JButton unreadFilterButton;
    private JButton refreshButton;
    private JPanel listPanel;

    public NotificationsView(NotificationsViewModel viewModel) {
        this.viewModel = viewModel;

        viewModel.addPropertyChangeListener(this);

        initComponents();
        layoutComponents();

    }

    public void setNotificationsController(NotificationsController controller) {
        this.controller = controller;
        bindListeners();
    }

    public String getViewName() {
        return viewName;
    }

    private void initComponents() {
        titleLabel = new JLabel("Notifications");
        infoLabel = new JLabel();
        errorLabel = new JLabel();
        errorLabel.setForeground(Color.RED);

        allFilterButton = new JButton("All");
        unreadFilterButton = new JButton("Unread");
        refreshButton = new JButton("Refresh");

        listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
    }

    private void layoutComponents() {
        setLayout(new BorderLayout());
        JPanel top = new JPanel(new BorderLayout());
        top.add(titleLabel, BorderLayout.WEST);

        JPanel btnPanel = new JPanel();
        btnPanel.add(allFilterButton);
        btnPanel.add(unreadFilterButton);
        btnPanel.add(refreshButton);
        top.add(btnPanel, BorderLayout.EAST);

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(listPanel), BorderLayout.CENTER);

        JPanel bottom = new JPanel(new GridLayout(2, 1));
        bottom.add(infoLabel);
        bottom.add(errorLabel);
        add(bottom, BorderLayout.SOUTH);
    }

    private void bindListeners() {
        if (controller == null) return; // 防御式，避免 NPE

        allFilterButton.addActionListener(e -> {
            viewModel.setCurrentFilter(NotificationFilter.ALL);
            controller.loadNotifications(NotificationFilter.ALL);
        });

        unreadFilterButton.addActionListener(e -> {
            viewModel.setCurrentFilter(NotificationFilter.UNREAD);
            controller.loadNotifications(NotificationFilter.UNREAD);
        });

        refreshButton.addActionListener(e -> {
            controller.loadNotifications(viewModel.getCurrentFilter());
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        refreshFromViewModel();
    }

    private void refreshFromViewModel() {
        infoLabel.setText(viewModel.getInfoMessage() == null ? "" : viewModel.getInfoMessage());
        errorLabel.setText(viewModel.getErrorMessage() == null ? "" : viewModel.getErrorMessage());

        listPanel.removeAll();
        for (NotificationItemViewModel item : viewModel.getItems()) {
            listPanel.add(buildRow(item));
        }
        listPanel.revalidate();
        listPanel.repaint();
    }

    private JPanel buildRow(NotificationItemViewModel item) {
        JPanel row = new JPanel(new BorderLayout());
        String prefix = item.isRead() ? "" : "[NEW] ";
        JLabel msg = new JLabel(prefix + item.getMessage());
        row.add(msg, BorderLayout.CENTER);
        JLabel time = new JLabel(item.getCreatedAtDisplay());
        row.add(time, BorderLayout.EAST);
        return row;
    }
}
