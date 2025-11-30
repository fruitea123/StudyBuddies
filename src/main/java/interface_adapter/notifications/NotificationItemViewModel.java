package interface_adapter.notifications;

public class NotificationItemViewModel {

    private String notificationId;
    private String invitationId;
    private String message;
    private String createdAtDisplay;
    private boolean isRead;

    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    public String getInvitationId() {
        return invitationId;
    }

    public void setInvitationId(String invitationId) {
        this.invitationId = invitationId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCreatedAtDisplay() {
        return createdAtDisplay;
    }

    public void setCreatedAtDisplay(String createdAtDisplay) {
        this.createdAtDisplay = createdAtDisplay;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }
}
