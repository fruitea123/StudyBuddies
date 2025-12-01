package data_access;

import entity.Notification;

import java.util.List;
import java.util.Optional;

public interface NotificationDataAccessObject {

    List<Notification> findByUserId(String userId);

    // 如果暂时不想做“只看未读”，这个方法可以先留空实现，或者以后再加
    List<Notification> findUnreadByUserId(String userId);

    Optional<Notification> findById(String notificationId);

    void save(Notification notification);

    void saveAll(List<Notification> notifications);
}
