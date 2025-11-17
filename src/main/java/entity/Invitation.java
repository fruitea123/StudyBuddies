package entity;

import java.time.LocalDate;
import java.time.LocalTime;

public class Invitation {

    public static final String Mode_Online = "Online";
    public static final String Mode_In_Person = "In Person";

    private final String course;
    private final String description; // not necessary
    private final LocalDate date;
    private final LocalTime start;
    private final LocalTime end;
    private final String mode;            // "ONLINE" / "IN_PERSON"
    private final String location;
    private final int capacity;

    public Invitation(String course, String description, LocalDate date, LocalTime start, LocalTime end, String mode, String location, int capacity) {
        this.course = course;
        this.description = description;
        this.date = date;
        this.start = start;
        this.end = end;
        this.mode = mode;
        this.location = location;
        this.capacity = capacity;

    }

}
