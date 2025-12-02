package mapper;
import entity.Invitation;
import entity.User;
import entity.UserFactory;
import org.bson.Document;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class DBInvitationMapper {

    public static Invitation fromDocument(Document doc) {
//        Invitation inv = new Invitation();
//
//        inv.setId(doc.getObjectId("_id").toHexString());
//        inv.setCourse(doc.getString("course"));
//        inv.setDescription(doc.getString("description"));
//
//        // Convert String to LocalDate
//        inv.setDate(LocalDate.parse(doc.getString("date")));
//
//        inv.setStartTime(LocalTime.parse(doc.getString("start_time")));
//        inv.setEndTime(LocalTime.parse(doc.getString("end_time")));
//        inv.setMode(doc.getString("mode"));
//        inv.setLocation(doc.getString("location"));
//        inv.setCapacity(Integer.parseInt(doc.getString("capacity")));
//        inv.setOwner(doc.getString("owner"));
//
//        List<String> participants = doc.getList("participants", String.class);
//        inv.setParticipants(participants);
//
//        return inv;
        String id          = doc.getObjectId("_id").toHexString();
        String course      = doc.getString("course");
        String description = doc.getString("description");
        LocalDate date     = LocalDate.parse(doc.getString("date"));
        LocalTime start    = LocalTime.parse(doc.getString("start_time"));
        LocalTime end      = LocalTime.parse(doc.getString("end_time"));
        String mode        = doc.getString("mode");
        String location    = doc.getString("location");
        Integer capacity   = Integer.parseInt(doc.get("capacity").toString());

        String ownerEmail = doc.getString("owner");

        User owner = User.fromEmail(ownerEmail);

        List<String> emails = doc.getList("participants", String.class);
        List<User> participants = new ArrayList<>();
        if (emails != null) {
            for (String email : emails) {
                participants.add(User.fromEmail(email));
            }
        }

        return Invitation.builder()
                .invitationID(id)
                .course(course)
                .description(description)
                .date(date)
                .startTime(start)
                .endTime(end)
                .mode(mode)
                .location(location)
                .capacity(capacity)
                .owner(owner)
                .participants(participants)
                .build();
    }


}