import org.java.runtimers.quizaiproject.ContactModel.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    User user1;
    User user2;
    User user3;
    User user4;

    String email1 = "nguyenmanh19042004@gmail.com";
    String password1 = "Qwerty!12345";


    @BeforeEach
    public void setup(){
        user1 = new User ("John", "Smith","abc@gmail.com","JohnSmith");
        user2 = new User ("NBA", "YoungBoy","YB@gmail.com","StolenGlock");
        user3 = new User ("Manh", "Nguyen", "nguyenmanh19042004@gmail.com",
                "Nguyenm@nh19042004");
        user4 = new User ("BoyYoung", "NBA", "YBY@gmail.com", "Qwerty!12345");
    }

    @Test
    public void testFirstName(){
        assertEquals("John", user1.getFirstName());
        assertEquals("NBA", user2.getFirstName());
    }

    @Test
    public void testLastNAme(){
        assertEquals("Smith", user1.getLastName());
        assertEquals("YoungBoy", user2.getLastName());
    }

    @Test
    public void testHash(){
        assertEquals("-289043920", User.Hash(email1));
        assertEquals("30702548", User.Hash(password1));
    }

    @Test
    public void testValidPassword(){
        assertTrue(user4.checkValidPassword("Qwerty!12345"));
        assertTrue(user3.checkValidPassword("Nguyenm@nh19042004"));
        assertFalse(user1.checkValidPassword("lowercase")); // Missing uppercase, digit, and special character
        assertFalse(user1.checkValidPassword("UPPERCASE")); // Missing lowercase, digit, and special character
        assertFalse(user1.checkValidPassword("123456789")); // Missing uppercase, lowercase, and special character
        assertFalse(user1.checkValidPassword("Special!")); // Missing uppercase, lowercase, and digit
        assertFalse(user1.checkValidPassword("Upper1")); // Missing lowercase and special character
        assertFalse(user1.checkValidPassword("lower1")); // Missing uppercase and special character
        assertFalse(user1.checkValidPassword("Upper!")); // Missing lowercase and digit
        assertFalse(user1.checkValidPassword("lower!")); // Missing uppercase and digit
        assertFalse(user1.checkValidPassword("UpperLower")); // Missing digit and special character
        assertFalse(user1.checkValidPassword("UpperLower1")); // Missing special character
        assertFalse(user1.checkValidPassword(" ")); // Input is null
    }

    @Test
    public void testValidEmail() {
        assertTrue(user1.checkValidEmail("abc@gmail.com")); // Valid gmail
        assertTrue(user3.checkValidEmail("nguyenmanh19042004@gmail.com")); // Valid gmail
        assertTrue(user2.checkValidEmail("YB@mail.com")); // Valid mail.com
        assertFalse(user4.checkValidEmail("email")); // Missing "@"
        assertFalse(user1.checkValidEmail(".com")); // Missing "@" and valid domain
        assertFalse(user2.checkValidEmail("@gmail.com")); // Missing prefix
        assertFalse(user3.checkValidEmail("YoungBoy@")); // Missing suffix
        assertFalse(user4.checkValidEmail("YoungBoy@domain")); // Invalid domain
        assertFalse(user1.checkValidEmail("YoungBoy@.com")); // Special character as prefix
        assertFalse(user2.checkValidEmail(".Gmail@com")); // Special character as prefix
        assertFalse(user1.checkValidEmail(" ")); // Input is null

    }


}
