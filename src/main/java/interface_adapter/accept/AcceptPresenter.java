package interface_adapter.accept;

import interface_adapter.ViewManagerModel;
import interface_adapter.study_pool.StudyPoolViewModel;
import interface_adapter.study_pool.StudyPoolState;
import use_case.accept.AcceptInvitationOutputBoundary;

public class AcceptPresenter implements AcceptInvitationOutputBoundary {

    private final StudyPoolViewModel studyPoolViewModel;
    private final ViewManagerModel viewManagerModel;

    public AcceptPresenter(StudyPoolViewModel studyPoolViewModel,
                           ViewManagerModel viewManagerModel) {
        this.studyPoolViewModel = studyPoolViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(String successMessage) {
        // After the use case has updated the repository,
        // reload invitations into StudyPoolState before calling this.
        StudyPoolState studyPoolState = studyPoolViewModel.getState();
        // e.g., studyPoolState.setInvitations(updatedInvites);

        studyPoolViewModel.firePropertyChange();

        viewManagerModel.setState(studyPoolViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }

    @Override
    public void prepareFailureView(String errorMessage) {
        // For now, just log; you can add an error field later if you want.
        System.out.println("Accept failed: " + errorMessage);
    }
}
