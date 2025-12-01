package use_case.filter;

public interface FilterOutputBoundary {

    void prepareSuccessView(FilterOutputData filterOutputData);

    void prepareFailView(String error);
}
