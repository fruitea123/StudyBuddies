package use_case.make_invitation;

import entity.Invitation;
import entity.User;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
//import java.util.Optional;

public interface MakeInvitationDataAccessInterface {

    void save(Invitation inv);

    boolean existsOverlap(String course, LocalDate date, LocalTime start, LocalTime end);

    boolean ownerHasOverlap(User owner, LocalDate date, LocalTime start, LocalTime end);


    List<Invitation> listByCourseAndDate(String course, LocalDate date);

//    Optional<Invitation> findById(String id); optional idea for seraching by id
}