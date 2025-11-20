package use_case.accept;

public class AcceptInvitationOutputData {
        private final boolean success;
        private final String message;

        public AcceptInvitationOutputData(boolean success, String message) {
            this.success = success;
            this.message = message;
        }
        public boolean isSuccess() {
            return success;
        }
        public String getMessage() {
            return message;
        }
}
