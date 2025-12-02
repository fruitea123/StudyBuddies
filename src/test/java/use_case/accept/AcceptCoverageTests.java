package use_case.accept;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class AcceptCoverageTests {

    // ----- Test doubles -----

    private static class FakePresenter implements AcceptInvitationOutputBoundary {
        String lastError = null;
        boolean successCalled = false;

        @Override
        public void prepareFailureView(String errorMessage) {
            lastError = errorMessage;
        }

        @Override
        public void prepareSuccessView() {
            successCalled = true;
        }
    }

    private static class FakeDao implements AcceptInvitationDataAccessInterface {
        Document targetSession = null;
        Iterable<Document> userSessions = Collections.emptyList();
        boolean addCalled = false;
        String addedUsername = null;

        @Override
        public Document findInvitationById(String invitationId) {
            return null;
        }

        @Override
        public Document findInvitationByOwner(String ownerName) {
            return targetSession;
        }

        @Override
        public Iterable<Document> findInvitationsByParticipant(String username) {
            return userSessions;
        }

        @Override
        public void addParticipantToInvitation(Document invitation, String username) {
            addCalled = true;
            addedUsername = username;
        }
    }

    // ----- Tests -----

    @Test
    public void hasTimeConflict_targetMissingTimes_returnsFalseAndStillAdds() {
        String username = "max";
        String owner = "Tim";

        FakePresenter presenter = new FakePresenter();
        FakeDao dao = new FakeDao();

        // Target session exists, but its times are null
        Document target = new Document("_id", new ObjectId())
                .append("owner", owner)
                .append("startTime", null)
                .append("endTime", null);
        dao.targetSession = target;

        // User has no sessions; they are irrelevant because we return before the loop
        dao.userSessions = Collections.emptyList();

        AcceptInvitationInteractor interactor =
                new AcceptInvitationInteractor(presenter, dao);

        AcceptInvitationInputData input =
                new AcceptInvitationInputData(username, owner);

        interactor.acceptInvitation(input);

        // hasTimeConflict should return false, so we reach addParticipantToInvitation
        assertTrue(dao.addCalled);
        assertEquals(username, dao.addedUsername);
        assertNull(presenter.lastError);
    }

    @Test
    public void acceptInvitation_noConflict_butSessionMissingOnSecondFetch() {
        String username = "max";
        String owner = "Tim";

        FakePresenter presenter = new FakePresenter();

        AcceptInvitationDataAccessInterface dao = new FakeDao() {
            int calls = 0;

            @Override
            public Document findInvitationByOwner(String ownerName) {
                calls++;
                if (calls == 1) {
                    // First call → from hasTimeConflict(), must NOT be null
                    return new Document("_id", new ObjectId())
                            .append("owner", owner)
                            .append("startTime", new Date(1000L))
                            .append("endTime", new Date(2000L));
                } else {
                    // Second call → from acceptInvitation(), must be null
                    return null;
                }
            }

            @Override
            public Iterable<Document> findInvitationsByParticipant(String username) {
                // No user sessions → hasTimeConflict returns false naturally
                return Collections.emptyList();
            }
        };

        AcceptInvitationInteractor interactor =
                new AcceptInvitationInteractor(presenter, dao);

        interactor.acceptInvitation(new AcceptInvitationInputData(username, owner));

        assertEquals("No session found for owner: " + owner, presenter.lastError);
    }

    @Test
    public void hasTimeConflict_targetEndNull_only() {
        FakePresenter presenter = new FakePresenter();
        FakeDao dao = new FakeDao();

        dao.targetSession = new Document("_id", new ObjectId())
                .append("owner", "Tim")
                .append("startTime", new Date(1000L))
                .append("endTime", null);  // ONLY endTime is null

        dao.userSessions = Collections.emptyList();

        new AcceptInvitationInteractor(presenter, dao)
                .acceptInvitation(new AcceptInvitationInputData("max", "Tim"));
    }

    @Test
    public void hasTimeConflict_targetStartNull_only_executedInAcceptInvitation() {
        FakePresenter presenter = new FakePresenter();
        FakeDao dao = new FakeDao();

        dao.targetSession = new Document("_id", new ObjectId())
                .append("owner", "Tim")
                .append("startTime", null)  // ONLY startTime null
                .append("endTime", new Date(2000L));

        dao.userSessions = Collections.emptyList();

        new AcceptInvitationInteractor(presenter, dao)
                .acceptInvitation(new AcceptInvitationInputData("max", "Tim"));
    }

    // Case 1: targetStart.before(userEnd) == false, userStart.before(targetEnd) == true
    @Test
    public void hasTimeConflict_firstConditionFalse_secondTrue() {
        String username = "max";
        String owner = "Tim";

        FakePresenter presenter = new FakePresenter();
        FakeDao dao = new FakeDao();

        // Target: [3000, 4000]
        Date tStart = new Date(3000L);
        Date tEnd   = new Date(4000L);

        // User session: [1000, 3500]
        Date uStart = new Date(1000L);
        Date uEnd   = new Date(3500L);

        // Here: targetStart.before(userEnd) → 3000<3500 = true actually.
        // So swap: make userEnd BEFORE targetStart.
        uEnd = new Date(2000L);            // userEnd < targetStart

        // Now:
        // targetStart.before(userEnd) → 3000<2000 = false
        // userStart.before(targetEnd) → 1000<4000 = true

        dao.targetSession = new Document("_id", new ObjectId())
                .append("owner", owner)
                .append("startTime", tStart)
                .append("endTime", tEnd);

        Document sess = new Document("_id", new ObjectId())
                .append("startTime", uStart)
                .append("endTime", uEnd);
        dao.userSessions = Arrays.asList(sess);

        new AcceptInvitationInteractor(presenter, dao)
                .acceptInvitation(new AcceptInvitationInputData(username, owner));

        // no conflict, should add participant
        assertTrue(dao.addCalled);
        assertNull(presenter.lastError);
    }

    // Case 2: first condition true, second false
    @Test
    public void hasTimeConflict_firstConditionTrue_secondFalse() {
        String username = "max";
        String owner = "Tim";

        FakePresenter presenter = new FakePresenter();
        FakeDao dao = new FakeDao();

        // Target: [1000, 4000]
        Date tStart = new Date(1000L);
        Date tEnd   = new Date(4000L);

        // User session: [3000, 5000]
        Date uStart = new Date(3000L);
        Date uEnd   = new Date(5000L);

        // Here:
        // targetStart.before(userEnd) → 1000<5000 = true
        // userStart.before(targetEnd) → 3000<4000 = true, so adjust to false
        uStart = new Date(5000L);          // userStart > targetEnd

        // Now:
        // targetStart.before(userEnd) → 1000<5000 = true
        // userStart.before(targetEnd) → 5000<4000 = false

        dao.targetSession = new Document("_id", new ObjectId())
                .append("owner", owner)
                .append("startTime", tStart)
                .append("endTime", tEnd);

        Document sess = new Document("_id", new ObjectId())
                .append("startTime", uStart)
                .append("endTime", uEnd);
        dao.userSessions = Arrays.asList(sess);

        new AcceptInvitationInteractor(presenter, dao)
                .acceptInvitation(new AcceptInvitationInputData(username, owner));

        // again no conflict, should add participant
        assertTrue(dao.addCalled);
        assertNull(presenter.lastError);
    }

    // Short‑circuit branch: first condition false, second not evaluated
    @Test
    public void hasTimeConflict_firstConditionFalse_shortCircuitsSecond() {
        String username = "max";
        String owner = "Tim";

        FakePresenter presenter = new FakePresenter();
        FakeDao dao = new FakeDao();

        // Target: [3000, 4000]
        Date tStart = new Date(3000L);
        Date tEnd   = new Date(4000L);

        // User session: end BEFORE targetStart, start is null
        Date uStart = null;                // would NPE if second condition ran
        Date uEnd   = new Date(2000L);     // so targetStart.before(userEnd) is false

        dao.targetSession = new Document("_id", new ObjectId())
                .append("owner", owner)
                .append("startTime", tStart)
                .append("endTime", tEnd);

        Document sess = new Document("_id", new ObjectId())
                .append("startTime", uStart)   // null
                .append("endTime", uEnd);
        dao.userSessions = Arrays.asList(sess);

        AcceptInvitationInteractor interactor =
                new AcceptInvitationInteractor(presenter, dao);

        interactor.acceptInvitation(new AcceptInvitationInputData(username, owner));

        // No conflict, and no NPE ⇒ second condition was skipped
        assertTrue(dao.addCalled);
        assertNull(presenter.lastError);
    }

    @Test
    public void hasTimeConflict_userEndNull_only_triggersContinue() {
        String username = "max";
        String owner = "Tim";

        FakePresenter presenter = new FakePresenter();
        FakeDao dao = new FakeDao();

        // Normal target session
        Document target = new Document("_id", new ObjectId())
                .append("owner", owner)
                .append("startTime", new Date(1000L))
                .append("endTime", new Date(2000L));
        dao.targetSession = target;

        // User session with non‑null startTime but null endTime
        Document session = new Document("_id", new ObjectId())
                .append("startTime", new Date(1500L))  // NOT null
                .append("endTime", null);              // ONLY endTime null
        dao.userSessions = Arrays.asList(session);

        AcceptInvitationInteractor interactor =
                new AcceptInvitationInteractor(presenter, dao);

        interactor.acceptInvitation(new AcceptInvitationInputData(username, owner));

        // Because of 'continue', this session is ignored → no conflict, user added
        assertTrue(dao.addCalled);
        assertNull(presenter.lastError);
    }


    // hasTimeConflict = false, session != null, normal success
    @Test
    public void acceptInvitation_noConflict_addsParticipant() {
        String username = "max";
        String owner = "Tim";

        FakePresenter presenter = new FakePresenter();
        FakeDao dao = new FakeDao();

        Document target = new Document("_id", new ObjectId())
                .append("owner", owner)
                .append("startTime", new Date(1000L))
                .append("endTime", new Date(2000L));
        dao.targetSession = target;

        // non‑overlapping user session → no conflict
        Document other = new Document("_id", new ObjectId())
                .append("startTime", new Date(3000L))
                .append("endTime", new Date(4000L));
        dao.userSessions = Arrays.asList(other);

        AcceptInvitationInteractor interactor =
                new AcceptInvitationInteractor(presenter, dao);

        interactor.acceptInvitation(new AcceptInvitationInputData(username, owner));

        assertTrue(dao.addCalled);
        assertEquals(username, dao.addedUsername);
        assertNull(presenter.lastError);
    }

    // hasTimeConflict = true → first if branch
    @Test
    public void acceptInvitation_timeConflict_triggersErrorAndNoAdd() {
        String username = "max";
        String owner = "Tim";

        FakePresenter presenter = new FakePresenter();
        FakeDao dao = new FakeDao();

        Document target = new Document("_id", new ObjectId())
                .append("owner", owner)
                .append("startTime", new Date(1000L))
                .append("endTime", new Date(2000L));
        dao.targetSession = target;

        // overlapping session
        Document overlap = new Document("_id", new ObjectId())
                .append("startTime", new Date(1500L))
                .append("endTime", new Date(2500L));
        dao.userSessions = Arrays.asList(overlap);

        AcceptInvitationInteractor interactor =
                new AcceptInvitationInteractor(presenter, dao);

        interactor.acceptInvitation(new AcceptInvitationInputData(username, owner));

        assertFalse(dao.addCalled);
        assertEquals("time conflicts", presenter.lastError);
    }

    // session == null branch in acceptInvitation
    @Test
    public void acceptInvitation_noSessionForOwner_triggersError() {
        String username = "max";
        String owner = "DoesNotExist";

        FakePresenter presenter = new FakePresenter();
        FakeDao dao = new FakeDao(); // targetSession remains null

        AcceptInvitationInteractor interactor =
                new AcceptInvitationInteractor(presenter, dao);

        interactor.acceptInvitation(new AcceptInvitationInputData(username, owner));

        assertFalse(dao.addCalled);
        assertEquals("No session found for owner: " + owner, presenter.lastError);
    }

    // targetSession == null branch INSIDE hasTimeConflict
    @Test
    public void hasTimeConflict_targetSessionNull_branchCovered() {
        String username = "max";
        String owner = "Tim";

        FakePresenter presenter = new FakePresenter();

        // DAO that returns null for the first call (from hasTimeConflict)
        // and a real session for the second call (from acceptInvitation).
        AcceptInvitationDataAccessInterface dao = new FakeDao() {
            int calls = 0;

            @Override
            public Document findInvitationByOwner(String ownerName) {
                calls++;
                if (calls == 1) {
                    return null; // hasTimeConflict: targetSession == null → returns false
                }
                return new Document("_id", new ObjectId())
                        .append("owner", owner)
                        .append("startTime", new Date(1000L))
                        .append("endTime", new Date(2000L));
            }
        };

        AcceptInvitationInteractor interactor =
                new AcceptInvitationInteractor(presenter, dao);

        interactor.acceptInvitation(new AcceptInvitationInputData(username, owner));

        assertTrue(((FakeDao) dao).addCalled);
        assertNull(presenter.lastError);
    }

    // targetStart == null || targetEnd == null branch → returns false
    @Test
    public void hasTimeConflict_targetMissingTimes_returnsFalse() {
        String username = "max";
        String owner = "Tim";

        FakePresenter presenter = new FakePresenter();
        FakeDao dao = new FakeDao();

        Document target = new Document("_id", new ObjectId())
                .append("owner", owner)
                .append("startTime", null)
                .append("endTime", null);
        dao.targetSession = target;

        dao.userSessions = Collections.emptyList();

        AcceptInvitationInteractor interactor =
                new AcceptInvitationInteractor(presenter, dao);
        interactor.acceptInvitation(new AcceptInvitationInputData(username, owner));

        assertTrue(dao.addCalled);
        assertNull(presenter.lastError);
    }

    // userStart == null || userEnd == null branch (continue)
    @Test
    public void hasTimeConflict_userSessionMissingTimes_continueBranch() {
        String username = "max";
        String owner = "Tim";

        FakePresenter presenter = new FakePresenter();
        FakeDao dao = new FakeDao();

        Document target = new Document("_id", new ObjectId())
                .append("owner", owner)
                .append("startTime", new Date(1000L))
                .append("endTime", new Date(2000L));
        dao.targetSession = target;

        // bad session triggers continue; second is non‑overlapping → no conflict
        Document bad = new Document("_id", new ObjectId())
                .append("startTime", null)
                .append("endTime", null);
        Document nonOverlap = new Document("_id", new ObjectId())
                .append("startTime", new Date(3000L))
                .append("endTime", new Date(4000L));
        dao.userSessions = Arrays.asList(bad, nonOverlap);

        AcceptInvitationInteractor interactor =
                new AcceptInvitationInteractor(presenter, dao);

        interactor.acceptInvitation(new AcceptInvitationInputData(username, owner));

        assertTrue(dao.addCalled);
        assertNull(presenter.lastError);
    }

    // overlap condition tested both false (first session) and true (second)
    @Test
    public void hasTimeConflict_multipleSessions_trueAndFalseOverlap() {
        String username = "max";
        String owner = "Tim";

        FakePresenter presenter = new FakePresenter();
        FakeDao dao = new FakeDao();

        Document target = new Document("_id", new ObjectId())
                .append("owner", owner)
                .append("startTime", new Date(1000L))
                .append("endTime", new Date(2000L));
        dao.targetSession = target;

        Document nonOverlap = new Document("_id", new ObjectId())
                .append("startTime", new Date(3000L))
                .append("endTime", new Date(4000L));
        Document overlap = new Document("_id", new ObjectId())
                .append("startTime", new Date(1500L))
                .append("endTime", new Date(2500L));
        dao.userSessions = Arrays.asList(nonOverlap, overlap);

        AcceptInvitationInteractor interactor =
                new AcceptInvitationInteractor(presenter, dao);

        interactor.acceptInvitation(new AcceptInvitationInputData(username, owner));

        assertFalse(dao.addCalled);
        assertEquals("time conflicts", presenter.lastError);
    }
}
