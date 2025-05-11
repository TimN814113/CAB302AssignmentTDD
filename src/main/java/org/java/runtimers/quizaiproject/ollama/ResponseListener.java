package org.java.runtimers.quizaiproject.ollama;

import java.sql.SQLException;

public interface ResponseListener {
    public void onResponseReceived(OllamaResponse response) throws SQLException;
}
