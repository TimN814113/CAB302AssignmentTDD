import org.java.runtimers.quizaiproject.QuizModel.Choice;
import org.java.runtimers.quizaiproject.QuizModel.Quiz;
import org.java.runtimers.quizaiproject.QuizModel.QuizDAO;
import org.java.runtimers.quizaiproject.QuizModel.Question;
import org.junit.jupiter.api.*;
import org.java.runtimers.quizaiproject.ContactModel.SqliteConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class QuizTest {

    private static final String TEST_QUIZ_TITLE = "Sample Quiz";
    private int insertedQuizId = -1;
    private QuizDAO quizDAO;
    private Connection connection;
    private int testQuizId;
    private int testQuestionId;
    private int testAttemptId;

    @BeforeEach
    public void setup() throws SQLException {
        quizDAO = new QuizDAO();
        connection = SqliteConnection.getInstance();
    }

    @AfterEach
    public void cleanup() throws SQLException {
        // Remove the quiz inserted by the test
        if (insertedQuizId != -1) {
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM Quiz WHERE quiz_id = ?");
            stmt.setInt(1, insertedQuizId);
            stmt.executeUpdate();

        }
    }

    @Test
    public void testQuizConstructorAndAddQuestion() throws SQLException {
        // Act
        Quiz quiz = new Quiz(TEST_QUIZ_TITLE);
        insertedQuizId = quiz.quizid;  // Save for cleanup

        // Assert
        assertNotNull(quiz);
        assertEquals(TEST_QUIZ_TITLE, quiz.quizTitle);
        assertTrue(quiz.quizid > 0, "Quiz ID should be a positive integer");

    }
}
