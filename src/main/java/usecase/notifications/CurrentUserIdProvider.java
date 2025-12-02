package usecase.notifications;

// 一个非常小的接口：只负责告诉用例“当前登录用户的 ID 是谁”
public interface CurrentUserIdProvider {
    String getCurrentUserId();
}
