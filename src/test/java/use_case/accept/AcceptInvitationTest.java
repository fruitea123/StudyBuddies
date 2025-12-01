package use_case.accept;

import data_access.MongoAcceptInvitationDAO;

public class AcceptInvitationTest { // accept invitation test
    public static void main(String[] args) {

        // 1) Presenter (for error messages)
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

        // 2) DAO (Mongo implementation of your data access interface)
        AcceptInvitationUserDataAccessInterface dao = new MongoAcceptInvitationDAO();

        // 3) Interactor needs BOTH presenter and dao
        AcceptInvitationInteractor interactor =
                new AcceptInvitationInteractor(presenter, dao);

        // 4) Build input and call use case
        String username = "max";
        String sessionOwner = "john";
        AcceptInvitationInputData input =
                new AcceptInvitationInputData(username, sessionOwner);

        interactor.acceptInvitation(input);

        System.out.println("Accept invitation test complete.");
    }
}
