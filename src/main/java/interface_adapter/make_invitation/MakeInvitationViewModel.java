package interface_adapter.make_invitation;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class MakeInvitationViewModel {
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    private MakeInvitationState state = MakeInvitationState.empty();

    public void addPropertyChangeListener(PropertyChangeListener l) { pcs.addPropertyChangeListener(l); }
    public void removePropertyChangeListener(PropertyChangeListener l) { pcs.removePropertyChangeListener(l); }

    public MakeInvitationState getState() { return state; }

    public void setState(MakeInvitationState newState) {
        this.state = newState;
        pcs.firePropertyChange("state", null, newState);
    }
}