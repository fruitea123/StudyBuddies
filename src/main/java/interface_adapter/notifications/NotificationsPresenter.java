package interface_adapter.notifications;

import usecase.notifications.NotificationSummary;
import usecase.notifications.ViewNotificationsOutputBoundary;
import usecase.notifications.ViewNotificationsOutputData;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class NotificationsPresenter implements ViewNotificationsOutputBoundary {

    private final NotificationsViewModel viewModel;

    public NotificationsPresenter(NotificationsViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void present(ViewNotificationsOutputData outputData) {
        // 1. 先把 loading 关掉，清空错误
        viewModel.setLoading(false);
        viewModel.setErrorMessage(null);

        // 2. 把 use case 的通知列表转换成 ViewModel 的列表
        List<NotificationItemViewModel> itemViewModels = new ArrayList<>();
        for (NotificationSummary n : outputData.getNotifications()) {
            NotificationItemViewModel item = new NotificationItemViewModel();
            item.setNotificationId(n.getNotificationId());
            item.setInvitationId(n.getInvitationId());
            item.setMessage(n.getMessage());
            item.setRead(n.isRead());
            item.setCreatedAtDisplay(formatTime(n.getCreatedAt()));

            itemViewModels.add(item);
        }

        viewModel.setItems(itemViewModels);

        // 3. 根据是否为空设置 infoMessage
        if (itemViewModels.isEmpty()) {
            viewModel.setInfoMessage("You have no notifications yet.");
        } else {
            viewModel.setInfoMessage(null);
        }


    }

    private String formatTime(LocalDateTime time) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return time.format(formatter);
    }
}
