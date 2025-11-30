package use_case.accept;

public class AcceptInvitationInputData {
    private final String username;
    private final String sessionOwner;

    public AcceptInvitationInputData(String username, String sessionOwner) {
        this.username = username;
        this.sessionOwner = sessionOwner;
    }

    public String getUsername() {
        return username;
    }

    public String getSessionOwner() {
        return sessionOwner;
    }
}
