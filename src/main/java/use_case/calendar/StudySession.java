package use_case.calendar;

/**
 * Represents a study session with course information, timing, and description.
 */
public class StudySession {

  private final String course;
  private final String description;
  private final String date;
  private final String startTime;
  private final String endTime;

  /**
     * Creates a new study session.
     *
     * @param course the course name
     * @param description a description of the study session
     * @param date the date of the study session
     * @param startTime the start time of the session
     * @param endTime the end time of the session
   */
  public StudySession(String course, String description,
                        String date, String startTime, String endTime) {
    this.course = course;
    this.description = description;
    this.date = date;
    this.startTime = startTime;
    this.endTime = endTime;
  }

  /**
     * Returns the course for the study session.
     *
     * @return the course name
   */
  public String getCourse() {
    return course;
  }

  /**
     * Returns the description of the study session.
     *
     * @return the description
  */
  public String getDescription() {
    return description;
  }

  /**
     * Returns the date of the study session.
     *
     * @return the date string
  */
  public String getDate() {
    return date;
  }

  /**
     * Returns the start time of the study session.
     *
     * @return the start time
  */
  public String getStartTime() {
    return startTime;
  }

  /**
     * Returns the end time of the study session.
     *
     * @return the end time
  */
  public String getEndTime() {
    return endTime;
  }
}
