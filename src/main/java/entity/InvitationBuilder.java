package entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class InvitationBuilder {
    String course;
    private String description;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String mode;
    private String location;
    private Integer capacity;
    private User owner;
    private List<User> participants =  new ArrayList<>();
    private String invitationID;

    // Setter
    public InvitationBuilder course(String v){
        this.course = v;
        return this;
    }
    public InvitationBuilder description(String v){
        this.description = v;
        return this;
    }
    public InvitationBuilder date(LocalDate v){
        this.date = v;
        return this;
    }
    public InvitationBuilder startTime(LocalTime v){
        this.startTime = v;
        return this;
    }
    public InvitationBuilder endTime(LocalTime v){
        this.endTime = v;
        return this;
    }
    public InvitationBuilder mode(String v){
        this.mode = v;
        return this;
    }
    public InvitationBuilder location(String v){
        this.location = v;
        return this;
    }
    public InvitationBuilder capacity(Integer v){
        this.capacity = v;
        return this;
    }
    public InvitationBuilder owner(User u) {
        this.owner = u;
        return this;
    }
    public InvitationBuilder addParticipant(User u) {
        if (u != null) this.participants.add(u);
        return this;
    }
    public InvitationBuilder participants(Collection<User> users) {
        if (users != null) {
            this.participants.addAll(users);
        }
        return this;
    }
    public InvitationBuilder invitationID(String v) {
        this.invitationID = v;
        return this;
    }

    // getter
    public String getCourse(){
        return course;
    }
    public String getDescription(){
        return description;
    }
    public LocalDate getDate(){
        return date;
    }
    public LocalTime getStartTime(){
        return startTime;
    }
    public LocalTime getEndTime(){
        return endTime;
    }
    public String getMode(){
        return mode;
    }
    public String getLocation(){
        return location;
    }
    public Integer getCapacity(){
        return capacity;
    }
    public User getOwner(){
        return owner;
    }
    public List<User> getParticipants(){
        return participants;
    }
    public String getInvitationID(){ return invitationID; }

    public Invitation build() {
        return new Invitation(this);
    }

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
