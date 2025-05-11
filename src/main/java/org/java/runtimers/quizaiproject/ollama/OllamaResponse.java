package org.java.runtimers.quizaiproject.ollama;

import com.google.gson.Gson;

public class OllamaResponse {

    private String model;
    private String created_at;
    private String response;
    private String done;


    public String getResponse() {
        return response;
    }

    public static OllamaResponse fromJson(String body) {
        //for documentation of how to decode JSON response https://github.com/ollama/ollama/blob/main/docs/api.md
        Gson gson = new Gson();
        OllamaResponse response = gson.fromJson(body, OllamaResponse.class);
        return response;
    }
}
