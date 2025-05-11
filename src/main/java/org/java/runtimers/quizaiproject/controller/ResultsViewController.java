package org.java.runtimers.quizaiproject.controller;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.java.runtimers.quizaiproject.ContactModel.User;
import org.java.runtimers.quizaiproject.HelloApplication;
import org.java.runtimers.quizaiproject.QuizModel.Choice;
import org.java.runtimers.quizaiproject.QuizModel.IQuizDAO;
import org.java.runtimers.quizaiproject.QuizModel.QuizDAO;
import org.java.runtimers.quizaiproject.results.Results;
import org.java.runtimers.quizaiproject.results.login_id;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ResultsViewController {
    @FXML
    private Button homeLink;
    @FXML
    private Button logoutLink;
    @FXML
    private Button settingsLink;
    @FXML
    private TableView<Results> resultsTable;
    @FXML
    private TableColumn<Results, String> quizColumn;
    @FXML
    private TableColumn<Results, Integer> scoreColumn;

    public static login_id login; // import this class

    public static void setLogin(login_id id) {
        login = id;
    }

    private IQuizDAO quizDAO;

    public ResultsViewController() {
        quizDAO = new QuizDAO();
    }

    public ObservableList<Results> getResultsForUser(int userId) {
        ObservableList<Results> results = FXCollections.observableArrayList();

        try {
            List<Integer> quizIds = quizDAO.getQuizIdsForUser(userId);

            for (int quizId : quizIds) {
                List<Integer> attemptIds = quizDAO.getAttemptIdsForQuiz(quizId);

                for (int attemptId : attemptIds) {
                    int score = quizDAO.getScoreForAttempt(attemptId);
                    String quizTitle = quizDAO.GetQuizTitleFromId(quizId);
                    results.add(new Results(score, quizTitle, attemptId));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return results;
    }
    @FXML
    public void initialize() throws SQLException {
        quizColumn.prefWidthProperty().bind(resultsTable.widthProperty().multiply(0.75));
        scoreColumn.prefWidthProperty().bind(resultsTable.widthProperty().multiply(0.25));

        ObservableList<Results> results = getResultsForUser(login.getLogin_id());

        resultsTable.setItems(results);

        //quizColumn.setCellValueFactory(new PropertyValueFactory<Results, String>("quiz_name"));
        //scoreColumn.setCellValueFactory(new PropertyValueFactory<Results, String>("score"));
        quizColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getQuiz_name()));
        scoreColumn.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getScore()).asObject()
        );

    }

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

    @FXML
    public void onBackToMainPageButtonClick(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) logoutLink.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        stage.setScene(scene);
    }
}
