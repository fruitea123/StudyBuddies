package use_case.makeinvitation;

import entity.Invitation;
import entity.User;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
// import java.util.Optional;

/**
 * Data access Interface for making new invitation in studypool.
 */
public interface MakeInvitationDataAccessInterface {

  /**
   * method to save new invitation in studypool.
   *
   * @param inv new invitation
   */
  void save(Invitation inv);

  /**
   * method to check if there were existed invitation with same course name and event time.
   *
   * @param course input course
   * @param date input date
   * @param start input star ttime
   * @param end input endtime
   * @return boolean to check conflict.
   */
  boolean existsOverlap(String course, LocalDate date, LocalTime start, LocalTime end);

  /**
   * method to check if there were a time conflict for current user to create a new invitation.
   *
   * @param owner current user
   * @param date input date
   * @param start input star ttime
   * @param end input end time
   * @return boolean to check conflict.
   */
  boolean ownerHasOverlap(User owner, LocalDate date, LocalTime start, LocalTime end);

  /**
   * method to search invitation by course and date.
   *
   * @param course input course
   * @param date input date
   * @return list of invitations.
   */
  List<Invitation> listByCourseAndDate(String course, LocalDate date);

  //    Optional<Invitation> findById(String id); optional idea for seraching by id
}