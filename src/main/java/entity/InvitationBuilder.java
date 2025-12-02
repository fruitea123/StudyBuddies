package entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Builder for Invitation entities.
 * This class uses a fluent interface so that fields can be set by
 * chaining method calls and finally constructing an Invitation build().
 */
public class InvitationBuilder {
  private String course;
  private String description;
  private LocalDate date;
  private LocalTime startTime;
  private LocalTime endTime;
  private String mode;
  private String location;
  private Integer capacity;
  private User owner;
  private List<User> participants = new ArrayList<>();
  private String invitationID;

  /**
   * Sets the course for the invitation.
   *
   * @param v course code
   * @return this builder
   */
  public InvitationBuilder course(String v) {
    this.course = v;
    return this;
  }

  /**
   * Sets the description for the invitation.
   *
   * @param v description
   * @return this builder
   */
  public InvitationBuilder description(String v) {
    this.description = v;
    return this;
  }

  /**
   * Sets the date for the invitation.
   *
   * @param v date
   * @return this builder
   */
  public InvitationBuilder date(LocalDate v) {
    this.date = v;
    return this;
  }

  /**
   * Sets the start time for the invitation.
   *
   * @param v start time
   * @return this builder
   */
  public InvitationBuilder startTime(LocalTime v) {
    this.startTime = v;
    return this;
  }

  /**
   * Sets the end time for the invitation.
   *
   * @param v end time
   * @return this builder
   */
  public InvitationBuilder endTime(LocalTime v) {
    this.endTime = v;
    return this;
  }

  /**
   * Sets the mode for the invitation.
   *
   * @param v mode
   * @return this builder
   */
  public InvitationBuilder mode(String v) {
    this.mode = v;
    return this;
  }

  /**
   * Sets the location for the invitation.
   *
   * @param v location
   * @return this builder
   */
  public InvitationBuilder location(String v) {
    this.location = v;
    return this;
  }

  /**
   * Sets the capacity for the invitation.
   *
   * @param v capacity
   * @return this builder
   */
  public InvitationBuilder capacity(Integer v) {
    this.capacity = v;
    return this;
  }

  /**
   * Sets the owner for the invitation.
   *
   * @param u owner
   * @return this builder
   */
  public InvitationBuilder owner(User u) {
    this.owner = u;
    return this;
  }

  /**
   * method to add single participant to the invitation.
   *
   * @param u user
   * @return this builder
   */
  public InvitationBuilder addParticipant(User u) {
    if (u != null) {
      this.participants.add(u);
    }
    return this;
  }

  /**
   * method to add the collection of participants to the invitation.
   *
   * @param users collection of users
   * @return this builder
   */
  public InvitationBuilder participants(Collection<User> users) {
    if (users != null) {
      this.participants.addAll(users);
    }
    return this;
  }

  /**
   * Sets the owner for the invitation.
   *
   * @param v invitationID
   * @return this builder
   */
  public InvitationBuilder invitationID(String v) {
    this.invitationID = v;
    return this;
  }

  /**
   * Returns the configured course.
   *
   * @return course code
   */
  public String getCourse() {
    return course;
  }

  /**
   * Returns the configured description.
   *
   * @return description text
   */
  public String getDescription() {
    return description;
  }

  /**
   * Returns the configured date.
   *
   * @return invitation date
   */
  public LocalDate getDate() {
    return date;
  }

  /**
   * Returns the configured start time.
   *
   * @return start time
   */
  public LocalTime getStartTime() {
    return startTime;
  }

  /**
   * Returns the configured end time.
   *
   * @return end time
   */
  public LocalTime getEndTime() {
    return endTime;
  }

  /**
   * Returns the configured mode.
   *
   * @return mode string
   */
  public String getMode() {
    return mode;
  }

  /**
   * Returns the configured location.
   *
   * @return location string
   */
  public String getLocation() {
    return location;
  }

  /**
   * Returns the configured capacity.
   *
   * @return capacity value, may be {@code null}
   */
  public Integer getCapacity() {
    return capacity;
  }

  /**
   * Returns the configured owner.
   *
   * @return owner user, may be {@code null}
   */
  public User getOwner() {
    return owner;
  }

  /**
   * Returns the configured participants.
   *
   * @return mutable list of participants
   */
  public List<User> getParticipants() {
    return participants;
  }

  /**
   * Returns the configured invitation identifier.
   *
   * @return invitation ID
   */
  public String getinvitationID() {
    return invitationID;
  }

  /**
   * Builds a new Invitation instance from the current builder state.
   *
   * @return a new Invitation
   */
  public Invitation build() {
    return new Invitation(this);
  }

  /**
   * Creates a new builder pre-populated from an existing invitation.
   *
   * @param src source invitation
   * @return a builder containing the data from src
   */
  public static InvitationBuilder from(Invitation src) {
    return new InvitationBuilder()
      .course(src.getCourse())
      .description(src.getDescription())
      .startTime(src.getStartTime())
      .endTime(src.getEndTime())
      .mode(src.getMode())
      .location(src.getLocation())
      .capacity(src.getCapacity())
      .owner(src.getOwner())
      .participants(src.getParticipants());
  }

}