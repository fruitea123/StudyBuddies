package interface_adapter.filter;

public class FilterState {

    private String course = "";
    private String date = "";
    private String filterError;

    public String getCourse() {
        return course;
    }

    public String getDate() {
        return date;
    }

    public String getFilterError() {
        return filterError;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setFilterError(String filterError) {
        this.filterError = filterError;
    }
}
