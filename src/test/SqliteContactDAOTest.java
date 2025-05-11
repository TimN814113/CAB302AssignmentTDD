import org.java.runtimers.quizaiproject.ContactModel.IContactDAO;
import org.java.runtimers.quizaiproject.ContactModel.SqliteContactDAO;
import org.java.runtimers.quizaiproject.ContactModel.SqliteConnection;
import org.java.runtimers.quizaiproject.ContactModel.User;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SqliteContactDAOTest {

    private IContactDAO contactDAO;
    private Connection connection;
    private User testUser;

    @BeforeEach
    public void setup() throws SQLException {
        contactDAO = new SqliteContactDAO();
        connection = SqliteConnection.getInstance();

        testUser = new User("John", "Doe", "john.doe@example.com", "securePass");
        contactDAO.addContact(testUser);
    }

    @AfterEach
    public void cleanup() throws SQLException {
        if (testUser.getId() > 0) {
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM contacts WHERE id = ?");
            stmt.setInt(1, testUser.getId());
            stmt.executeUpdate();
        }
    }

    @Test
    public void testIsUserExist() {
        assertTrue(contactDAO.isUserExist("John", "Doe", "john.doe@example.com"));
        assertFalse(contactDAO.isUserExist("Jane", "Smith", "nonexistent@example.com"));
    }

    @Test
    public void testAuthenticateUser() {
        assertTrue(contactDAO.authenticateUser("john.doe@example.com", "securePass"));
        assertFalse(contactDAO.authenticateUser("john.doe@example.com", "wrongPass"));
    }

    @Test
    public void testGetUserId() {
        int userId = contactDAO.getUserId("john.doe@example.com", "securePass");
        assertEquals(testUser.getId(), userId);

        int invalidId = contactDAO.getUserId("invalid@example.com", "wrongPass");
        assertEquals(0, invalidId);
    }

    @Test
    public void testAddContact() {
        User newUser = new User("Jane", "Smith", "jane.smith@example.com", "pass123");
        contactDAO.addContact(newUser);

        assertTrue(newUser.getId() > 0);
        assertTrue(contactDAO.isUserExist("Jane", "Smith", "jane.smith@example.com"));

        // Clean up
        try (PreparedStatement stmt = connection.prepareStatement("DELETE FROM contacts WHERE id = ?")) {
            stmt.setInt(1, newUser.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            fail("Cleanup failed: " + e.getMessage());
        }
    }

    @Test
    public void testUpdateContact() {
        testUser.setFirstName("Updated");
        testUser.setPassword("NewPass1!");  // Now meets all password rules
        contactDAO.updateContact(testUser);

        User updated = contactDAO.getContact(testUser.getId());
        assertEquals("Updated", updated.getFirstName());
        assertEquals("2068546121", updated.getPassword());
    }


    @Test
    public void testDeleteContact() {
        contactDAO.deleteContact(testUser);

        User deleted = contactDAO.getContact(testUser.getId());
        assertNull(deleted);
    }

    @Test
    public void testGetContact() {
        User fetched = contactDAO.getContact(testUser.getId());
        assertNotNull(fetched);
        assertEquals("John", fetched.getFirstName());
        assertEquals("Doe", fetched.getLastName());
    }

    @Test
    public void testGetAllContacts() {
        List<User> allUsers = contactDAO.getAllContacts();
        assertFalse(allUsers.isEmpty());

        boolean found = allUsers.stream().anyMatch(u -> u.getId() == testUser.getId());
        assertTrue(found);
    }
}
