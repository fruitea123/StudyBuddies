package data_access;

import entity.Invitation;
import entity.User;
import use_case.makeinvitation.MakeInvitationDataAccessInterface;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Local test version for invitation

public class InMemoryInvitationDataAccessObject
        implements MakeInvitationDataAccessInterface {

    private final List<Invitation> invitations = new ArrayList<>();

    @Override
    public void save(Invitation inv) {
        invitations.add(inv);
    }

    @Override
    public boolean existsOverlap(String course, LocalDate date,
                                 LocalTime start, LocalTime end) {

        for (Invitation inv : invitations) {
            if (!inv.getCourse().equals(course)) {
                continue;
            }
            if (!inv.getDate().equals(date)) {
                continue;
            }

            if (timeOverlap(start, end,
                    inv.getStartTime(), inv.getEndTime())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean ownerHasOverlap(User owner, LocalDate date,
                                   LocalTime start, LocalTime end) {

        for (Invitation inv : invitations) {
            if (!inv.getOwner().equals(owner)) {
                continue;
            }
            if (!inv.getDate().equals(date)) {
                continue;
            }

            if (timeOverlap(start, end,
                    inv.getStartTime(), inv.getEndTime())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Invitation> listByCourseAndDate(String course, LocalDate date) {
        List<Invitation> result = new ArrayList<>();
        for (Invitation inv : invitations) {
            if (inv.getCourse().equals(course) && inv.getDate().equals(date)) {
                result.add(inv);
            }
        }
        return Collections.unmodifiableList(result);
    }

    private boolean timeOverlap(LocalTime aStart, LocalTime aEnd,
                                LocalTime bStart, LocalTime bEnd) {
        return aStart.isBefore(bEnd) && bStart.isBefore(aEnd);
    }

    // add for testing
    public List<Invitation> getAllInvitations() {
        return Collections.unmodifiableList(invitations);
    }
}