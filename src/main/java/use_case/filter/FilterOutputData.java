package use_case.filter;

import entity.Invitation;

import java.util.List;

public class FilterOutputData {

    private final List<Invitation> invites;

    public FilterOutputData(List<Invitation> invites) {
        this.invites = invites;
    }
    public List<Invitation> getInvites() {return invites;}
}
