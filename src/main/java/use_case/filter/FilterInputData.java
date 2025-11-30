package use_case.filter;

public class FilterInputData {

    private final String course;
    private final String date;

    public FilterInputData(String course, String date) {
        this.course = course;
        this.date = date;
    }

    String getCourse() {return course;}
    String getDate() {return date;}
}
