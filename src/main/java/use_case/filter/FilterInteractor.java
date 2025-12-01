package use_case.filter;

import entity.Invitation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FilterInteractor implements FilterInputBoundary {

    private final FilterInvitationDataAccessInterface invitationDAO;
    private final FilterOutputBoundary filterPresenter;

    public FilterInteractor(FilterInvitationDataAccessInterface invitationDAO,
                            FilterOutputBoundary filterPresenter) {
        this.invitationDAO = invitationDAO;
        this.filterPresenter = filterPresenter;
    }

    @Override
    public void execute(FilterInputData filterInputData) {
        final String course = filterInputData.getCourse();
        final String date = filterInputData.getDate();

        if (!(valid_course(course) || valid_date(date))) {
            filterPresenter.prepareFailViewCD();
        }
        else if (Boolean.FALSE.equals(valid_course(course))) {
            filterPresenter.prepareFailViewC();
        }
        else if (Boolean.FALSE.equals(valid_date(date))) {
            filterPresenter.prepareFailViewD();
        }
        else if (course.isEmpty() && date.isEmpty()) {
            List<Invitation> result_list = base_filter_list();
            FilterOutputData filterOutputData = new FilterOutputData(result_list);
            filterPresenter.prepareSuccessView(filterOutputData);
        }
        else if (course.isEmpty()) {
            List<Invitation> result_list = filter_list_date(date);
            FilterOutputData filterOutputData = new FilterOutputData(result_list);
            filterPresenter.prepareSuccessView(filterOutputData);

        }
        else if (date.isEmpty()) {
            List<Invitation> result_list = filter_list_course(course);
            FilterOutputData filterOutputData = new FilterOutputData(result_list);
            filterPresenter.prepareSuccessView(filterOutputData);
        }
        else {
            List<Invitation> result_list = filter_list(course, date);
            FilterOutputData filterOutputData = new FilterOutputData(result_list);
            filterPresenter.prepareSuccessView(filterOutputData);
        }
    }

    public Boolean valid_course(String course) {
        if (course.isEmpty()) {return true;}
        if (course.length() != 6) {return false;}

        String letters = course.substring(0, 3);
        String numbers = course.substring(3, 6);

        for (char c : letters.toCharArray()) {
            if (!(Character.isLetter(c) && Character.isUpperCase(c))) {return false;}
        }
        for (char c : numbers.toCharArray()) {
            if (!Character.isDigit(c)) {return false;}
        }
        return true;
    }

    public Boolean valid_date(String date) {
        if (date.isEmpty()) {return true;}
        if (date.length() != 10) {return false;}

        String year = date.substring(0, 4);
        String month = date.substring(5, 7);
        String day = date.substring(8, 10);

        if (!(date.charAt(4) == '-' && date.charAt(7) == '-')) {return false;}

        for (char c : month.toCharArray()) {
            if (!Character.isDigit(c)) {return false;}
        }
        for (char c : day.toCharArray()) {
            if (!Character.isDigit(c)) {return false;}
        }
        for (char c : year.toCharArray()) {
            if (!Character.isDigit(c)) {return false;}
        }
        return true;
    }

    public List<Invitation> filter_list(String course, String date) {
        List<Invitation> base_list = invitationDAO.findAll();
        List<Invitation> result_list = new ArrayList<>();

        for (Invitation invitation : base_list) {
            if (course.equals(invitation.getCourse()) &&
                    LocalDate.parse(date).equals(invitation.getDate()) &&
            invitation.getParticipants().size() < invitation.getCapacity() - 1) {
                result_list.add(invitation);
            }
        }
        return result_list;
    }
    public List<Invitation> filter_list_date(String date) {
        List<Invitation> base_list = invitationDAO.findAll();
        List<Invitation> result_list = new ArrayList<>();

        for (Invitation invitation : base_list) {
            if (LocalDate.parse(date).equals(invitation.getDate()) &&
                    invitation.getParticipants().size() < invitation.getCapacity() - 1) {
                result_list.add(invitation);
            }
        }
        return result_list;
    }
    public List<Invitation> filter_list_course(String course) {
        List<Invitation> base_list = invitationDAO.findAll();
        List<Invitation> result_list = new ArrayList<>();

        for (Invitation invitation : base_list) {
            if (course.equals(invitation.getCourse()) &&
                    invitation.getParticipants().size() < invitation.getCapacity() - 1) {
                result_list.add(invitation);
            }
        }
        return result_list;
    }
    public List<Invitation> base_filter_list() {
        List<Invitation> base_list = invitationDAO.findAll();
        List<Invitation> result_list = new ArrayList<>();

        for (Invitation invitation : base_list) {
            if (invitation.getParticipants().size() < invitation.getCapacity() - 1) {
                result_list.add(invitation);
            }
        }
        return result_list;
    }
}
