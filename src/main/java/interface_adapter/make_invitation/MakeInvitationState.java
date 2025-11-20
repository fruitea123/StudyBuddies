// interface_adapter/make_invitation/MakeInvitationViewState.java
package interface_adapter.make_invitation;

public class MakeInvitationState {
    public final String successMessage;
    public final String errorMessage;
    public final boolean navigateBackToProfile; //saved for switch pages

    public MakeInvitationState(String successMessage, String errorMessage, boolean navigateBackToProfile) {
        this.successMessage = successMessage;
        this.errorMessage = errorMessage;
        this.navigateBackToProfile = navigateBackToProfile;
    }

    public static MakeInvitationState empty() {
        return new MakeInvitationState("", "", false);
    }
}