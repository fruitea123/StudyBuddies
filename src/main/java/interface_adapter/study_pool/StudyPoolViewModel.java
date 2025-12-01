package interface_adapter.study_pool;

import interface_adapter.ViewModel;
import interface_adapter.signup.SignupState;

public class StudyPoolViewModel extends ViewModel<StudyPoolState> {
    public static final String TITLE_LABEL = "Study Pool View";

    public static final String TO_HOME_BUTTON_LABEL = "Go to Home";

    public StudyPoolViewModel() {
        super("Study Pool");
        setState(new StudyPoolState());
    }
}
