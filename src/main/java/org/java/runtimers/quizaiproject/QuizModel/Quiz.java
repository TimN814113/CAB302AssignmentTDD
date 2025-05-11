package org.java.runtimers.quizaiproject.QuizModel;

import org.java.runtimers.quizaiproject.ContactModel.SqliteConnection;
import org.java.runtimers.quizaiproject.QuizModel.QuizDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Quiz {
    public int quizid;
    public String quizTitle;
    public List<Question> Questions;

    //Creates quiz object and adds to the database
    public Quiz(String quizTitle) throws SQLException {
        this.quizTitle = quizTitle;
        QuizDAO quizDAO = new QuizDAO();
        quizDAO.addQuiz(quizTitle);
        this.quizid = quizDAO.GetQuizIdFromTitle(quizTitle);
    }

    public void addQuestion(Question question){
        Questions.add(question);
    }




}
