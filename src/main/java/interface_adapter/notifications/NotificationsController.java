package interface_adapter.notifications;

import use_case.notifications.NotificationFilter;
import use_case.notifications.ViewNotificationsInputBoundary;
import use_case.notifications.ViewNotificationsInputData;

import interface_adapter.ViewManagerModel;
import view.forms.MyInvitationsView;

public class NotificationsController {

    private final ViewNotificationsInputBoundary viewNotificationsInteractor;
    private final ViewManagerModel viewManagerModel;
    private final MyInvitationsView myInvitationsView;

    public NotificationsController(ViewNotificationsInputBoundary viewNotificationsInteractor,
                                   ViewManagerModel viewManagerModel, MyInvitationsView myInvitationsView) {
        this.viewNotificationsInteractor = viewNotificationsInteractor;
        this.viewManagerModel = viewManagerModel;
        this.myInvitationsView = myInvitationsView;

    }

    public void loadNotifications(NotificationFilter filter) {
        ViewNotificationsInputData inputData = new ViewNotificationsInputData(filter);
        viewNotificationsInteractor.execute(inputData);
    }


    public void goHome() {
        viewManagerModel.setState(myInvitationsView.getViewName());
        viewManagerModel.firePropertyChange();
    }
}
