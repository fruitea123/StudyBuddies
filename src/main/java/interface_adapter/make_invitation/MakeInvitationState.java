package interface_adapter.make_invitation;

// This class only holds the messages for MakeInvitationView
public class MakeInvitationState {
    private String successMessage = "";
    private String errorMessage = "";

    public String getSuccessMessage() { return successMessage; }
    public void setSuccessMessage(String successMessage) { this.successMessage = nonNull(successMessage); }

    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = nonNull(errorMessage); }

    private static String nonNull(String s) { return s == null ? "" : s; }
}