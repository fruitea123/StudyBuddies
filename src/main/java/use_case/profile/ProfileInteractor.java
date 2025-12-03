package use_case.profile;

public class ProfileInteractor implements ProfileInputBoundary {

    private final ProfileOutputBoundary presenter;

    public ProfileInteractor(ProfileOutputBoundary presenter) {
        this.presenter = presenter;
    }

    @Override
    public void switchToHomeView() {
        presenter.switchToHomeView();
    }
}


