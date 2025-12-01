package view;

import entity.Invitation;
import interface_adapter.study_pool.StudyPoolState;
import interface_adapter.study_pool.StudyPoolViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class StudyPoolView extends JPanel implements ActionListener, PropertyChangeListener {

    private final String viewName = "Study Pool";

    private List<Invitation> result_list = new ArrayList<>();

    private final StudyPoolViewModel studyPoolViewModel;
    //private final ProfileViewModel profileViewModel;

    private final JPanel scroll_panel = new JPanel();
    private final JButton toHome;

    //private final AcceptController acceptController;


    public StudyPoolView(StudyPoolViewModel studyPoolViewModel) {
        this.studyPoolViewModel = studyPoolViewModel;
        studyPoolViewModel.addPropertyChangeListener(this);

        //this.profileViewModel = profileViewModel;     //will be in constructor

        final JLabel title = new JLabel(studyPoolViewModel.TITLE_LABEL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        toHome = new JButton(studyPoolViewModel.TO_HOME_BUTTON_LABEL);

        final JPanel button = new JPanel();
        button.add(toHome);


        //implement movement from Study Pool page to Home page (MyInvitations)






        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(title);
        this.add(scroll_panel);
        this.add(button);

    }

    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());   //this is just a placeholder line until i implement toHome button
    }

    public void propertyChange(PropertyChangeEvent evt) {
        final StudyPoolState state = (StudyPoolState) evt.getNewValue();
        result_list = state.getInvitations();

        scroll_panel.removeAll();
        JPanel scroll_list = new JPanel();

        //final ProfileState currentState = profileViewModel.getState();
        //final String user = currentState.getusername;

        for (Invitation invitation : result_list) {
            if (!invitation.getParticipants().contains("placeholder")) {   // instead of placeholder it will be user
                InvitationCardAcceptPanel invitationCardAcceptPanel = new InvitationCardAcceptPanel(invitation);  //more parameters in real version
                scroll_list.add(invitationCardAcceptPanel);
            }
        }
        JScrollPane jScrollPane = new JScrollPane(scroll_list);
        scroll_panel.add(jScrollPane);


    }







    //public void setAcceptController(AcceptController controller) {
    //        this.acceptController = controller;
    //    }



}
