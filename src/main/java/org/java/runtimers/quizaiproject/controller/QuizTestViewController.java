package org.java.runtimers.quizaiproject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.java.runtimers.quizaiproject.HelloApplication;
import org.java.runtimers.quizaiproject.QuizModel.*;
import org.java.runtimers.quizaiproject.results.login_id;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;



public class QuizTestViewController {

    //Question Navitgation buttons
    @FXML
    private Button ButtonQuestion1Link;
    @FXML
    private Button ButtonQuestion2Link;
    @FXML
    private Button ButtonQuestion3Link;
    @FXML
    private Button ButtonQuestion4Link;
    @FXML
    private Button ButtonQuestion5Link;
    @FXML
    private Button ButtonQuestion6Link;
    @FXML
    private Button ButtonQuestion7Link;
    @FXML
    private Button ButtonQuestion8Link;
    @FXML
    private Button ButtonQuestion9Link;
    @FXML
    private Button ButtonQuestion10Link;

    List<Button> navbuttonGroup;

    //Question numbering and text
    @FXML
    private Label LabelQuestionNumber;
    @FXML
    private Label LabelQuestionText;

    //Question response buttons
    @FXML
    private ToggleGroup toggleGroupAnswers;

    @FXML
    private RadioButton RadioButtonAnswer1;
    @FXML
    private RadioButton RadioButtonAnswer2;
    @FXML
    private RadioButton RadioButtonAnswer3;
    @FXML
    private RadioButton RadioButtonAnswer4;


    //Submit response
    @FXML
    private Button SubmitAnswer;

    //settings and logout

    @FXML
    private Button logoutLink;

    @FXML
    private Button settingsLink;


    private IQuizDAO quizDAO;

    public QuizTestViewController() {
        quizDAO = new QuizDAO();
    }


    //Need to make sure user has access to quiz first
    public static int activeQuizId;
    public static void setActiveQuizId(int id) {
            activeQuizId = id;
    }

    public int activeQuestion = 1;

    List<Question> questions;
    private int currentQuestionIndex = 0;
    private Map<Integer, List<Choice>> choicesMap = new HashMap<>();
    // This map stores the randomized choices for each question
    private Map<Integer, List<Choice>> shuffledChoicesMap = new HashMap<>();

    public int submittedCount = 0;

    //private final List<Boolean> correctResponses = new ArrayList<>();

    //may need to change n=10 to count of questions in test
    private List<Boolean> userResponses = new ArrayList<>(Collections.nCopies(10, null)); // true/false/null
    private int currentAttemptId = -1; // Will store attempt_id after creation


    @FXML
    public void initialize() throws SQLException {
        navbuttonGroup = List.of(
                ButtonQuestion1Link,
                ButtonQuestion2Link,
                ButtonQuestion3Link,
                ButtonQuestion4Link,
                ButtonQuestion5Link,
                ButtonQuestion6Link,
                ButtonQuestion7Link,
                ButtonQuestion8Link,
                ButtonQuestion9Link,
                ButtonQuestion10Link
        );
        //Default
        if (activeQuizId < 1){
            System.out.println(activeQuizId);
            setActiveQuizId(1);
        }
        System.out.println("initalized");
        //testing with quiz 1 on startup

        questions = quizDAO.getQuestionsForQuiz(activeQuizId);

        //for each question in the quiz create question node with 4 choices
        for (Question q : questions) {
            List<Choice> choices = quizDAO.getChoicesForQuestion(q.questionId);
            choicesMap.put(q.questionNumber, choices);
        }

        showQuestion(activeQuestion);


        /* Manual test for quiz 1 question 1
        Question question1 = questions.get(0);

        LabelQuestionNumber.setText("Question " + question1.questionNumber);
        LabelQuestionText.setText(question1.questionText);

        List<Choice> question1Choices = quizDAO.getChoicesForQuestion(question1.questionId);
        Choice choice1 = question1Choices.get(0);

        RadioButtonAnswer1.setText(choice1.choiceText);

        Choice choice2 = question1Choices.get(1);

        RadioButtonAnswer2.setText(choice2.choiceText);
        */


        System.out.println(quizDAO.GetQuizTitleFromId(activeQuizId));
    }


    @FXML
    public void showQuestion(int questionNumber) throws SQLException {
        currentQuestionIndex = questionNumber - 1;
        if (questions == null || questions.size() < questionNumber) return;

        Question question = questions.get(currentQuestionIndex);
        List<Choice> originalChoices = choicesMap.get(questionNumber);

        LabelQuestionNumber.setText("Question " + questionNumber);
        LabelQuestionText.setText(question.questionText);

        if (originalChoices != null && originalChoices.size() >= 4) {
            // Make a new list and shuffle it
            List<Choice> shuffledChoices = new ArrayList<>(originalChoices);
            Collections.shuffle(shuffledChoices);

            // Save shuffled choices to use later when checking answers
            shuffledChoicesMap.put(questionNumber, shuffledChoices);

            // Set the radio button texts
            RadioButtonAnswer1.setText(shuffledChoices.get(0).choiceText);
            RadioButtonAnswer2.setText(shuffledChoices.get(1).choiceText);
            RadioButtonAnswer3.setText(shuffledChoices.get(2).choiceText);
            RadioButtonAnswer4.setText(shuffledChoices.get(3).choiceText);

            // Reset selection
            RadioButtonAnswer1.setSelected(false);
            RadioButtonAnswer2.setSelected(false);
            RadioButtonAnswer3.setSelected(false);
            RadioButtonAnswer4.setSelected(false);
        }
    }


    @FXML
    public void handleLinkButtonClick(javafx.event.ActionEvent actionEvent) throws SQLException {
        Button clickedButton = (Button) actionEvent.getSource();
        String id = clickedButton.getId(); // e.g., "btn7"

        // Extract digits using regex
        String numberStr = id.replaceAll("\\D", ""); // removes non-digits
        int number = Integer.parseInt(numberStr);    // works for any digit count

        activeQuestion = number;
        System.out.println(number);
        showQuestion(number);
    }

    @FXML
    public void onSubmitAnswerClick() throws SQLException {
        Toggle activeToggle = toggleGroupAnswers.getSelectedToggle();
        if (activeToggle != null) {
            RadioButton selectedRadio = (RadioButton) activeToggle;


            List<Choice> shuffledChoices = shuffledChoicesMap.get(activeQuestion);

            String selectedRadioId = selectedRadio.getId();
            String numberStr = selectedRadioId.replaceAll("\\D", ""); // removes non-digits
            int number = Integer.parseInt(numberStr);

            // Get the correct choice from the shuffled list
            boolean isCorrect = shuffledChoices.get(number - 1).isCorrect;
            userResponses.set(currentQuestionIndex, isCorrect);
            System.out.println(isCorrect ? "Correct" : "Incorrect");


            //Disable question button when answered
            Button currentnavbutton = navbuttonGroup.get(currentQuestionIndex);
            currentnavbutton.setDisable(true);
            currentnavbutton.setStyle("-fx-background-color: gray;");

            //Trying to handle if the test is done or not
            activeQuestion++;
            submittedCount++;

            for (int i = 1; i < navbuttonGroup.size(); i++) {
                Button b = navbuttonGroup.get(i-1);
                if (!b.isDisabled()){
                    //System.out.println("Button " + i + " is active and will be new page");
                    activeQuestion = i;
                    break;
                }
            }
            if (submittedCount >= 10){
                System.out.println("test complete");
                // Insert new attempt into quiz_attempts
                int newAttemptId = quizDAO.createQuizAttempt(activeQuizId);
                this.currentAttemptId = newAttemptId;

                // Save results per question
                for (int i = 0; i < questions.size(); i++) {
                    int questionId = questions.get(i).questionId;
                    boolean correct = userResponses.get(i);
                    quizDAO.insertQuizResult(newAttemptId, questionId, correct);

                    //quizDAO.insertQuizResultNew(newAttemptId, questionId, correct, activeQuizId);
                }
            }
            System.out.println(activeQuestion);
            showQuestion(activeQuestion);

        }

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