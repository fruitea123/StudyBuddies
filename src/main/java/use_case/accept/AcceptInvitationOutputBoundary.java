package use_case.accept;

/**
 * Defines methods to handle the output of the AcceptInvitation use case.
 */
public interface AcceptInvitationOutputBoundary {
    /**
     * Called when the use case encounters a failure such as a time conflict or missing session.
     * @param errorMessage the message describing the failure reason
     */
    void prepareFailureView(String errorMessage);

    /**
     * Optional: Called on success if you want to provide any output.
     * Can be omitted or left empty if no output is needed on success.
     */
    default void prepareSuccessView() {
        // no output action required by default
    }
}
