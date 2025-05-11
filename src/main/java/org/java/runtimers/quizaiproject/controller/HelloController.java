package org.java.runtimers.quizaiproject.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.java.runtimers.quizaiproject.HelloApplication;
import org.java.runtimers.quizaiproject.ContactModel.*;
import org.java.runtimers.quizaiproject.results.login_id;

import java.io.IOException;

public class HelloController {
    /*@FXML
    private TextField usernameField;*/
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label loginErrorLabel;
    @FXML
    private Button loginButton;

    private SqliteContactDAO contactDAO = new SqliteContactDAO();


    @FXML
    protected void onLoginButtonClick() throws IOException {
        String email = emailField.getText();
        String password = passwordField.getText();
        String hashedEmail = User.Hash(email);
        String hashedPassword = User.Hash(password);

        if (contactDAO.authenticateUser(hashedEmail, hashedPassword)) {
            login_id login = new login_id(contactDAO.getUserId(hashedEmail, hashedPassword));
            ResultsViewController.setLogin(login);
            Stage stage = (Stage) loginButton.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
            stage.setScene(scene);
        } else {
            loginErrorLabel.setText("Invalid email or password. Please try again.");
            loginErrorLabel.setVisible(true); // Make the error label visible
        }
    }

    @FXML
    protected void onForgotPasswordButtonClick() throws IOException {

        Stage stage = (Stage) loginButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("forgotpassword-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 360, 520);
        stage.setScene(scene);
    }

    @FXML
    protected void onCreateAccountButtonClick() throws IOException {
        Stage stage = (Stage) loginButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("signup-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        stage.setScene(scene);
    }



}