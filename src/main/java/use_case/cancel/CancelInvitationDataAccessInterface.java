package use_case.cancel;
//import
import org.bson.Document;

public interface CancelInvitationDataAccessInterface {
//Interface

        Document findInvitationByParticipant(String username);

        Document findInvitationByOwner(String ownerName);

        void removeParticipantFromInvitation(Document invitation, String username);

        void deleteInvitation(Document invitation);
    }



