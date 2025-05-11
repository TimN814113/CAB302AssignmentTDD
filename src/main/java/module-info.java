module org.java.runtimers.quizaiproject {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;
    requires org.json;
    requires com.google.gson;
    requires java.sql;

    exports org.java.runtimers.quizaiproject;
    opens org.java.runtimers.quizaiproject to javafx.fxml;

    exports org.java.runtimers.quizaiproject.controller;
    opens org.java.runtimers.quizaiproject.controller to javafx.fxml;

    exports org.java.runtimers.quizaiproject.ContactModel;
    opens org.java.runtimers.quizaiproject.ContactModel to javafx.fxml;

    exports org.java.runtimers.quizaiproject.QuizModel;
    opens org.java.runtimers.quizaiproject.QuizModel to javafx.fxml;

    opens org.java.runtimers.quizaiproject.ollama to com.google.gson;
}