package org.java.runtimers.quizaiproject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.java.runtimers.quizaiproject.HelloApplication;
import org.java.runtimers.quizaiproject.generationModel.*;
import java.io.IOException;
import java.sql.SQLException;

public class CreateViewController {
    @FXML
    private Button createQuizLink;
    @FXML
    private Button logoutLink;
    @FXML
    private Button settingsLink;

    @FXML
    private TextField promptField;

    @FXML
    public void onLogoutButtonClick(javafx.event.ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) logoutLink.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 360, 520);
        stage.setScene(scene);
    }

    @FXML
    public void onSettingsButtonClick(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) settingsLink.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("settings-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        stage.setScene(scene);

    }

    public void onCreateQuizClick(ActionEvent actionEvent) throws SQLException {
        String userprompt = promptField.getText();
        //createQuizLink.setDisable(true);
        createQuizLink.setStyle("-fx-background-color: green;");
        //quizGeneration quizGeneration = new quizGeneration(userprompt);
        AsynchronousQuery asynchronousQuery = new AsynchronousQuery(userprompt);
        asynchronousQuery.createQuiz();




    }

    @FXML
    public void onBackToMainPageButtonClick(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) logoutLink.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        stage.setScene(scene);
    }
}
