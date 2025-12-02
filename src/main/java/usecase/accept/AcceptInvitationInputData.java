package usecase.accept;

/**
 * Holds input data for accepting invitation by particular owner for particular user.
 */
public class AcceptInvitationInputData {
  private final String username;
  private final String sessionOwner;

  /**
     * Creates a new input data object with the given username and sessionOwner.
     *
     * @param username user's username.
     * @param sessionOwner owner's username.
  */
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
