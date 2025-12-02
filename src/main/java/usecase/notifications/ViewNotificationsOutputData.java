package usecase.notifications;

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
