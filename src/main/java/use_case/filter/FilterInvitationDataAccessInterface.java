package use_case.filter;

import entity.Invitation;

import java.util.List;

public interface FilterInvitationDataAccessInterface {

    List<Invitation> filter_list();
}
