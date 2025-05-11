package org.java.runtimers.quizaiproject.generationModel;

import org.java.runtimers.quizaiproject.ollama.OllamaResponse;
import org.java.runtimers.quizaiproject.ollama.OllamaResponseFetcher;
import org.java.runtimers.quizaiproject.ollama.ResponseListener;

import java.sql.SQLException;

public class AsynchronousQuery {
    public static String apiURL = "http://127.0.0.1:11434/api/generate/";
    public static String model = "llama3.1";
    public String prompt;
    public String quizprompt;

    //Use this for a normal prompt and response
    static class MyResponseListener implements ResponseListener {

        @Override
        public void onResponseReceived(OllamaResponse response) {
            System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            System.out.print("Ollama says: ");
            System.out.println(response.getResponse());
            System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        }
    };

    //only used for creating a quiz
    class MyQuizResponseListener implements ResponseListener {

        @Override
        public void onResponseReceived(OllamaResponse response) throws SQLException {
            System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            System.out.print("Ollama says you made this quiz:" );
            System.out.println(response.getResponse());
            QuizGeneration quizGeneration = new QuizGeneration(prompt,quizprompt,response.getResponse());

            System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++");

        }
    };

    /*
    public static void main(String[] args) {
        String apiURL = "http://127.0.0.1:11434/api/generate/";
        String model = "llama3.1";
        String prompt = "Why did the chicken try to cross the road?";

        OllamaResponseFetcher fetcher = new OllamaResponseFetcher(apiURL);
        fetcher.fetchAsynchronousOllamaResponse(model, prompt, new MyResponseListener());

        // note that fetcher returns immediately, and the answer is printed by MyResponseListener when a response becomes available
        System.out.println("======================================================");
        System.out.print("You asked: ");
        System.out.println(prompt);
        System.out.println("======================================================");
    }

     */

    public AsynchronousQuery(String prompt){
        this.prompt = prompt;

        //OllamaResponseFetcher fetcher = new OllamaResponseFetcher(apiURL);
        //fetcher.fetchAsynchronousOllamaResponse(model, prompt, new MyQuizResponseListener());

        System.out.println("======================================================");
        System.out.print("Generated async setup ");
        System.out.println(prompt);
        System.out.println("======================================================");

    }

    //normal prompt and repsponse
    public void promptResponse(){

        OllamaResponseFetcher fetcher = new OllamaResponseFetcher(apiURL);
        fetcher.fetchAsynchronousOllamaResponse(model, this.prompt, new MyResponseListener());

        System.out.println("======================================================");
        System.out.print("You asked: ");
        System.out.println(prompt);
        System.out.println("======================================================");
    }

    public void createQuiz(){
        OllamaResponseFetcher fetcher = new OllamaResponseFetcher(apiURL);
        setConstructedPrompt();
        System.out.println(this.prompt);
        fetcher.fetchAsynchronousOllamaResponse(model, this.quizprompt, new MyQuizResponseListener());

        System.out.println("======================================================");
        System.out.print("You asked: ");
        System.out.println(this.prompt);
        System.out.println("======================================================");
    }

    //used for creating
    private void setConstructedPrompt() {
        String jsonExample = """
        [
          {
            "questionText": "What is the main purpose of photosynthesis?",
            "choices": [
              { "choiceText": "To produce oxygen", "isCorrect": false },
              { "choiceText": "To convert light energy into chemical energy", "isCorrect": true },
              { "choiceText": "To absorb water from soil", "isCorrect": false },
              { "choiceText": "To store heat energy", "isCorrect": false }
            ]
          }
        ]
        """;

        this.quizprompt = """
        You are a quiz generator. Generate a multiple-choice quiz about %s.
        The quiz must contain 10 questions, each with exactly 4 choices and one correct answer.
        Return the data as a JSON array using the structure below.
        Do not include any introductory or closing text.
        The first and last characters must be '[' and ']' respectively.
        Here is an example:
        %s
        """.formatted(prompt, jsonExample);

        System.out.println(this.prompt);
        System.out.println(this.quizprompt);
    }

}
