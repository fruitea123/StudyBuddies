package usecase.make_invitation;

import java.time.LocalDate;
import java.time.LocalTime;

public class MakeInvitationInputData {
    private final String course;
    private final String description;
    private final LocalDate date;
    private final LocalTime startTime;
    private final LocalTime endTime;
    private final String mode;
    private final String location;
    private final Integer occupancy;

    public MakeInvitationInputData(String course, String description, LocalDate date, LocalTime startTime,
                                   LocalTime endTime, String mode, String location, Integer occupancy) {
        this.course = course;
        this.description = description;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.mode = mode;
        this.location = location;
        this.occupancy = occupancy;
    }

    public String getCourse() {
        return course;
    }
    public String getDescription() {
        return description;
    }
    public LocalDate getDate() {
        return date;
    }
    public LocalTime getStartTime() {
        return startTime;
    }
    public LocalTime getEndTime() {
        return endTime;
    }
    public String getMode() {
        return mode;
    }
    public String getLocation() {
        return location;
    }
    public Integer getOccupancy() {
        return occupancy;
    }
}
