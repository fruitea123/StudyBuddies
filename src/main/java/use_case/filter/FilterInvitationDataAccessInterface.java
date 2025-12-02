package use_case.filter;

import entity.Invitation;

import java.util.List;

public interface FilterInvitationDataAccessInterface {

    public List<Invitation> findAll();
}
