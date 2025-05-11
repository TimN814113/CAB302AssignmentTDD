import org.java.runtimers.quizaiproject.QuizModel.Choice;
import org.java.runtimers.quizaiproject.QuizModel.QuizDAO;
import org.java.runtimers.quizaiproject.QuizModel.Question;
import org.junit.jupiter.api.*;
import org.java.runtimers.quizaiproject.ContactModel.SqliteConnection;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class QuizDaoTest {
    private QuizDAO quizDAO;
    private Connection connection;
    private int testQuizId = -1;
    private int testQuestionId = -1;
    private int testAttemptId = -1;

    @BeforeEach
    public void setup() throws SQLException {
        connection = SqliteConnection.getInstance();
        createTables();
        quizDAO = new QuizDAO();
    }

    private void createTables() {
        try (Statement stmt = connection.createStatement()) {

            stmt.execute("CREATE TABLE IF NOT EXISTS Quiz (" +
                    "quiz_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "title TEXT NOT NULL)");

            stmt.execute("CREATE TABLE IF NOT EXISTS Question (" +
                    "question_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "quiz_id INTEGER NOT NULL," +
                    "question_number INTEGER NOT NULL," +
                    "question_text TEXT NOT NULL," +
                    "FOREIGN KEY (quiz_id) REFERENCES Quiz(quiz_id))");

            stmt.execute("CREATE TABLE IF NOT EXISTS Choice (" +
                    "choice_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "question_id INTEGER NOT NULL," +
                    "choice_number INTEGER NOT NULL," +
                    "choice_text TEXT NOT NULL," +
                    "is_correct BOOLEAN NOT NULL," +
                    "FOREIGN KEY (question_id) REFERENCES Question(question_id))");

            stmt.execute("CREATE TABLE IF NOT EXISTS User_to_Quiz (" +
                    "User_Id INTEGER NOT NULL," +
                    "Quiz_Id INTEGER NOT NULL," +
                    "FOREIGN KEY (User_Id) REFERENCES contacts(id)," +
                    "FOREIGN KEY (Quiz_Id) REFERENCES Quiz(quiz_id))");

            stmt.execute("CREATE TABLE IF NOT EXISTS quiz_attempts (" +
                    "attempt_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "quiz_id INTEGER NOT NULL," +
                    "FOREIGN KEY (quiz_id) REFERENCES Quiz(quiz_id))");

            stmt.execute("CREATE TABLE IF NOT EXISTS quiz_results (" +
                    "result_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "attempt_id INTEGER NOT NULL," +
                    "question_id INTEGER NOT NULL," +
                    "correct_response BOOLEAN NOT NULL," +
                    "FOREIGN KEY (attempt_id) REFERENCES quiz_attempts(attempt_id)," +
                    "FOREIGN KEY (question_id) REFERENCES Question(question_id))");


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    void cleanup() throws SQLException {
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

        if (testQuestionId > 0) {
            try (PreparedStatement stmt = connection.prepareStatement("DELETE FROM Choice WHERE question_id = ?")) {
                stmt.setInt(1, testQuestionId);
                stmt.executeUpdate();
            }
            try (PreparedStatement stmt = connection.prepareStatement("DELETE FROM Question WHERE question_id = ?")) {
                stmt.setInt(1, testQuestionId);
                stmt.executeUpdate();
            }
        }

        if (testQuizId > 0) {
            try (PreparedStatement stmt = connection.prepareStatement("DELETE FROM Quiz WHERE quiz_id = ?")) {
                stmt.setInt(1, testQuizId);
                stmt.executeUpdate();
            }
        }

        testAttemptId = -1;
        testQuestionId = -1;
        testQuizId = -1;
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
        quizDAO.addQuiz("Temp Quiz");
        var rs = connection.createStatement().executeQuery("SELECT quiz_id FROM Quiz WHERE title = 'Temp Quiz'");
        assertTrue(rs.next());
        testQuizId = rs.getInt("quiz_id");

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
        quizDAO.addQuiz("Temp Quiz");
        var rs = connection.createStatement().executeQuery("SELECT quiz_id FROM Quiz WHERE title = 'Temp Quiz'");
        assertTrue(rs.next());
        testQuizId = rs.getInt("quiz_id");

        testQuestionId = quizDAO.addQuestion(testQuizId, 1, "Question for Choices");

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
        quizDAO.addQuiz("Quiz for Attempt");
        var rs = connection.createStatement().executeQuery("SELECT quiz_id FROM Quiz WHERE title = 'Quiz for Attempt'");
        assertTrue(rs.next());
        testQuizId = rs.getInt("quiz_id");

        testQuestionId = quizDAO.addQuestion(testQuizId, 1, "Scoring Question");

        testAttemptId = quizDAO.createQuizAttempt(testQuizId);
        assertTrue(testAttemptId > 0);

        quizDAO.insertQuizResult(testAttemptId, testQuestionId, true);
        quizDAO.insertQuizResult(testAttemptId, testQuestionId, false); // duplicate, one incorrect

        int score = quizDAO.getScoreForAttempt(testAttemptId);
        assertEquals(1, score); // Only one correct counted
    }
}
