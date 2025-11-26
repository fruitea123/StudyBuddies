
package entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;


public class Invitation {

    public static final String MODE_ONLINE = "OnLine";
    public static final String MODE_IN_PERSON = "In Person";
    public static final int DEFAULT_CAPACITY = 2;

    private final String course;
    private final String description;
    private final LocalDate date;
    private final LocalTime startTime;
    private final LocalTime endTime;
    private final String mode;
    private final String location;
    private final int capacity;
    private final User owner;
    private final List<User> participants;

    Invitation(InvitationBuilder b) {
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
        validate();
    }

    // unified input function
    private static String trim(String s) {
        if  (s == null) {
            return null;
        } else{
            return s.trim();
        }
    }

    private static String trimOrEmpty(String s) {
        if (s == null) {
            return "";
        } else  {
            return s.trim();
        }
    }

    private static String normalizeMode(String raw) {
        if (raw == null || raw.isBlank()){
            return MODE_ONLINE;
        }

        String m = raw.trim().replace(' ', '_').toUpperCase(Locale.ROOT);

        if (m.equals(MODE_IN_PERSON)){
            return MODE_IN_PERSON;
        }
        return MODE_ONLINE;
    }

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
    public User getOwner() {
        return owner;
    }
    public List<User> getParticipants() {
        return participants;
    }

    public int participantsCount() {
        return participants.size();
    }

    public boolean isInPerson() {
        return MODE_IN_PERSON.equals(mode);
    }

    public boolean isOnLine() {
        return MODE_ONLINE.equals(mode);
    }

    public boolean checkConflict(Invitation other) {
        if (other == null) {
            return false;
        }

        boolean sameCourse = Objects.equals(this.course, other.course);
        boolean sameDate   = Objects.equals(this.date,   other.date);
        boolean overlap    = !(this.endTime.isBefore(other.startTime)
                || this.startTime.isAfter(other.endTime));

        return sameCourse && sameDate && overlap;
    }

    public String summary() {
        return course + " " + date + " " + startTime + "–" + endTime + " (" + mode + ")";
    }

    @Override
    public String toString() {
        return "Invitation [course=" + course + ", description=" + description + ", date=" + date;
    }

    public static InvitationBuilder builder() {
        return new InvitationBuilder();
    }

}
