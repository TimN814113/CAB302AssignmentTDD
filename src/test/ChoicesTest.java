import org.java.runtimers.quizaiproject.QuizModel.Choice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ChoicesTest {

    Choice choice;

    @BeforeEach
    public void setup() {
        choice = new Choice(1, 2, 3, "Choice Text Example", true);
    }

    @Test
    public void testGetChoiceId() {
        assertEquals(1, choice.getChoiceId());
    }

    @Test
    public void testSetChoiceId() {
        choice.setChoiceId(10);
        assertEquals(10, choice.choiceId);
    }

    @Test
    public void testGetQuestionId() {
        assertEquals(2, choice.getQuestionId());
    }

    @Test
    public void testSetQuestionId() {
        choice.setQuestionId(20);
        assertEquals(20, choice.questionId);
    }

    @Test
    public void testGetChoiceNumber() {
        assertEquals(3, choice.getChoiceNumber());
    }

    @Test
    public void testSetChoiceNumber() {
        choice.setChoiceNumber(30);
        assertEquals(30, choice.choiceNumber);
    }

    @Test
    public void testGetChoiceText() {
        assertEquals("Choice Text Example", choice.getChoiceText());
    }

    @Test
    public void testSetChoiceText() {
        choice.setChoiceText("Updated Choice Text");
        assertEquals("Updated Choice Text", choice.choiceText);
    }

    @Test
    public void testIsCorrect() {
        assertTrue(choice.isCorrect());
    }
}
