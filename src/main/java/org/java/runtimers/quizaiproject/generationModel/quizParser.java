package org.java.runtimers.quizaiproject.generationModel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.List;
import org.java.runtimers.quizaiproject.QuizModel.Question;

public class quizParser {
    public static List<Question> parseQuiz(String fullResponse) {
        Gson gson = new Gson();
        System.out.println(fullResponse);


        // Step 1: Find the first '['
        int jsonStart = fullResponse.indexOf('[');
        if (jsonStart == -1) {
            throw new IllegalArgumentException("No JSON array found in the response.");
        }

        // Step 2: Trim to start from '['
        String jsonString = fullResponse.substring(jsonStart).trim();

        // Step 3: Parse with Gson
        return gson.fromJson(jsonString, new TypeToken<List<Question>>() {}.getType());
    }
}
