package use_case.notifications;

import java.util.List;

public class ViewNotificationsOutputData {

    private final List<NotificationSummary> notifications;

    public ViewNotificationsOutputData(List<NotificationSummary> notifications) {
        this.notifications = notifications;
    }

    public List<NotificationSummary> getNotifications() {
        return notifications;
    }
}
