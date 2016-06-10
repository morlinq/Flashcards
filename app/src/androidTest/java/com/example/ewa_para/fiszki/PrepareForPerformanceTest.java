package com.example.ewa_para.fiszki;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.SmallTest;
import android.util.Log;
import java.util.Random;


public class PrepareForPerformanceTest extends ActivityUnitTestCase {

    AddFlashcardActivity addFlashcardActivity;
    Random random = new Random();

    public PrepareForPerformanceTest(){
        super(AddFlashcardActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Intent intent = new Intent(getInstrumentation().getTargetContext(),
                AddFlashcardActivity.class);
        startActivity(intent, null, null);
        addFlashcardActivity = (AddFlashcardActivity) getActivity();
    }

    @SmallTest
    public void testShouldAddLargeNumberOfInputs(){
        int numberOfInputs = 10;
        TestSQLiteHelper testSQLiteHelper =
                new TestSQLiteHelper(addFlashcardActivity.getBaseContext());
        long startTime = System.currentTimeMillis();
        for(int i=0; i < numberOfInputs; ++i){
            testSQLiteHelper
                    .addNewFlashcardForTest(String.valueOf(random.nextInt()),
                            String.valueOf(random.nextInt()));
        }
        long endTime = System.currentTimeMillis();

        Log.d("TimeInSeconds", ""+(endTime-startTime)/1000);
        Log.d("NumberOfRows", ""+testSQLiteHelper.getNumberOfRows());
    }
}
