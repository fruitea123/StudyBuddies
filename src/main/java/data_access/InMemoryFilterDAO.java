package data_access;

import entity.Invitation;
import entity.InvitationBuilder;
import use_case.filter.FilterInvitationDataAccessInterface;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class InMemoryFilterDAO implements FilterInvitationDataAccessInterface {

    public List<Invitation> findAll() {
        List<Invitation> list = new ArrayList<>();

        LocalDate date = LocalDate.parse("2025-12-5");
        Invitation invitation1 = Invitation.builder()
                .course("CSC207")
                .date(date)
                .build();

        LocalDate date2 = LocalDate.parse("2025-12-6");
        Invitation invitation2 = Invitation.builder()
                .course("CSC207")
                .date(date2)
                .build();

        Invitation invitation3 = Invitation.builder()
                .course("CSC209")
                .date(date)
                .build();

        list.add(invitation1);
        list.add(invitation2);
        list.add(invitation3);

        return list;
    }
}
