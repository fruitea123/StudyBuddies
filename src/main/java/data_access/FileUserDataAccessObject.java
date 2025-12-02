package data_access;

import entity.User;
import entity.UserFactory;
import usecase.change_password.ChangePasswordUserDataAccessInterface;
import usecase.login.LoginUserDataAccessInterface;
import usecase.logout.LogoutUserDataAccessInterface;
import usecase.signup.SignupUserDataAccessInterface;

import java.io.*;
import java.util.*;

/**
 * DAO for user data implemented using a File to persist the data.
 */
public class FileUserDataAccessObject implements SignupUserDataAccessInterface,
                                                 LoginUserDataAccessInterface,
                                                 ChangePasswordUserDataAccessInterface,
                                                 LogoutUserDataAccessInterface {

    private static final String HEADER = "username,password,firstName,lastName,programs,pfpIndex,description";

    private final File csvFile;
    private final Map<String, Integer> headers = new LinkedHashMap<>();
    private final Map<String, User> accounts = new HashMap<>();

    private String currentUsername;

    /**
     * Construct this DAO for saving to and reading from a local file.
     * @param csvPath the path of the file to save to
     * @param userFactory factory for creating user objects
     * @throws RuntimeException if there is an IOException when accessing the file
     */
    public FileUserDataAccessObject(String csvPath, UserFactory userFactory) {

        csvFile = new File(csvPath);
        headers.put("email", 0);
        headers.put("password", 1);
        headers.put("firstName", 2);
        headers.put("lastName", 3);
        headers.put("programs", 4);
        headers.put("pfpIndex", 5);
        headers.put("description", 6);

        if (csvFile.length() == 0) {
            save();
        }
        else {

            try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
                final String header = reader.readLine();

                if (!header.equals(HEADER)) {
                    throw new RuntimeException(String.format("header should be%n: %s%n but was:%n%s", HEADER, header));
                }

                String row;
                while ((row = reader.readLine()) != null) {
                    final String[] col = row.split(",");
                    final String email = String.valueOf(col[headers.get("username")]);
                    final String password = String.valueOf(col[headers.get("password")]);
                    final String firstName = col[headers.get("firstName")];
                    final String lastName = col[headers.get("lastName")];
                    final List<String> programs = Collections.singletonList(col[headers.get("programs")]);
                    final int pfpIndex = Integer.parseInt(col[headers.get("pfpIndex")]);
                    final String descripion = col[headers.get("description")];
                    final User user = userFactory.create(email, password, firstName, lastName, programs, pfpIndex, descripion);
                    accounts.put(email, user);
                }
            }
            catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private void save() {
        final BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(csvFile));
            writer.write(String.join(",", headers.keySet()));
            writer.newLine();

            for (User user : accounts.values()) {
                final String line = String.format("%s,%s,%s,%s,%s,%d,%s",
                        user.getEmail(),
                        user.getPassword(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getPrograms(),
                        user.getPfpIndex(),
                        user.getDescription());
                writer.write(line);
                writer.newLine();
            }

            writer.close();

        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void save(User user) {
        accounts.put(user.getEmail(), user);
        this.save();
    }

    @Override
    public User get(String email) {
        return accounts.get(email);
    }

    @Override
    public void setCurrentUsername(String email) {
        currentUsername = email;
    }

    @Override
    public String getCurrentUsername() {
        return currentUsername;
    }

    @Override
    public boolean existsByEmail(String identifier) {
        return accounts.containsKey(identifier);
    }

    @Override
    public void changePassword(User user) {
        // Replace the User object in the map
        accounts.put(user.getEmail(), user);
        save();
    }
}
