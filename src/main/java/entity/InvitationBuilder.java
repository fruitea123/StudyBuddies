package entity;

import java.time.LocalDate;
import java.time.LocalTime;

public class InvitationBuilder {
    String course;
    String description;
    LocalDate date;
    LocalTime start;
    LocalTime end;
//    String mode = Invitation.MODE_ONLINE;
    String location = "";
    int capacity = 0;




    public Invitation build(){
        return new Invitation(course, description, date, start, end, mode, location, capacity);
    }
}
