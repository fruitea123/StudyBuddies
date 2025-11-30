package use_case.notifications;

public class ViewNotificationsInputData {

    private final NotificationFilter filter;

    public ViewNotificationsInputData(NotificationFilter filter) {
        this.filter = filter;
    }

    public NotificationFilter getFilter() {
        return filter;
    }
}
