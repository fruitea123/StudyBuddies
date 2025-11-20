package use_case.accept;

public class AcceptInvitationInputData {
        private final String username;
        private final String invitationId;

        public AcceptInvitationInputData(String username, String invitationId) {
            this.username = username;
            this.invitationId = invitationId;
        }
        public String getUsername() {
            return username;
        }
        public String getInvitationId() {
            return invitationId;
        }

}