package org.java.runtimers.quizaiproject.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label; // Import Label
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.java.runtimers.quizaiproject.HelloApplication;
import org.java.runtimers.quizaiproject.ContactModel.*;

import java.io.IOException;
import java.sql.Connection;
import java.util.*;

public class SignupController {

    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private Label signupErrorLabel; // Added Label
    @FXML
    private Button signupButton;

    private IContactDAO contactDAO;

    public SignupController() {
        contactDAO = new SqliteContactDAO();
    }


    @FXML
    protected void onSignupButtonClick() throws IOException {
        if (Signup()) {
            Stage stage = (Stage) signupButton.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 360, 520);
            stage.setScene(scene);
        }
    }

    //TODO: put all helper functions into the contact model

    private boolean Signup() {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        signupErrorLabel.setVisible(false); //hide label by default

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            signupErrorLabel.setText("All fields must be filled!");
            signupErrorLabel.setVisible(true);
            return false;
        } else if (!password.equals(confirmPassword)) {
            signupErrorLabel.setText("Passwords do not match!");
            signupErrorLabel.setVisible(true);
            return false;
        } else if (contactDAO.isUserExist(firstName, lastName, email)) {
            signupErrorLabel.setText("User already exists with this name or email!");
            signupErrorLabel.setVisible(true);
            return false;
        } else {
            User newUser = new User(null, null, null, null); // Create a user with null values
            newUser.setFirstName(firstName);
            newUser.setLastName(lastName);
            try {
                newUser.setEmail(email);
                newUser.setPassword(password);
                contactDAO.addContact(newUser);
                System.out.println("Signup successful!");
                //Create default test:



                return true; //return true to switch to login page
            } catch (IllegalArgumentException e) {
                signupErrorLabel.setText(e.getMessage());
                signupErrorLabel.setVisible(true);
                return false;
            }
        }
    }

    @FXML
    protected void onHadAccountButtonClick() throws IOException {
        Stage stage = (Stage) signupButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        stage.setScene(scene);
    }
}