package data_access;

import entity.Notification;

import java.util.List;
import java.util.Optional;

public interface NotificationDataAccessObject {

    List<Notification> findByUserId(String userId);

    List<Notification> findUnreadByUserId(String userId);

    Optional<Notification> findById(String notificationId);

    void save(Notification notification);

    void saveAll(List<Notification> notifications);
}
