package use_case.notifications;

import data_access.NotificationDataAccessObject;
import entity.Notification;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ViewNotificationsInteractorTest {

    // ======= 测试用假的 DAO / 当前用户 / Presenter =======

    private static class FakeNotificationDAO implements NotificationDataAccessObject {

        List<Notification> allNotifications = new ArrayList<>();
        List<Notification> unreadNotifications = new ArrayList<>();

        boolean findAllCalled = false;
        boolean findUnreadCalled = false;

        @Override
        public List<Notification> findByUserId(String userId) {
            findAllCalled = true;
            return new ArrayList<>(allNotifications);
        }

        @Override
        public List<Notification> findUnreadByUserId(String userId) {
            findUnreadCalled = true;
            return new ArrayList<>(unreadNotifications);
        }

        @Override
        public Optional<Notification> findById(String notificationId) {
            return Optional.empty(); // not needed for these tests
        }

        @Override
        public void save(Notification notification) {
            // not needed
        }

        @Override
        public void saveAll(List<Notification> notifications) {
            // not needed
        }
    }

    private static class FakeCurrentUserIdProvider implements CurrentUserIdProvider {

        private final String userId;

        FakeCurrentUserIdProvider(String userId) {
            this.userId = userId;
        }

        @Override
        public String getCurrentUserId() {
            return userId;
        }
    }

    private static class FakePresenter implements ViewNotificationsOutputBoundary {

        ViewNotificationsOutputData lastOutputData;

        @Override
        public void present(ViewNotificationsOutputData outputData) {
            this.lastOutputData = outputData;
        }
    }

    // ======= 测试 1：ALL 分支 + 排序 + 字段映射 =======

    @Test
    void execute_allFilter_sortsAndMapsCorrectly() {
        // Arrange
        FakeNotificationDAO dao = new FakeNotificationDAO();
        FakeCurrentUserIdProvider currentUser = new FakeCurrentUserIdProvider("user-1");
        FakePresenter presenter = new FakePresenter();

        LocalDateTime t1 = LocalDateTime.of(2025, 1, 1, 10, 0);
        LocalDateTime t2 = LocalDateTime.of(2025, 1, 2, 10, 0);

        Notification n1 = new Notification(
                "n1", "user-1", "inv1",
                "TYPE_A", "message1", t1, false
        );
        Notification n2 = new Notification(
                "n2", "user-1", "inv2",
                "TYPE_B", "message2", t2, true
        );

        dao.allNotifications.add(n1);
        dao.allNotifications.add(n2);

        ViewNotificationsInteractor interactor =
                new ViewNotificationsInteractor(dao, currentUser, presenter);

        ViewNotificationsInputData input =
                new ViewNotificationsInputData(NotificationFilter.ALL);

        // Act
        interactor.execute(input);

        // Assert
        assertTrue(dao.findAllCalled);
        assertFalse(dao.findUnreadCalled);
        assertNotNull(presenter.lastOutputData);

        List<NotificationSummary> summaries = presenter.lastOutputData.getNotifications();
        assertEquals(2, summaries.size());

        // 排序：t2 比 t1 新，所以 n2 应该排在前面
        assertEquals("n2", summaries.get(0).getNotificationId());
        assertEquals("n1", summaries.get(1).getNotificationId());

        // 字段映射是否正确
        NotificationSummary first = summaries.get(0);
        assertEquals("user-1", first.getUserId());
        assertEquals("inv2", first.getInvitationId());
        assertEquals("TYPE_B", first.getType());
        assertEquals("message2", first.getMessage());
        assertEquals(t2, first.getCreatedAt());
        assertTrue(first.isRead());
    }

    // ======= 测试 2：UNREAD 分支只调用 findUnreadByUserId =======

    @Test
    void execute_unreadFilter_usesUnreadDaoMethod() {
        // Arrange
        FakeNotificationDAO dao = new FakeNotificationDAO();
        FakeCurrentUserIdProvider currentUser = new FakeCurrentUserIdProvider("user-1");
        FakePresenter presenter = new FakePresenter();

        LocalDateTime t = LocalDateTime.of(2025, 1, 3, 10, 0);
        Notification unread = new Notification(
                "n3", "user-1", "inv3",
                "TYPE_C", "unread message", t, false
        );
        dao.unreadNotifications.add(unread);

        ViewNotificationsInteractor interactor =
                new ViewNotificationsInteractor(dao, currentUser, presenter);

        ViewNotificationsInputData input =
                new ViewNotificationsInputData(NotificationFilter.UNREAD);

        // Act
        interactor.execute(input);

        // Assert
        assertFalse(dao.findAllCalled);
        assertTrue(dao.findUnreadCalled);
        assertNotNull(presenter.lastOutputData);

        List<NotificationSummary> summaries = presenter.lastOutputData.getNotifications();
        assertEquals(1, summaries.size());
        assertEquals("n3", summaries.get(0).getNotificationId());
        assertFalse(summaries.get(0).isRead());
    }

    // ======= 测试 3：没有通知时返回空列表 =======

    @Test
    void execute_noNotifications_returnsEmptyList() {
        // Arrange
        FakeNotificationDAO dao = new FakeNotificationDAO();
        FakeCurrentUserIdProvider currentUser = new FakeCurrentUserIdProvider("user-1");
        FakePresenter presenter = new FakePresenter();

        ViewNotificationsInteractor interactor =
                new ViewNotificationsInteractor(dao, currentUser, presenter);

        ViewNotificationsInputData input =
                new ViewNotificationsInputData(NotificationFilter.ALL);

        // Act
        interactor.execute(input);

        // Assert
        assertNotNull(presenter.lastOutputData);
        List<NotificationSummary> summaries = presenter.lastOutputData.getNotifications();
        assertTrue(summaries.isEmpty());
    }
}
