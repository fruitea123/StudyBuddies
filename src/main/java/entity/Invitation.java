package entity;

import org.bson.types.ObjectId;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Invitation {
    private String id;
    private String course;
    private String description;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String mode;
    private String location;
    private int capacity;
    private String owner;
    private List<String> participants;

    // Constructors, getters, setters
    public Invitation() {}

    public String getId() {
        return id;
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

    public int getCapacity() {
        return capacity;
    }

    public String getOwner() {
        return owner;
    }

    public List<String> getParticipants() {
        return participants;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setParticipants(List<String> participants) {
        this.participants = participants;
    }
}
