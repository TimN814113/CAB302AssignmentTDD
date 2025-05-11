package org.java.runtimers.quizaiproject.generationModel;
import org.java.runtimers.quizaiproject.QuizModel.*;

import java.util.List;

import java.sql.SQLException;

import static org.java.runtimers.quizaiproject.controller.ResultsViewController.login;

public class QuizGeneration {
    private String prompt;

    public QuizGeneration(String title, String prompt, String fullResponse) throws SQLException {
        //ollamaHandler generatedresponse = getBuiltResponse(prompt);
        //String fullResponse = generatedresponse.response;

        Quiz quiz = new Quiz(title);
        int quizid = quiz.quizid;
        QuizDAO quizDAO = new QuizDAO();
        List<Question> questions = quizParser.parseQuiz(fullResponse);

        // Now you can loop over the questions and store them into your database
        int questions_index = 1;
        int choices_index = 1;
        for (Question q : questions) {
            System.out.println(q.questionText);
            quizDAO.addQuestion(quizid, questions_index++,q.questionText);
            int question_id = quizDAO.getQuestionIDFromQuizIdQuestionNumber(quizid,questions_index-1);
            choices_index = 1;
            for (Choice c : q.choices) {
                System.out.println(" - " + c.choiceText + " (correct: " + c.isCorrect + ")");
                quizDAO.addChoice(question_id,choices_index++,c.choiceText,c.isCorrect);
            }
        }



    }

    private static ollamaHandler getBuiltResponse(String prompt) {
        String test_prompt = "You are a quiz generator. Based on a topic and given format, output a multiple-choice quiz with 10 questions, each with 4 choices, one of which is correct. Return the data as structured JSON matching the schema provided. When returning an output do not put any filler words or text before or after the structured json schema so that it can be easily parsed. The first and last characters of the string must always be '[', and ']' correspondingly. Double check your response after to ensure teh first and last characters of the out put is [ and ]. Create a quiz about " + prompt + ". Each question must have 4 options with one correct answer. Use this JSON structure: " +
                "[\n" +
                "  {\n" +
                "    \"questionText\": \"What is the main purpose of photosynthesis?\",\n" +
                "    \"choices\": [\n" +
                "      { \"choiceText\": \"To produce oxygen\", \"isCorrect\": false },\n" +
                "      { \"choiceText\": \"To convert light energy into chemical energy\", \"isCorrect\": true },\n" +
                "      { \"choiceText\": \"To absorb water from soil\", \"isCorrect\": false },\n" +
                "      { \"choiceText\": \"To store heat energy\", \"isCorrect\": false }\n" +
                "    ]\n" +
                "  },\n" +
                "  ...\n" +
                "]";

        ollamaHandler generatedresponse = new ollamaHandler(test_prompt);
        return generatedresponse;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public String getPrompt() {
        return this.prompt;
    }


}
