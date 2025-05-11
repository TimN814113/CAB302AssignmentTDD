package org.java.runtimers.quizaiproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import org.kordamp.bootstrapfx.BootstrapFX;

import org.kordamp.bootstrapfx.scene.layout.Panel;

public class HelloApplication extends Application {

    public static final String TITLE = "Java Runtimers AI Tutor/ Quiz ";
    public static final int WIDTH = 360;
    public static final int HEIGHT = 520;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), WIDTH, HEIGHT);

        //Get bootstrap stylesheet
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());

        //Get custom Stylesheet Css
        String stylesheet = HelloApplication.class.getResource("stylesheet.css").toExternalForm();
        scene.getStylesheets().add(stylesheet);

        stage.setTitle(TITLE);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}