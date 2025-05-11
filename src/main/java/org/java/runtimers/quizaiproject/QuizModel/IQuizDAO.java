package org.java.runtimers.quizaiproject.QuizModel;

import java.sql.SQLException;
import java.util.List;

public interface IQuizDAO {

    public void addQuiz(String title);

    public int addQuestion(int quizId, int questionNumber, String questionText) throws SQLException;

    public void addChoice(int questionId, int choiceNumber, String choiceText, boolean isCorrect) throws SQLException;

    public List<Question> getQuestionsForQuiz(int quizId) throws SQLException;

    public List<Choice> getChoicesForQuestion(int questionId) throws SQLException;

    public String GetQuizTitleFromId(int quizId) throws SQLException;

    public int createQuizAttempt(int quizId) throws SQLException;

    public void insertQuizResult(int attemptId, int questionId, boolean correct) throws SQLException;

    public int getScoreForAttempt(int attemptId) throws SQLException;

    public List<Integer> getQuizIdsForUser(int userId);

    List<Integer> getAttemptIdsForQuiz(int quizId);

    public void insertQuizResultNew(int attemptId, int questionId, boolean correct, int activeQuizId) throws SQLException;
}
