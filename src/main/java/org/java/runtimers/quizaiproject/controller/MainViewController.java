package org.java.runtimers.quizaiproject.controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.java.runtimers.quizaiproject.HelloApplication;
import org.java.runtimers.quizaiproject.QuizModel.*;

import java.io.IOException;
import java.sql.SQLException;


public class MainViewController {

    @FXML
    private Button takequizbutton;

    private IQuizDAO quizDAO;

    public MainViewController() {
        quizDAO = new QuizDAO();
    }

    @FXML
    public void onTakeQuizButtonClick() throws SQLException, IOException {
        Stage stage = (Stage) takequizbutton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("quiz-test-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        stage.setScene(scene);

    }

    @FXML
    public void onLogoutButtonClick(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) takequizbutton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 360, 520);
        stage.setScene(scene);
    }

    @FXML
    public void onSettingsButtonClick(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) takequizbutton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("settings-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        stage.setScene(scene);

    }

    @FXML
    public void onResultsButtonClick(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) takequizbutton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("results-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        stage.setScene(scene);

    }

    @FXML
    public void onCreateButtonClick(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) takequizbutton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("create-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        stage.setScene(scene);

    }

    @FXML
    public void onLearnTopicButtonClick(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) takequizbutton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("learn-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        stage.setScene(scene);

    }

}
