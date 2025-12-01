package use_case.calendar;

public class StudySession {
    private final String course;
    private final String description;
    private final String date;
    private final String startTime;
    private final String endTime;

    public StudySession(String course, String description,
                        String date, String startTime, String endTime) {
        this.course = course;
        this.description = description;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getCourse() { return course; }
    public String getDescription() { return description; }
    public String getDate() { return date; }
    public String getStartTime() { return startTime; }
    public String getEndTime() { return endTime; }
}
