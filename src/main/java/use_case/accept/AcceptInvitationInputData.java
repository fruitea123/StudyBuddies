package use_case.accept;

/**
 * Holds input data for accepting invitation by particular owner for particular user.
 */
public class AcceptInvitationInputData {
  private final String username;
  private final String ownerName;

  /**
     * Creates a new input data object with the given username and sessionOwner.
     *
     * @param username user's username.
     * @param ownerName invitation's ID.
  */
  public AcceptInvitationInputData(String ownerName, String username) {
    this.username = username;
    this.ownerName = ownerName;
  }

  public String getUsername() {
    return username;
  }

  public String getOwnerName() {
    return ownerName;
  }
}
