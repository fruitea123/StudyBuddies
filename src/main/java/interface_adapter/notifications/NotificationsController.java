package interface_adapter.notifications;

import use_case.notifications.NotificationFilter;
import use_case.notifications.ViewNotificationsInputBoundary;
import use_case.notifications.ViewNotificationsInputData;

public class NotificationsController {

    private final ViewNotificationsInputBoundary viewNotificationsInteractor;

    public NotificationsController(ViewNotificationsInputBoundary viewNotificationsInteractor) {
        this.viewNotificationsInteractor = viewNotificationsInteractor;
    }

    public void loadNotifications(NotificationFilter filter) {
        ViewNotificationsInputData inputData = new ViewNotificationsInputData(filter);
        viewNotificationsInteractor.execute(inputData);
    }
}
