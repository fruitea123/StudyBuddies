package use_case.accept;

public class AcceptInvitationTest { // accept invitation test
    public static void main(String[] args) {

        // 1. Create a simple presenter for failure messages
        AcceptInvitationOutputBoundary presenter = new AcceptInvitationOutputBoundary() {
            @Override
            public void prepareFailureView(String errorMessage) {
                System.out.println("Failure: " + errorMessage);
            }

            @Override
            public void prepareSuccessView() {
                System.out.println("Success");
            }
        };

        // 2. Create the interactor (this connects to MongoDB inside)
        AcceptInvitationInteractor interactor = new AcceptInvitationInteractor(presenter);

        // 3. Build input data. Right now your interactor expects username + sessionOwner
        String username = "max";        // user accepting the invitation
        String sessionOwner = "john";   // owner of the target session

        AcceptInvitationInputData input =
                new AcceptInvitationInputData(username, sessionOwner);

        // 4. Run the use case
        interactor.acceptInvitation(input);

        System.out.println("Accept invitation test complete.");
    }
}
