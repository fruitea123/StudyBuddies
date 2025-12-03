//package interface_adapter.profile;
//
//import interface_adapter.ViewManagerModel;
//import interface_adapter.makeinvitation.MakeInvitationViewModel;
//import use_case.profile.ProfileOutputBoundary;
//import view.MakeInvitationView;
//
//public class ProfilePresenter implements ProfileOutputBoundary {
//
//    private final ViewManagerModel viewManagerModel;
//
//    public ProfilePresenter(ViewManagerModel viewManagerModel) {
//        this.viewManagerModel = viewManagerModel;
//    }
//
//    @Override
//    public void switchToHomeView() {
//        MakeInvitationViewModel myInvitationsViewModel = new MakeInvitationViewModel();
//        viewManagerModel.setState(myInvitationsViewModel.getViewName());
//        viewManagerModel.firePropertyChange();
//    }
//}
