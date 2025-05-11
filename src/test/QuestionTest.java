import org.java.runtimers.quizaiproject.QuizModel.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class QuestionTest {
    Question question;

    @BeforeEach
    public void setup(){
        question = new Question(1,2,3,"What is the capital of New Zealand?");

    }

    @Test
    public void testgetquestionid() {assertEquals(1, question.getQuestionId());}

    @Test
    public void testsetquestionid() {
        question.setQuestionId(1);
        assertEquals(1, question.questionId);

    }

    @Test
    public void testgetquizid() {assertEquals(2, question.getQuizId());}

    @Test
    public void testsetquizid() {
        question.setQuizId(2);
        assertEquals(2, question.quizId);

    }

    @Test
    public void testgetquestionnumber() {assertEquals(3, question.getQuestionNumber());}

    @Test
    public void testsetquestionnumber() {
        question.setQuestionNumber(3);
        assertEquals(3, question.questionNumber);

    }

    @Test
    public void testgetquestionText() {assertEquals("What is the capital of New Zealand?", question.getQuestionText());}

    @Test
    public void testsetquestiontest() {
        question.setQuestionText("What is the capital of New Zealand?");
        assertEquals("What is the capital of New Zealand?", question.questionText);

    }

}
