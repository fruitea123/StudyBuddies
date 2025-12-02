package view;

import interface_adapter.filter.FilterController;
import interface_adapter.filter.FilterState;
import interface_adapter.filter.FilterViewModel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class FilterView extends JPanel implements ActionListener, PropertyChangeListener {

    private final String viewName = "Filter";
    private final FilterViewModel filterViewModel;

    private final JTextField courseInputField = new JTextField(20);
    private final JTextField dateInputField = new JTextField(20);
    private final JLabel inputErrorField =  new JLabel();

    private final JButton filter;

    private FilterController filterController = null;

    public FilterView(FilterViewModel filterViewModel) {

        this.filterViewModel = filterViewModel;
        this.filterViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel("Filter Screen");
        final JLabel course_inst = new JLabel(filterViewModel.COURSE_INSTRUCTIONS);
        final JLabel date_inst = new JLabel(filterViewModel.DATE_INSTRUCTIONS);

        final LabelTextPanel course_info = new LabelTextPanel(new JLabel("Course"),
                courseInputField);
        final LabelTextPanel date_info = new LabelTextPanel(new JLabel("Date"),
                dateInputField);

        final JPanel button = new JPanel();
        filter = new JButton("Filter");
        button.add(filter);

        filter.addActionListener(this);

        courseInputField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final FilterState currentState = filterViewModel.getState();
                currentState.setCourse(courseInputField.getText());
                filterViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {documentListenerHelper();}

            @Override
            public void removeUpdate(DocumentEvent e) {documentListenerHelper();}

            @Override
            public void changedUpdate(DocumentEvent e) {documentListenerHelper();}
        });

        dateInputField.getDocument().addDocumentListener((new DocumentListener() {

            private void documentListenerHelper() {
                final FilterState currentState = filterViewModel.getState();
                currentState.setDate(dateInputField.getText());
                filterViewModel.setState(currentState);
            }


            @Override
            public void insertUpdate(DocumentEvent e) {documentListenerHelper();}

            @Override
            public void removeUpdate(DocumentEvent e) {documentListenerHelper();}

            @Override
            public void changedUpdate(DocumentEvent e) {documentListenerHelper();}
        }));


        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(title);
        this.add(inputErrorField);
        this.add(course_info);
        this.add(course_inst);
        this.add(date_info);
        this.add(date_inst);
        this.add(button);

    }


    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource().equals(filter)) {
            final FilterState currentState = filterViewModel.getState();
            filterController.execute(currentState.getCourse(), currentState.getDate());
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final FilterState filterState = (FilterState) evt.getNewValue();
        inputErrorField.setText(filterState.getFilterError());
    }

    public void setFilterController(FilterController filterController) {
        this.filterController = filterController;
    }

    public String getViewName() {
        return viewName;
    }

}
