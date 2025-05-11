import org.java.runtimers.quizaiproject.QuizModel.Choice;
import org.java.runtimers.quizaiproject.QuizModel.QuizDAO;
import org.java.runtimers.quizaiproject.QuizModel.Question;
import org.junit.jupiter.api.*;
import org.java.runtimers.quizaiproject.ContactModel.SqliteConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class QuizDaoTest {
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
    void cleanup() throws SQLException {
        // Delete inserted quiz results
        if (testAttemptId > 0) {
            try (PreparedStatement stmt = connection.prepareStatement("DELETE FROM quiz_results WHERE attempt_id = ?")) {
                stmt.setInt(1, testAttemptId);
                stmt.executeUpdate();
            }

            try (PreparedStatement stmt = connection.prepareStatement("DELETE FROM quiz_attempts WHERE attempt_id = ?")) {
                stmt.setInt(1, testAttemptId);
                stmt.executeUpdate();
            }
        }

        // Delete inserted choices
        if (testQuestionId > 0) {
            try (PreparedStatement stmt = connection.prepareStatement("DELETE FROM Choice WHERE question_id = 0")) {
                //stmt.setInt(1, testQuestionId);
                stmt.executeUpdate();
            }

            // Delete inserted questions
            try (PreparedStatement stmt = connection.prepareStatement("DELETE FROM Question WHERE question_id = ?")) {
                stmt.setInt(1, testQuestionId);
                stmt.executeUpdate();
            }
        }

        // Delete inserted quizzes
        if (testQuizId > 0) {
            try (PreparedStatement stmt = connection.prepareStatement("DELETE FROM Quiz WHERE quiz_id = ?")) {
                stmt.setInt(1, testQuizId);
                stmt.executeUpdate();
            }
        }
    }

    @Test
    void testAddQuizAndGetQuizTitleFromId() throws SQLException {
        quizDAO.addQuiz("Test Quiz Title");

        var rs = connection.createStatement().executeQuery("SELECT quiz_id FROM Quiz WHERE title = 'Test Quiz Title'");
        assertTrue(rs.next());
        testQuizId = rs.getInt("quiz_id");

        String title = quizDAO.GetQuizTitleFromId(testQuizId);
        assertEquals("Test Quiz Title", title);

    }

    @Test
    void testAddQuestionAndGetQuestionsForQuiz() throws SQLException {

        testQuestionId = quizDAO.addQuestion(testQuizId, 1, "Test Question Text");
        assertTrue(testQuestionId > 0);

        List<Question> questions = quizDAO.getQuestionsForQuiz(testQuizId);
        assertFalse(questions.isEmpty());
        Question q = questions.get(0);

        assertEquals(testQuizId, q.getQuizId());
        assertEquals(1, q.getQuestionNumber());
        assertEquals("Test Question Text", q.getQuestionText());
    }

    @Test
    void testAddChoiceAndGetChoicesForQuestion() throws SQLException {
        //assertTrue(testQuestionId > 0, "Question must be added before adding choices.");

        quizDAO.addChoice(testQuestionId, 1, "Choice 1", true);
        quizDAO.addChoice(testQuestionId, 2, "Choice 2", false);

        List<Choice> choices = quizDAO.getChoicesForQuestion(testQuestionId);
        assertEquals(2, choices.size());

        Choice choice1 = choices.get(0);
        assertEquals("Choice 1", choice1.getChoiceText());
        assertTrue(choice1.isCorrect());

        Choice choice2 = choices.get(1);
        assertEquals("Choice 2", choice2.getChoiceText());
        assertFalse(choice2.isCorrect());
    }

    @Test
    void testCreateQuizAttemptAndInsertQuizResultAndGetScoreForAttempt() throws SQLException {
        //assertTrue(testQuizId > 0, "Quiz must be added before creating attempt.");
        //assertTrue(testQuestionId > 0, "Question must be added before creating attempt.");

        testAttemptId = quizDAO.createQuizAttempt(testQuizId);
        assertTrue(testAttemptId > 0);

        quizDAO.insertQuizResult(testAttemptId, testQuestionId, true);
        quizDAO.insertQuizResult(testAttemptId, testQuestionId, false); // an incorrect answer

        int score = quizDAO.getScoreForAttempt(testAttemptId);
        assertEquals(1, score); // Only one correct
    }





}
