package usecase.notifications;

import java.time.LocalDateTime;

public class NotificationSummary {

    private final String notificationId;
    private final String userId;
    private final String invitationId;
    private final String type;
    private final String message;
    private final LocalDateTime createdAt;
    private final boolean isRead;

    public NotificationSummary(String notificationId,
                               String userId,
                               String invitationId,
                               String type,
                               String message,
                               LocalDateTime createdAt,
                               boolean isRead) {
        this.notificationId = notificationId;
        this.userId = userId;
        this.invitationId = invitationId;
        this.type = type;
        this.message = message;
        this.createdAt = createdAt;
        this.isRead = isRead;
    }

    public String getNotificationId() {
        return notificationId;
    }

    public String getUserId() {
        return userId;
    }

    public String getInvitationId() {
        return invitationId;
    }

    public String getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public boolean isRead() {
        return isRead;
    }
}
