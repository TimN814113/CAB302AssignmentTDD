package org.java.runtimers.quizaiproject.QuizModel;

import org.java.runtimers.quizaiproject.ContactModel.SqliteConnection;
import org.java.runtimers.quizaiproject.controller.QuizTestViewController;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.java.runtimers.quizaiproject.controller.ResultsViewController.login;

public class QuizDAO implements IQuizDAO{

    private Connection connection;

    public QuizDAO() {
        connection = SqliteConnection.getInstance();

    }

    //change to return type of integer so we can use this for something

    public void addQuiz(String title) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO Quiz (title) VALUES (?)");
            statement.setString(1, title);
            statement.executeUpdate();
            int id = GetQuizIdFromTitle(title);
            int userid = login.getLogin_id();
            System.out.println(id);
            System.out.println(userid);
            QuizTestViewController.setActiveQuizId(id);

            PreparedStatement statement1 = connection.prepareStatement(
                    "INSERT INTO User_to_Quiz (User_Id, Quiz_Id) VALUES (?,?)");
            statement1.setInt(1, userid);
            statement1.setInt(2, id);
            statement1.executeUpdate();

        }

        catch (Exception e) {
            e.printStackTrace();
        }

    }

    public int addQuestion(int quizId, int questionNumber, String questionText) throws SQLException{
        String sql = "INSERT INTO Question (quiz_id, question_number, question_text) VALUES (?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, quizId);
            statement.setInt(2, questionNumber);
            statement.setString(3, questionText);
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            }
        }

        catch (Exception e) {
            e.printStackTrace();
        }
        throw new SQLException("Failed to insert question.");
    }

    public void addChoice(int questionId, int choiceNumber, String choiceText, boolean isCorrect) throws SQLException {
        String sql = "INSERT INTO Choice (question_id, choice_number, choice_text, is_correct) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, questionId);
            pstmt.setInt(2, choiceNumber);
            pstmt.setString(3, choiceText);
            pstmt.setBoolean(4, isCorrect);
            pstmt.executeUpdate();
        }
    }

    public String GetQuizTitleFromId(int quizId) throws SQLException{
        String sql = "SELECT * FROM Quiz WHERE quiz_id = ? ORDER BY quiz_id";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, quizId);
            ResultSet rs = pstmt.executeQuery();
            return rs.getString("title");
        }

    }

    public int GetQuizIdFromTitle(String title) throws SQLException{
        String sql = "SELECT quiz_id FROM Quiz WHERE title = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, title);
            ResultSet rs = pstmt.executeQuery();
            return rs.getInt("quiz_id");
        }

    }

    public int getQuestionIDFromQuizIdQuestionNumber(int quizid, int questionnumber) throws SQLException{
    String sql = "Select question_id FROM Question WHERE quiz_id = ? AND question_number = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, quizid);
            pstmt.setInt(2, questionnumber);
            ResultSet rs = pstmt.executeQuery();
            return rs.getInt("question_id");
        }

    }

    public List<Question> getQuestionsForQuiz(int quizId) throws SQLException {
        String sql = "SELECT * FROM Question WHERE quiz_id = ? ORDER BY question_number";
        List<Question> questions = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, quizId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                questions.add(new Question(
                        rs.getInt("question_id"),
                        rs.getInt("quiz_id"),
                        rs.getInt("question_number"),
                        rs.getString("question_text")
                ));
            }
        }
        return questions;
    }

    public List<Choice> getChoicesForQuestion(int questionId) throws SQLException {
        String sql = "SELECT * FROM Choice WHERE question_id = ? ORDER BY choice_number";
        List<Choice> choices = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, questionId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                choices.add(new Choice(
                        rs.getInt("choice_id"),
                        rs.getInt("question_id"),
                        rs.getInt("choice_number"),
                        rs.getString("choice_text"),
                        rs.getBoolean("is_correct")
                ));
            }
        }
        return choices;
    }

    public int createQuizAttempt(int quizId) throws SQLException {
        String sql = "INSERT INTO quiz_attempts (quiz_id) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, quizId);
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        throw new SQLException("Failed to create quiz attempt.");
    }

    public void insertQuizResult(int attemptId, int questionId, boolean correct) throws SQLException {
        String sql = "INSERT INTO quiz_results (attempt_id, question_id, correct_response) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, attemptId);
            stmt.setInt(2, questionId);
            stmt.setBoolean(3, correct);
            stmt.executeUpdate();
        }
    }

    public void insertQuizResultNew(int attemptId, int questionId, boolean correct, int quizId) throws SQLException {
        String sql = "INSERT INTO quiz_results_new (attempt_id, question_id, correct_response, quiz_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, attemptId);
            stmt.setInt(2, questionId);
            stmt.setBoolean(3, correct);
            stmt.setInt(4, quizId);
            stmt.executeUpdate();
        }
    }

    public int getScoreForAttempt(int attemptId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM quiz_results WHERE attempt_id = ? AND correct_response = 1";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, attemptId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return 0;
    }

    public List<Integer> getQuizIdsForUser(int userId) {
        List<Integer> quizIds = new ArrayList<>();
        String query = "SELECT Quiz_Id FROM User_to_Quiz WHERE User_Id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                quizIds.add(rs.getInt("Quiz_Id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching quiz IDs for user", e);
        }

        return quizIds;
    }

    public List<Integer> getAttemptIdsForQuiz(int quizId) {
        List<Integer> attemptIds = new ArrayList<>();
        String query = "SELECT attempt_id FROM quiz_attempts WHERE quiz_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, quizId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                attemptIds.add(rs.getInt("attempt_id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching attempt IDs for quiz", e);
        }

        return attemptIds;
    }




}
