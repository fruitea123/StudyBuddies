package interface_adapter.notifications;

import usecase.notifications.NotificationFilter;
import usecase.notifications.ViewNotificationsInputBoundary;
import usecase.notifications.ViewNotificationsInputData;

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
