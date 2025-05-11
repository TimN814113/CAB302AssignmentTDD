
package org.java.runtimers.quizaiproject.QuizModel;

import java.util.List;

public class Question {
    public int questionId;
    public int quizId;
    public int questionNumber;
    public String questionText;
    public List<Choice> choices;

    public Question(int questionId, int quizId, int questionNumber, String questionText) {
        this.questionId = questionId;
        this.quizId = quizId;
        this.questionNumber = questionNumber;
        this.questionText = questionText;
    }

    public Question(int questionNumber, String questionText){
        this.questionNumber = questionNumber;
        this.questionText = questionText;

    }

    public Question(List<Choice> choices, String questionText){
        this.choices = choices;
        this.questionText = questionText;

    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public void addChoice(Choice choice){
        choices.add(choice);
    }


}
