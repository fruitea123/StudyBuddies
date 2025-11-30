package entity;

import java.time.LocalDateTime;

public class Notification {

    private final String id;
    private final String userId;
    private final String invitationId; // 可以允许为 null
    private final String type;         // e.g. "INVITATION_ACCEPTED"
    private final String message;
    private final LocalDateTime createdAt;
    private boolean isRead;

    public Notification(String id,
                        String userId,
                        String invitationId,
                        String type,
                        String message,
                        LocalDateTime createdAt,
                        boolean isRead) {
        this.id = id;
        this.userId = userId;
        this.invitationId = invitationId;
        this.type = type;
        this.message = message;
        this.createdAt = createdAt;
        this.isRead = isRead;
    }

    public String getId() {
        return id;
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

    public void markAsRead() {
        this.isRead = true;
    }
}
