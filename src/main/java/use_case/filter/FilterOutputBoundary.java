package use_case.filter;

public interface FilterOutputBoundary {

    void prepareSuccessView(FilterOutputData filterOutputData);

    void prepareFailViewC();

    void prepareFailViewD();

    void prepareFailViewCD();
}
