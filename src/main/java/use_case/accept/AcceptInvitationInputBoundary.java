package use_case.accept;

/**
 * Defines methods to handle the input of the AcceptInvitation use case.
 */
public interface AcceptInvitationInputBoundary {
  /**
     * Called when the accept‑invitation use case receives an input.
     *
     * @param inputData containing the user's and owner's username.
  */
  void acceptInvitation(AcceptInvitationInputData inputData);
}

