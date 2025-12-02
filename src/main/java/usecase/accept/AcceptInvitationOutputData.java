package usecase.accept;

/**
 * Holds output data for failure cases or messages to show.
 */
public class AcceptInvitationOutputData {
  private final String message;

  /**
     * Creates a new output data object with the given message.
     *
     * @param message human‑readable status or error message to expose.
  */
  public AcceptInvitationOutputData(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}
