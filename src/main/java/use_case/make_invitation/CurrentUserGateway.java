package use_case.make_invitation;

import entity.User;

public interface CurrentUserGateway {
    @return
    User getCurrentUser();
}
