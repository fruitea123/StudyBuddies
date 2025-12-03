package interface_adapter.myinvitations;

import interface_adapter.ViewManagerModel;
import interface_adapter.profile.ProfileViewModel;
import use_case.cancel.CancelInvitation;
import use_case.calendar.InsertUserStudySessionsInteractor;
import use_case.myinvitations.MyInvitationsInputBoundary;

public class MyInvitationsController {

    private final MyInvitationsInputBoundary interactor;
    private final CancelInvitation cancelUseCase;
    private final InsertUserStudySessionsInteractor calendarUseCase;
    private final ViewManagerModel viewManagerModel;
    private final ProfileViewModel profileViewModel;

    public MyInvitationsController(MyInvitationsInputBoundary interactor,
                                   CancelInvitation cancelUseCase,
                                   InsertUserStudySessionsInteractor calendarUseCase,
                                   ViewManagerModel viewManagerModel,
                                   ProfileViewModel profileViewModel) {
        this.interactor = interactor;
        this.cancelUseCase = cancelUseCase;
        this.calendarUseCase = calendarUseCase;
        this.viewManagerModel = viewManagerModel;
        this.profileViewModel = profileViewModel;
    }

    public void load() {
        String userEmail = profileViewModel.getState().getUsername();
        interactor.loadMyInvitations(userEmail);
    }

    public void onLeaveClicked(String invitationId) {
        String userEmail = profileViewModel.getState().getUsername();
        cancelUseCase.setUsername(userEmail);
        cancelUseCase.leave();
        load();
    }

    public void onDeleteClicked(String invitationId) {
        String userEmail = profileViewModel.getState().getUsername();
        cancelUseCase.setOwnerName(userEmail);
        cancelUseCase.delete();
        load();
    }

    public void onCreateInvitation() {
        System.out.println("Navigate: Create Invitation");
        viewManagerModel.setState("MakeInvitation");
        viewManagerModel.firePropertyChange();
    }

    public void onCalendarClicked() {
        String userEmail = profileViewModel.getState().getUsername();
        try {
            calendarUseCase.execute(userEmail);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onStudyPoolClicked() {
        System.out.println("Navigate: Study Pool");
    }

    public void onProfileClicked() {
        System.out.println("Navigate: Profile");
    }
}
