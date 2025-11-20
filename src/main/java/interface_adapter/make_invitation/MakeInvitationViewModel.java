package interface_adapter.make_invitation;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class MakeInvitationViewModel {

    public static final String STATE_PROPERTY = "state";

    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    private final MakeInvitationState state = new MakeInvitationState();

    public MakeInvitationState getState() {
        return state;
    }

    public void addPropertyChangeListener(PropertyChangeListener l) {
        pcs.addPropertyChangeListener(l);
    }

    public void firePropertyChanged() {
        pcs.firePropertyChange(STATE_PROPERTY, null, state);
    }
}