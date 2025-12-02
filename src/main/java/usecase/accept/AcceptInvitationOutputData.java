package usecase.accept;

/**
 * Holds output data for failure cases or messages to show.
 */
public class AcceptInvitationOutputData {
    private final String message;

    public AcceptInvitationOutputData(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
