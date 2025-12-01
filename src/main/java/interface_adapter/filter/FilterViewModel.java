package interface_adapter.filter;

import interface_adapter.ViewModel;
import interface_adapter.signup.SignupState;

public class FilterViewModel extends ViewModel<FilterState> {
    public static final String TITLE_LABEL = "Filter View";
    public static final String COURSE_LABEL = "Choose course";
    public static final String COURSE_INSTRUCTIONS = "Course must be typed with 3 uppercase letters " +
            "followed by 3 numbers (ex CSC207)";
    public static final String DATE_LABEL = "Choose date";
    public static final String DATE_INSTRUCTIONS = "Date must be typed in with the following format: " +
            "yyyy-mm-dd";

    public static final String FILTER_BUTTON_LABEL = "Filer";

    public FilterViewModel() {
        super("Filter");
        setState(new FilterState());
    }
}
