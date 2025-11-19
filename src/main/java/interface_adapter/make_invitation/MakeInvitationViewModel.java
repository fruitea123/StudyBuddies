package interface_adapter.make_invitation;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class MakeInvitationViewModel {
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    private String successMessage = "";
    private String failMessage = "";

    public String getSuccessMessage() {
        return successMessage;
    }
    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }
    public String getFailMessage() {
        return failMessage;
    }
    public void setFailMessage(String failMessage) {
        this.failMessage = failMessage;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }

}
