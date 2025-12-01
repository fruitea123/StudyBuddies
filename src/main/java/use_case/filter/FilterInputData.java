package use_case.filter;

public class FilterInputData {

    private final String course;
    private final String date;

    public FilterInputData(String course, String date) {
        this.course = course;
        this.date = date;
    }

    public String getCourse() {
        return course;
    }

    public String getDate() {
        return date;
    }
}
