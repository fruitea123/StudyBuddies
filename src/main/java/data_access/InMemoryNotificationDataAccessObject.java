package data_access;

import entity.Notification;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryNotificationDataAccessObject implements NotificationDataAccessObject {

    private final Map<String, Notification> storage = new HashMap<>();

    @Override
    public List<Notification> findByUserId(String userId) {
        return storage.values().stream()
                .filter(n -> n.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Notification> findUnreadByUserId(String userId) {
        return storage.values().stream()
                .filter(n -> n.getUserId().equals(userId) && !n.isRead())
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Notification> findById(String notificationId) {
        return Optional.ofNullable(storage.get(notificationId));
    }

    @Override
    public void save(Notification notification) {
        storage.put(notification.getId(), notification);
    }

    @Override
    public void saveAll(List<Notification> notifications) {
        for (Notification n : notifications) {
            storage.put(n.getId(), n);
        }
    }
}
