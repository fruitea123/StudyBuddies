package view;

import entity.Invitation;
import interface_adapter.accept.AcceptInvitationController;
import interface_adapter.filter.FilterController;
import interface_adapter.profile.ProfileState;
import interface_adapter.profile.ProfileViewModel;
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
    private final ProfileViewModel profileViewModel;

    private final JPanel scroll_panel = new JPanel();
    private final JButton toHome;

    private AcceptInvitationController acceptController;
    private FilterController filterController;


    public StudyPoolView(StudyPoolViewModel studyPoolViewModel, ProfileViewModel profileViewModel,
                         AcceptInvitationController acceptController, FilterController filterController) {
        this.studyPoolViewModel = studyPoolViewModel;
        this.profileViewModel = profileViewModel;
        this.acceptController = acceptController;
        this.filterController = filterController;
        studyPoolViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel(studyPoolViewModel.TITLE_LABEL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        toHome = new JButton(studyPoolViewModel.TO_HOME_BUTTON_LABEL);

        final JPanel button = new JPanel();
        button.add(toHome);

        toHome.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (e.getSource().equals(toHome)) {
                            filterController.movetohome();
                        }
                    }
                }
        );
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

        final ProfileState currentState = profileViewModel.getState();
        final String user = currentState.getUsername();

        for (Invitation invitation : result_list) {
            if (!invitation.getParticipants().contains(user)) {   // instead of placeholder it will be user
                InvitationCardAcceptPanel invitationCardAcceptPanel = new InvitationCardAcceptPanel(invitation,
                        profileViewModel, acceptController);
                scroll_list.add(invitationCardAcceptPanel);
            }
        }
        JScrollPane jScrollPane = new JScrollPane(scroll_list);
        scroll_panel.add(jScrollPane);


    }


    public void setAcceptController(AcceptInvitationController controller) {
            this.acceptController = controller;
        }



}
