package mapper;
import entity.Invitation;
import org.bson.Document;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class DBInvitationMapper {

    public static Invitation fromDocument(Document doc) {
        Invitation inv = new Invitation();

        inv.setId(doc.getObjectId("_id").toHexString());
        inv.setCourse(doc.getString("course"));
        inv.setDescription(doc.getString("description"));

        // Convert String to LocalDate
        inv.setDate(LocalDate.parse(doc.getString("date")));

        inv.setStartTime(LocalTime.parse(doc.getString("start_time")));
        inv.setEndTime(LocalTime.parse(doc.getString("end_time")));
        inv.setMode(doc.getString("mode"));
        inv.setLocation(doc.getString("location"));
        inv.setCapacity(Integer.parseInt(doc.getString("capacity")));
        inv.setOwner(doc.getString("owner"));

        List<String> participants = doc.getList("participants", String.class);
        inv.setParticipants(participants);

        return inv;
    }

}

