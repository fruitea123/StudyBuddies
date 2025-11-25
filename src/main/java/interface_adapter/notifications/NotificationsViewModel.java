package interface_adapter.notifications;

import use_case.notifications.NotificationFilter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class NotificationsViewModel {

    // 看你们项目的其他 ViewModel 用什么 property name，可以照抄
    public static final String STATE_PROPERTY = "notificationsState";

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    private List<NotificationItemViewModel> items = new ArrayList<>();
    private boolean isLoading = false;
    private String errorMessage;
    private String infoMessage;
    private NotificationFilter currentFilter = NotificationFilter.ALL;

    public List<NotificationItemViewModel> getItems() {
        return items;
    }

    public void setItems(List<NotificationItemViewModel> items) {
        this.items = items;
        fireStateChanged();
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
        fireStateChanged();
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        fireStateChanged();
    }

    public String getInfoMessage() {
        return infoMessage;
    }

    public void setInfoMessage(String infoMessage) {
        this.infoMessage = infoMessage;
        fireStateChanged();
    }

    public NotificationFilter getCurrentFilter() {
        return currentFilter;
    }

    public void setCurrentFilter(NotificationFilter currentFilter) {
        this.currentFilter = currentFilter;
        fireStateChanged();
    }

    // ==== PropertyChangeSupport 相关 ====

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }

    // 这招比较偷懒：每次更新都 fire 一次“整体状态变了”
    private void fireStateChanged() {
        support.firePropertyChange(STATE_PROPERTY, null, null);
    }
}
