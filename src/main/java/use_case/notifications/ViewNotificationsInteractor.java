package use_case.notifications;

import data_access.NotificationDataAccessObject;
import entity.Notification;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ViewNotificationsInteractor implements ViewNotificationsInputBoundary {

    private final NotificationDataAccessObject notificationDAO;
    private final CurrentUserIdProvider currentUserIdProvider;
    private final ViewNotificationsOutputBoundary presenter;

    public ViewNotificationsInteractor(NotificationDataAccessObject notificationDAO,
                                       CurrentUserIdProvider currentUserIdProvider,
                                       ViewNotificationsOutputBoundary presenter) {
        this.notificationDAO = notificationDAO;
        this.currentUserIdProvider = currentUserIdProvider;
        this.presenter = presenter;
    }

    @Override
    public void execute(ViewNotificationsInputData inputData) {
        String currentUserId = currentUserIdProvider.getCurrentUserId();

        // 1. 根据 filter 查询通知
        List<Notification> notifications;
        NotificationFilter filter = inputData.getFilter();
        if (filter == NotificationFilter.UNREAD) {
            notifications = notificationDAO.findUnreadByUserId(currentUserId);
        } else {
            // 默认：ALL
            notifications = notificationDAO.findByUserId(currentUserId);
        }

        // 2. 按时间倒序排序（最新在前）
        notifications.sort(Comparator.comparing(Notification::getCreatedAt).reversed());

        // 3. 映射为 NotificationSummary 列表
        List<NotificationSummary> summaries = new ArrayList<>();
        for (Notification n : notifications) {
            NotificationSummary summary = new NotificationSummary(
                    n.getId(),
                    n.getUserId(),
                    n.getInvitationId(),
                    n.getType(),
                    n.getMessage(),
                    n.getCreatedAt(),
                    n.isRead()
            );
            summaries.add(summary);
        }

        // 4. 打包成 OutputData，交给 Presenter
        ViewNotificationsOutputData outputData = new ViewNotificationsOutputData(summaries);
        presenter.present(outputData);
    }
}
