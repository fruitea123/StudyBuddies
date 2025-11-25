package interface_adapter.notifications;

import use_case.notifications.NotificationFilter;
import use_case.notifications.NotificationSummary;
import use_case.notifications.ViewNotificationsOutputBoundary;
import use_case.notifications.ViewNotificationsOutputData;

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

        // 若你们项目习惯用一个显式方法，比如 viewModel.firePropertyChanged()，也可以在这里再调一次
        // viewModel.fireStateChanged(); // 如果你自己额外加了这个 public 方法
    }

    private String formatTime(LocalDateTime time) {
        // 简单一点就 toString，也可以用 formatter
        // return time.toString();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return time.format(formatter);
    }
}
