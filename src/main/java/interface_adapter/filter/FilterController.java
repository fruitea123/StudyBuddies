package interface_adapter.filter;

import use_case.filter.FilterInputBoundary;
import use_case.filter.FilterInputData;

public class FilterController {

    private final FilterInputBoundary filterUseCaseInteractor;

    public FilterController(FilterInputBoundary filterUseCaseInteractor) {
        this.filterUseCaseInteractor = filterUseCaseInteractor;
    }

    public void movetohome() {
        filterUseCaseInteractor.movetohome();
    }

    public void execute(String course, String date) {
        final FilterInputData filterInputData = new FilterInputData(course, date);
        filterUseCaseInteractor.execute(filterInputData);
    }
}
