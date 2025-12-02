package use_case.makeinvitation;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * class for invitation input data.
 */
public class MakeInvitationInputData {
  private final String course;
  private final String description;
  private final LocalDate date;
  private final LocalTime startTime;
  private final LocalTime endTime;
  private final String mode;
  private final String location;
  private final Integer occupancy;


  /**
   * create a new invitation input data with the given parameters.
   *
   * @param course input course
   * @param description input description
   * @param date input date
   * @param startTime input start time
   * @param endTime input end time
   * @param mode input mode
   * @param location input location
   * @param occupancy inpute occupancy
   */
  public MakeInvitationInputData(String course, String description, LocalDate date,
                                 LocalTime startTime, LocalTime endTime, String mode,
                                 String location, Integer occupancy) {
    this.course = course;
    this.description = description;
    this.date = date;
    this.startTime = startTime;
    this.endTime = endTime;
    this.mode = mode;
    this.location = location;
    this.occupancy = occupancy;
  }

  /**
   * getter for course.
   *
   * @return course name
   */
  public String getCourse() {
    return course;
  }

  /**
   * getter for description.
   *
   * @return description
   */
  public String getDescription() {
    return description;
  }

  /**
   * getter for date.
   *
   * @return date
   */
  public LocalDate getDate() {
    return date;
  }

  /**
   * getter for start time.
   *
   * @return start time
   */
  public LocalTime getStartTime() {
    return startTime;
  }

  /**
   * getter for end time.
   *
   * @return end time
   */
  public LocalTime getEndTime() {
    return endTime;
  }

  /**
   * getter for mode.
   *
   * @return mode
   */
  public String getMode() {
    return mode;
  }

  /**
   * getter for location.
   *
   * @return location
   */
  public String getLocation() {
    return location;
  }

  /**
   * getter for occupancy.
   *
   * @return occupancy
   */
  public Integer getOccupancy() {
    return occupancy;
  }
}
