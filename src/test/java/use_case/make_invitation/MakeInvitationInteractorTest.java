package use_case.make_invitation;

import data_access.InMemoryInvitationDataAccessObject;
import entity.Invitation;
import entity.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class MakeInvitationInteractorTest {

    // Test set up

    // Presenter to record the last error/success
    private static class TestPresenter implements MakeInvitationOutputBoundary {

        MakeInvitationOutputData lastSuccess;
        String lastError;

        @Override
        public void presentSuccess(MakeInvitationOutputData response) {
            lastSuccess = response;
        }

        @Override
        public void presentFailure(String errorMessage) {
            lastError = errorMessage;
        }
    }

    // CurrentUserGateway to track current user -> owner
    private static class TestCurrentUserGateway implements CurrentUserGateway {

        private User user;

        void setCurrentUser(User u) {
            this.user = u;
        }

        @Override
        public User getCurrentUser() {
            return user;
        }
    }


    private MakeInvitationInputData makeValidRequest(LocalDate date,
                                                     LocalTime start,
                                                     LocalTime end,
                                                     Integer occupancy,
                                                     String description) {
        return new MakeInvitationInputData(
                "CSC207",         // course
                description,      // description
                date,
                start,
                end,
                "ONLINE",         // mode
                "",               // location
                occupancy
        );
    }

    // new user
    private User makeUser(String name) {
        return new User(name, "pw");
    }

    // Test

    @Test
    void success_whenAllDataValid() {
        InMemoryInvitationDataAccessObject dao =
                new InMemoryInvitationDataAccessObject();
        TestPresenter presenter = new TestPresenter();
        TestCurrentUserGateway currentUser = new TestCurrentUserGateway();
        currentUser.setCurrentUser(makeUser("alice"));

        MakeInvitationInteractor interactor =
                new MakeInvitationInteractor(dao, presenter, currentUser);

        LocalDate tomorrow = LocalDate.now().plusDays(1);
        LocalTime start = LocalTime.of(10, 0);
        LocalTime end = LocalTime.of(11, 0);

        MakeInvitationInputData req =
                makeValidRequest(tomorrow, start, end, 3, "Study group");

        interactor.execute(req);

        assertNull(presenter.lastError, "There should be no error");
        assertNotNull(presenter.lastSuccess, "There should be success");
        assertEquals("Invitation created.", presenter.lastSuccess.getMessage());
        assertEquals(1, dao.getAllInvitations().size(), "There should be one invitation");
    }

    @Test
    void failure_whenCourseBlank() {
        InMemoryInvitationDataAccessObject dao =
                new InMemoryInvitationDataAccessObject();
        TestPresenter presenter = new TestPresenter();
        TestCurrentUserGateway currentUser = new TestCurrentUserGateway();
        currentUser.setCurrentUser(makeUser("alice"));

        MakeInvitationInteractor interactor =
                new MakeInvitationInteractor(dao, presenter, currentUser);

        LocalDate tomorrow = LocalDate.now().plusDays(1);

        MakeInvitationInputData req = new MakeInvitationInputData(
                "",
                "desc",
                tomorrow,
                LocalTime.of(10, 0),
                LocalTime.of(11, 0),
                "ONLINE",
                "",
                3
        );

        interactor.execute(req);

        assertEquals("Course required", presenter.lastError);
        assertNull(presenter.lastSuccess);
        assertEquals(0, dao.getAllInvitations().size());
    }

    @Test
    void failure_whenDateNull() {
        InMemoryInvitationDataAccessObject dao =
                new InMemoryInvitationDataAccessObject();
        TestPresenter presenter = new TestPresenter();
        TestCurrentUserGateway currentUser = new TestCurrentUserGateway();
        currentUser.setCurrentUser(makeUser("alice"));

        MakeInvitationInteractor interactor =
                new MakeInvitationInteractor(dao, presenter, currentUser);

        MakeInvitationInputData req = new MakeInvitationInputData(
                "CSC207",
                "desc",
                null,
                LocalTime.of(10, 0),
                LocalTime.of(11, 0),
                "ONLINE",
                "",
                3
        );

        interactor.execute(req);

        assertEquals("Date required", presenter.lastError);
        assertEquals(0, dao.getAllInvitations().size());
    }

    @Test
    void failure_whenStartOrEndNull() {
        InMemoryInvitationDataAccessObject dao =
                new InMemoryInvitationDataAccessObject();
        TestPresenter presenter = new TestPresenter();
        TestCurrentUserGateway currentUser = new TestCurrentUserGateway();
        currentUser.setCurrentUser(makeUser("alice"));

        MakeInvitationInteractor interactor =
                new MakeInvitationInteractor(dao, presenter, currentUser);

        LocalDate tomorrow = LocalDate.now().plusDays(1);

        MakeInvitationInputData req =
                makeValidRequest(tomorrow, null, LocalTime.of(11, 0),
                        3, "desc");

        interactor.execute(req);

        assertEquals("Start/End required", presenter.lastError);
        assertEquals(0, dao.getAllInvitations().size());
    }

    @Test
    void failure_whenStartNotBeforeEnd() {
        InMemoryInvitationDataAccessObject dao =
                new InMemoryInvitationDataAccessObject();
        TestPresenter presenter = new TestPresenter();
        TestCurrentUserGateway currentUser = new TestCurrentUserGateway();
        currentUser.setCurrentUser(makeUser("alice"));

        MakeInvitationInteractor interactor =
                new MakeInvitationInteractor(dao, presenter, currentUser);

        LocalDate tomorrow = LocalDate.now().plusDays(1);
        LocalTime start = LocalTime.of(11, 0);
        LocalTime end = LocalTime.of(10, 0);

        MakeInvitationInputData req =
                makeValidRequest(tomorrow, start, end, 3, "desc");

        interactor.execute(req);

        assertEquals("Start must be before end", presenter.lastError);
        assertEquals(0, dao.getAllInvitations().size());
    }

    @Test
    void failure_whenDateInPast() {
        InMemoryInvitationDataAccessObject dao =
                new InMemoryInvitationDataAccessObject();
        TestPresenter presenter = new TestPresenter();
        TestCurrentUserGateway currentUser = new TestCurrentUserGateway();
        currentUser.setCurrentUser(makeUser("alice"));

        MakeInvitationInteractor interactor =
                new MakeInvitationInteractor(dao, presenter, currentUser);

        LocalDate yesterday = LocalDate.now().minusDays(1);

        MakeInvitationInputData req =
                makeValidRequest(yesterday, LocalTime.of(10, 0),
                        LocalTime.of(11, 0), 3, "desc");

        interactor.execute(req);

        assertEquals("Date cannot be in the past", presenter.lastError);
        assertEquals(0, dao.getAllInvitations().size());
    }

    @Test
    void failure_whenStartTimeInPastToday() {
        InMemoryInvitationDataAccessObject dao =
                new InMemoryInvitationDataAccessObject();
        TestPresenter presenter = new TestPresenter();
        TestCurrentUserGateway currentUser = new TestCurrentUserGateway();
        currentUser.setCurrentUser(makeUser("alice"));

        MakeInvitationInteractor interactor =
                new MakeInvitationInteractor(dao, presenter, currentUser);

        LocalDate today = LocalDate.now();
        LocalTime start = LocalTime.MIN; // 00:00
        LocalTime end = start.plusHours(1);

        MakeInvitationInputData req =
                makeValidRequest(today, start, end, 3, "desc");

        interactor.execute(req);

        assertEquals("Start time cannot be in the past", presenter.lastError);
        assertEquals(0, dao.getAllInvitations().size());
    }

    @Test
    void failure_whenCapacityTooSmall() {
        InMemoryInvitationDataAccessObject dao =
                new InMemoryInvitationDataAccessObject();
        TestPresenter presenter = new TestPresenter();
        TestCurrentUserGateway currentUser = new TestCurrentUserGateway();
        currentUser.setCurrentUser(makeUser("alice"));

        MakeInvitationInteractor interactor =
                new MakeInvitationInteractor(dao, presenter, currentUser);

        LocalDate tomorrow = LocalDate.now().plusDays(1);

        MakeInvitationInputData req =
                makeValidRequest(tomorrow, LocalTime.of(10, 0),
                        LocalTime.of(11, 0), 1, "desc");

        interactor.execute(req);

        assertEquals("Capacity must be at least 2", presenter.lastError);
        assertEquals(0, dao.getAllInvitations().size());
    }

    @Test
    void success_whenCapacityEqualsTwo() {
        InMemoryInvitationDataAccessObject dao = new InMemoryInvitationDataAccessObject();
        TestPresenter presenter = new TestPresenter();
        TestCurrentUserGateway currentUser = new TestCurrentUserGateway();
        currentUser.setCurrentUser(makeUser("alice"));

        MakeInvitationInteractor interactor =
                new MakeInvitationInteractor(dao, presenter, currentUser);

        LocalDate tomorrow = LocalDate.now().plusDays(1);

        MakeInvitationInputData req =
                makeValidRequest(
                        tomorrow,
                        LocalTime.of(10, 0),
                        LocalTime.of(11, 0),
                        2,
                        "desc"
                );

        interactor.execute(req);

        assertNull(presenter.lastError);
        assertNotNull(presenter.lastSuccess);
        assertEquals(1, dao.getAllInvitations().size());
    }

    @Test
    void failure_whenNotLoggedIn() {
        InMemoryInvitationDataAccessObject dao =
                new InMemoryInvitationDataAccessObject();
        TestPresenter presenter = new TestPresenter();
        TestCurrentUserGateway currentUser = new TestCurrentUserGateway();
        currentUser.setCurrentUser(null);

        MakeInvitationInteractor interactor =
                new MakeInvitationInteractor(dao, presenter, currentUser);

        LocalDate tomorrow = LocalDate.now().plusDays(1);

        MakeInvitationInputData req =
                makeValidRequest(tomorrow, LocalTime.of(10, 0),
                        LocalTime.of(11, 0), 3, "desc");

        interactor.execute(req);

        assertEquals("Not logged in", presenter.lastError);
        assertEquals(0, dao.getAllInvitations().size());
    }

    @Test
    void failure_whenOwnerHasOverlap() {
        InMemoryInvitationDataAccessObject dao =
                new InMemoryInvitationDataAccessObject();
        TestPresenter presenter = new TestPresenter();
        TestCurrentUserGateway currentUser = new TestCurrentUserGateway();

        User owner = makeUser("alice");
        currentUser.setCurrentUser(owner);

        LocalDate date = LocalDate.now().plusDays(1);
        LocalTime start1 = LocalTime.of(10, 0);
        LocalTime end1 = LocalTime.of(11, 0);

        Invitation existing = Invitation.builder()
                .course("CSC207")
                .description("existing")
                .date(date)
                .startTime(start1)
                .endTime(end1)
                .mode("ONLINE")
                .location("")
                .capacity(3)
                .owner(owner)
                .build();

        dao.save(existing);

        MakeInvitationInteractor interactor =
                new MakeInvitationInteractor(dao, presenter, currentUser);

        MakeInvitationInputData req =
                makeValidRequest(date,
                        LocalTime.of(10, 30),
                        LocalTime.of(11, 30),
                        3, "new");

        interactor.execute(req);

        assertEquals("You already have another invitation in this time slot.",
                presenter.lastError);
        // Do not save new invitation
        assertEquals(1, dao.getAllInvitations().size());
    }

    @Test
    void failure_whenExistsOverlapSameCourseAndTime() {
        InMemoryInvitationDataAccessObject dao =
                new InMemoryInvitationDataAccessObject();
        TestPresenter presenter = new TestPresenter();
        TestCurrentUserGateway currentUser = new TestCurrentUserGateway();

        User existingOwner = makeUser("bob");
        LocalDate date = LocalDate.now().plusDays(1);
        LocalTime start1 = LocalTime.of(10, 0);
        LocalTime end1 = LocalTime.of(11, 0);

        Invitation existing = Invitation.builder()
                .course("CSC207")
                .description("existing")
                .date(date)
                .startTime(start1)
                .endTime(end1)
                .mode("ONLINE")
                .location("")
                .capacity(3)
                .owner(existingOwner)
                .build();

        dao.save(existing);

        // current user -> alice，ownerHasOverlap -> false，
        // same course + same date + timeoverlap, existsOverlap -> true。
        currentUser.setCurrentUser(makeUser("alice"));

        MakeInvitationInteractor interactor =
                new MakeInvitationInteractor(dao, presenter, currentUser);

        MakeInvitationInputData req =
                makeValidRequest(date,
                        LocalTime.of(10, 30),
                        LocalTime.of(11, 30),
                        3, "new");

        interactor.execute(req);

        assertEquals("An invitation at this time already exists.",
                presenter.lastError);
        assertEquals(1, dao.getAllInvitations().size());
    }

    @Test
    void success_whenDescriptionNullOrBlank() {
        InMemoryInvitationDataAccessObject dao =
                new InMemoryInvitationDataAccessObject();
        TestPresenter presenter = new TestPresenter();
        TestCurrentUserGateway currentUser = new TestCurrentUserGateway();
        currentUser.setCurrentUser(makeUser("alice"));

        MakeInvitationInteractor interactor =
                new MakeInvitationInteractor(dao, presenter, currentUser);

        LocalDate date = LocalDate.now().plusDays(1);

        MakeInvitationInputData reqNull =
                makeValidRequest(date,
                        LocalTime.of(10, 0),
                        LocalTime.of(11, 0),
                        3, null);

        interactor.execute(reqNull);
        assertNull(presenter.lastError);
        assertNotNull(presenter.lastSuccess);

        presenter.lastSuccess = null;
        presenter.lastError = null;

        MakeInvitationInputData reqBlank =
                makeValidRequest(date,
                        LocalTime.of(12, 0),
                        LocalTime.of(13, 0),
                        3, "   ");

        interactor.execute(reqBlank);
        assertNull(presenter.lastError);
        assertNotNull(presenter.lastSuccess);
    }

    @Test
    void failure_whenDescriptionTooLong() {
        InMemoryInvitationDataAccessObject dao =
                new InMemoryInvitationDataAccessObject();
        TestPresenter presenter = new TestPresenter();
        TestCurrentUserGateway currentUser = new TestCurrentUserGateway();
        currentUser.setCurrentUser(makeUser("alice"));

        MakeInvitationInteractor interactor =
                new MakeInvitationInteractor(dao, presenter, currentUser);

        LocalDate date = LocalDate.now().plusDays(1);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 151; i++) {
            sb.append('a');
        }
        String tooLong = sb.toString();

        MakeInvitationInputData req =
                makeValidRequest(date,
                        LocalTime.of(10, 0),
                        LocalTime.of(11, 0),
                        3, tooLong);

        interactor.execute(req);

        assertEquals("Description must be at most 150 characters",
                presenter.lastError);
        assertEquals(0, dao.getAllInvitations().size());
    }
    @Test
    void failure_whenInPersonWithoutLocation() {
        InMemoryInvitationDataAccessObject dao =
                new InMemoryInvitationDataAccessObject();
        TestPresenter presenter = new TestPresenter();
        TestCurrentUserGateway currentUser = new TestCurrentUserGateway();
        currentUser.setCurrentUser(makeUser("alice"));

        MakeInvitationInteractor interactor =
                new MakeInvitationInteractor(dao, presenter, currentUser);

        LocalDate tomorrow = LocalDate.now().plusDays(1);

        MakeInvitationInputData base =
                makeValidRequest(
                        tomorrow,
                        LocalTime.of(10, 0),
                        LocalTime.of(11, 0),
                        3,
                        "desc"
                );

        MakeInvitationInputData req = new MakeInvitationInputData(
                base.getCourse(),
                base.getDescription(),
                base.getDate(),
                base.getStartTime(),
                base.getEndTime(),
                "IN_PERSON",
                "",
                base.getOccupancy()
        );

        interactor.execute(req);


        assertEquals("Location is required for in-person invitations",
                presenter.lastError);
        assertNull(presenter.lastSuccess);
        assertEquals(0, dao.getAllInvitations().size());
    }
}