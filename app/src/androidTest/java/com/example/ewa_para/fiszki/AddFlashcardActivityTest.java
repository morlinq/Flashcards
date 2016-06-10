package com.example.ewa_para.fiszki;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.Button;
import android.widget.EditText;
import java.util.ArrayList;

public class AddFlashcardActivityTest extends ActivityUnitTestCase {

    TestSQLiteHelper testSQLiteHelper;
    private AddFlashcardActivity addFlashcardActivity;
    private EditText originalEditText;
    private EditText translationEditText;
    private Button addButton;

    public AddFlashcardActivityTest() {
        super(AddFlashcardActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Intent intent =
                new Intent(getInstrumentation().getTargetContext(),
                        AddFlashcardActivity.class);
        startActivity(intent, null, null);
        addFlashcardActivity =
                (AddFlashcardActivity) getActivity();
        originalEditText =
                (EditText) addFlashcardActivity.findViewById(R.id.originalWord);
        translationEditText =
                (EditText) addFlashcardActivity.findViewById(R.id.translation);
        addButton =
                (Button)addFlashcardActivity.findViewById(R.id.addButton);
        testSQLiteHelper =
                new TestSQLiteHelper(addFlashcardActivity.getBaseContext());

        testSQLiteHelper.clearDatabase();
        simulateAddingFlashcard("test1", "trans1");
        simulateAddingFlashcard("test2", "trans2");
    }

    @SmallTest // 1
    public void testShouldAddNewFlashcard() {

        String originalInput = "correctExample-original";
        String translationInput = "correctExample-translation";
        simulateAddingFlashcard(originalInput, translationInput);
        int counter = numberOfFlashcards(originalInput, translationInput);
        assertEquals("Nie udalo sie dodac poprawnej fiszki", 1, counter);
    }

    @SmallTest // 2
    public void testShouldNotAddFlashcardWithEmptyOriginal() {

        String originalInput = "";
        String translationInput = "emptyExample-Translation";
        simulateAddingFlashcard(originalInput, translationInput);
        int counter = numberOfFlashcards(originalInput, translationInput);
        assertEquals("Udalo sie dodac niepoprawna fiszke", 0, counter);
    }

    @SmallTest // 3
    public void testShouldNotAddFlashcardWithEmptyTranslation() {

        String originalInput = "emptyExample-Original";
        String translationInput = "";
        simulateAddingFlashcard(originalInput, translationInput);
        int counter = numberOfFlashcards(originalInput, translationInput);
        assertEquals("Udalo sie dodac niepoprawna fiszke", 0, counter);
    }

    @SmallTest // 4
    public void testShouldNotAddFlashcardWithVeryLongWord() {

        String originalInput = "000originalExampleoriginalExampleoriginalExampleoriginalExampleoriginalExampleoriginalExampleoriginalExampleoriginalExampleoriginalExampleoriginalExampleoriginalExampleoriginalExampleoriginalExampleoriginalExampleoriginalExampleoriginalExampleoriginalExampleoriginalExampleoriginalExampleoriginalExample300";
        String translationInput = "longExample-Translation";
        simulateAddingFlashcard(originalInput, translationInput);
        int counter = numberOfFlashcards(originalInput, translationInput);
        assertEquals("Udalo sie dodac niepoprawna fiszke", 0, counter);
    }

    @SmallTest // 5
    public void testShouldNotAddFlashcardWithStrangeSymbols() {

        String originalInput = "strangeExample\n123  ?,.+=@~";
        String translationInput = "      ";
        simulateAddingFlashcard(originalInput, translationInput);
        int counter = numberOfFlashcards(originalInput, translationInput);
        assertEquals("Udalo sie dodac niepoprawna fiszke", 0, counter);
    }

    @SmallTest // 6
    public void testShouldNotAddTwoSameFlashcards() {

        String originalInput = "sameExample-Original";
        String translationInput = "sameExample-Translation";
        simulateAddingFlashcard(originalInput, translationInput);
        simulateAddingFlashcard(originalInput, translationInput);
        int counter = numberOfFlashcards(originalInput, translationInput);
        assertEquals("Dodano dwa razy jednakowa fiszke", 1, counter);
    }

    @SmallTest // 7
    public void testShouldAddFlashcardsWithSameOriginal() {

        String originalInput = "sameExample-Original";
        String translationInput1 = "sameExample-Translation1";
        String translationInput2 = "sameExample-Translation2";
        simulateAddingFlashcard(originalInput, translationInput1);
        simulateAddingFlashcard(originalInput, translationInput2);
        int counter1 = numberOfFlashcards(originalInput, translationInput1);
        int counter2 = numberOfFlashcards(originalInput, translationInput2);
        assertEquals("Blad przy dodawaniu poprawnych fiszek", 1, counter1);
        assertEquals("Blad przy dodawaniu poprawnych fiszek", 1, counter2);
    }

    @SmallTest // 8
    public void testShouldAddFlashcardsWithSameTranslation() {

        String originalInput1 = "sameExample-Original1";
        String originalInput2 = "sameExample-Original2";
        String translationInput = "sameExample-Translation";
        simulateAddingFlashcard(originalInput1, translationInput);
        simulateAddingFlashcard(originalInput2, translationInput);
        int counter1 = numberOfFlashcards(originalInput1, translationInput);
        int counter2 = numberOfFlashcards(originalInput2, translationInput);
        assertEquals("Blad przy dodawaniu poprawnych fiszek", 1, counter1);
        assertEquals("Blad przy dodawaniu poprawnych fiszek", 1, counter2);
    }


    // End of Tests //


    private void simulateAddingFlashcard(String original, String translation) {
        originalEditText.setText(original);
        translationEditText.setText(translation);
        addButton.performClick();
    }

    private int numberOfFlashcards(String original, String translation){

        ArrayList<Flashcard> list = testSQLiteHelper.getAllFlashcardsFromDatabase();
        int count = 0;
        for (Flashcard flashcard: list) {
            if (flashcard.getOriginalWord().equals(original)
                    && flashcard.getTranslation().equals(translation)){
                ++count;
            }
        }
        return count;
    }
}