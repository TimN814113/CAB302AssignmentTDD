package org.java.runtimers.quizaiproject.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.java.runtimers.quizaiproject.HelloApplication;

import java.io.IOException;

public class ForgotPasswordController {

    @FXML
    private TextField emailField;


    @FXML
    private Button requestPasswordButton;

    @FXML
    protected void onRequestPasswordButtonClick() throws IOException {
        //need to handle correct and incorrect login still for now just redirects to main page
        Stage stage = (Stage) requestPasswordButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 360, 520);
        stage.setScene(scene);
        //Can probably make a custom function to load new pages somewhere to be reused multiple times
    }

    //Implementation of Signup Still Required
    private void requestPassword(){
        String email = emailField.getText();
    }

    @FXML
    protected void onReturnToLogin() throws IOException {
        Stage stage = (Stage) requestPasswordButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        stage.setScene(scene);
    }

}
