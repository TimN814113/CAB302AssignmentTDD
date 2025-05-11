package org.java.runtimers.quizaiproject.results;

public class Results {
    private int score;
    private String quiz_name;
    private int quiz_id;


    public Results(int score, String quiz_name, int quiz_id){
        this.score = score;
        this.quiz_name = quiz_name;
        this.quiz_id = quiz_id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getQuiz_name() { return quiz_name; }

    public void setQuiz_name(String quiz_name) { this.quiz_name = quiz_name; }

    public int getQuiz_id() { return quiz_id; }

    public void setQuiz_id(int quiz_id) { this.quiz_id = quiz_id; }
}

