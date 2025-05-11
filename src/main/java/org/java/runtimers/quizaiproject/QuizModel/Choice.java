package org.java.runtimers.quizaiproject.QuizModel;

public class Choice {
    public int choiceId;
    public int questionId;
    public int choiceNumber;
    public String choiceText;
    public boolean isCorrect;

    public Choice(int choiceId, int questionId, int choiceNumber, String choiceText, boolean isCorrect) {
        this.choiceId = choiceId;
        this.questionId = questionId;
        this.choiceNumber = choiceNumber;
        this.choiceText = choiceText;
        this.isCorrect = isCorrect;
    }

    public Choice(String choiceText, boolean is_correct) {
        this.choiceText = choiceText;
        this.isCorrect = is_correct;
    }



    public int getChoiceId() {
        return choiceId;
    }

    public void setChoiceId(int choiceId) {
        this.choiceId = choiceId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getChoiceNumber() {
        return choiceNumber;
    }

    public void setChoiceNumber(int choiceNumber) {
        this.choiceNumber = choiceNumber;
    }

    public String getChoiceText() {
        return choiceText;
    }

    public void setChoiceText(String choiceText) {
        this.choiceText = choiceText;
    }

    public boolean isCorrect() {
        return isCorrect;
    }
}
