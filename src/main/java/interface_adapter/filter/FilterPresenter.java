package interface_adapter.filter;

import interface_adapter.ViewManagerModel;
import interface_adapter.profile.ProfileViewModel;
import interface_adapter.study_pool.StudyPoolState;
import interface_adapter.study_pool.StudyPoolViewModel;
import use_case.filter.FilterOutputBoundary;
import use_case.filter.FilterOutputData;

public class FilterPresenter implements FilterOutputBoundary {

    private final FilterViewModel filterViewModel;
    private final StudyPoolViewModel studyPoolViewModel;
    private final ViewManagerModel viewManagerModel;
    private final ProfileViewModel profileViewModel;


    public FilterPresenter(FilterViewModel filterViewModel,
                           StudyPoolViewModel studyPoolViewModel,
                           ViewManagerModel viewManagerModel,
                           ProfileViewModel profileViewModel) {
        this.filterViewModel = filterViewModel;
        this.studyPoolViewModel = studyPoolViewModel;
        this.viewManagerModel = viewManagerModel;
        this.profileViewModel = profileViewModel;
    }

    public void prepareSuccessView(FilterOutputData filterOutputData) {

        final StudyPoolState studyPoolState = studyPoolViewModel.getState();
        studyPoolState.setInvitations(filterOutputData.getInvites());
        this.studyPoolViewModel.firePropertyChange();

        filterViewModel.setState(new FilterState());

        this.viewManagerModel.setState(studyPoolViewModel.getViewName());
        this.viewManagerModel.firePropertyChange();
    }

    @Override
    public void prepareFailView(String error) {

        final FilterState filterState = filterViewModel.getState();
        filterState.setFilterError(error);
        filterViewModel.firePropertyChange();
    }

    public void movetohome() {
        this.viewManagerModel.setState(profileViewModel.getViewName());
        this.viewManagerModel.firePropertyChange();
    }
}
