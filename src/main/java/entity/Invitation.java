package entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;

/**
 * Entity representing a study invitation created by a user.
 * Stores course, time slot, mode, location, capacity, owner and participants.
 */
public class Invitation {

  public static final String MODE_ONLINE = "On Line";
  public static final String MODE_IN_PERSON = "In Person";
  public static final int DEFAULT_CAPACITY = 2;
  private final String invitationID;
  private String course;
  private String description;
  private LocalDate date;
  private LocalTime startTime;
  private LocalTime endTime;
  private String mode;
  private String location;
  private int capacity;
  private User owner; // store string(email in DAO)
  private List<User> participants; // store list of string(email)

  /**
   * create a new invitation by builder.
   *
   * @param b invitation builder
   */
  public Invitation(InvitationBuilder b) {
    this.course = trim(b.getCourse());
    this.description = trimOrEmpty(b.getDescription());
    this.date = b.getDate();
    this.startTime = b.getStartTime();
    this.endTime = b.getEndTime();
    this.mode = normalizeMode(b.getMode());
    this.location = trimOrEmpty(b.getLocation());
    this.capacity = (b.getCapacity() == null) ? DEFAULT_CAPACITY : b.getCapacity();
    this.owner = b.getOwner();
    LinkedHashSet<User> set = new LinkedHashSet<>();
    if (this.owner != null) {
      set.add(this.owner);
    }
    List<User> fromBuilder = b.getParticipants();
    if (fromBuilder != null && !fromBuilder.isEmpty()) {
      set.addAll(fromBuilder);
    }
    this.participants = Collections.unmodifiableList(new ArrayList<>(set));
    // delete repeated participants
    this.invitationID = b.getinvitationID();
    validate();
  }

  /**
   * method to unified input.
   *
   * @param s input string
   * @return string
   */
  private static String trim(String s) {
    if (s == null) {
      return null;
    } else {
      return s.trim();
    }
  }

  /**
   * method to unified input.
   *
   * @param s input string
   * @return string
   */
  private static String trimOrEmpty(String s) {
    if (s == null) {
      return "";
    } else {
      return s.trim();
    }
  }

  /**
   * Normalizes a raw mode string to either MODE_ONLINE or MODE_IN_PERSON.
   *
   * @param raw raw mode string from input
   * @return normalized mode string
   */
  private static String normalizeMode(String raw) {
    if (raw == null || raw.isBlank()) {
      return MODE_ONLINE;
    }

    String m = raw.trim().replace(' ', '_').toUpperCase(Locale.ROOT);

    if (m.equals(MODE_IN_PERSON)) {
      return MODE_IN_PERSON;
    }
    return MODE_ONLINE;
  }

  /**
   * return a new constructer for invitation.
   *
   * @return new invitation builder instance
   */
  public static InvitationBuilder builder() {
    return new InvitationBuilder();
  }

  /**
   * Validates the internal fields of this invitation.
   * This method checks required fields (course, date, times, mode, owner),
   * ensures the time range is valid, the capacity is at least 2, and that the
   * number of participants does not exceed the capacity.
   *
   * @throws IllegalArgumentException if any validation rule is violated
   */
  private void validate() {
    if (course == null || course.isBlank()) {
      throw new IllegalArgumentException("course required");
    }
    if (date == null) {
      throw new IllegalArgumentException("date required");
    }
    if (startTime == null || endTime == null) {
      throw new IllegalArgumentException("start/end required");
    }
    if (!startTime.isBefore(endTime)) {
      throw new IllegalArgumentException("start must be before end");
    }
    if (!MODE_ONLINE.equals(mode) && !MODE_IN_PERSON.equals(mode)) {
      throw new IllegalArgumentException("mode must be ONLINE or IN_PERSON");
    }
    if (MODE_IN_PERSON.equals(mode) && location.isBlank()) {
      throw new IllegalArgumentException("location required for in-person");
    }
    if (capacity < 2) {
      throw new IllegalArgumentException("capacity must be ≥ 2");
    }
    if (owner == null) {
      throw new IllegalArgumentException("owner required");
    }
    if (participants.size() > capacity) {
      throw new IllegalArgumentException("participants exceed capacity");
    }
  }

  /**
   * getter for course.
   *
   * @return course
   */
  public String getCourse() {
    return course;
  }

  /**
   * setter for course.
   *
   * @param course course
   */
  public void setCourse(String course) {
    this.course = course;
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
   * setter for description.
   *
   * @param description description
   */
  public void setDescription(String description) {
    this.description = description;
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
   * setter for date.
   *
   * @param date date
   */
  public void setDate(LocalDate date) {
    this.date = date;
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
   * setter for start time.
   *
   * @param startTime start time
   */
  public void setStartTime(LocalTime startTime) {
    this.startTime = startTime;
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
   * setter for end time.
   *
   * @param endTime end time
   */
  public void setEndTime(LocalTime endTime) {
    this.endTime = endTime;
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
   * setter for mode.
   *
   * @param mode mode
   */
  public void setMode(String mode) {
    this.mode = mode;
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
   * setter for location.
   *
   * @param location location
   */
  public void setLocation(String location) {
    this.location = location;
  }

  /**
   * getter for capacity.
   *
   * @return capacity
   */
  public int getCapacity() {
    return capacity;
  }

  /**
   * setter for capacity.
   *
   * @param capacity capacity
   */
  public void setCapacity(int capacity) {
    this.capacity = capacity;
  }

  /**
   * getter for owner.
   *
   * @return user
   */
  public User getOwner() {
    return owner;
  }

  /**
   * setter for owner.
   *
   * @param owner user
   */
  public void setOwner(User owner) {
    this.owner = owner;
  }

  /**
   * getter for participants list.
   *
   * @return participants
   */
  public List<User> getParticipants() {
    return participants;
  }

  /**
   * setter for participants list.
   *
   * @param participants participants
   */
  public void setParticipants(List<User> participants) {
    this.participants = participants;
  }

  /**
   * getter for invitationID.
   *
   * @return string of invitationID
   */
  public String getInvitationID() {
    return invitationID;
  }

  /**
   * method to count the number of participants in an invitation.
   *
   * @return occupancy
   */
  public int participantsCount() {
    return participants.size();
  }
}