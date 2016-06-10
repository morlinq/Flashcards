package com.example.ewa_para.fiszki;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class LearnActivity extends AppCompatActivity{
    private static final String PREFERENCES_NAME = "myPreferences";
    private static final String PREFERENCES_FLASHCARDS_NUMBER = "";

    private SharedPreferences preferences;

    ArrayList<Flashcard> list;
    int allFlashcards;
    int position;
    float points;
    int learned;
    String layout;
    Boolean isDialogDisplayed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            retriveData(savedInstanceState);
        }
        else{

            setContentView(R.layout.activity_learn);
            layout = "activity_learn";
            position = 0;
            points = 0;
            learned = 0;
            isDialogDisplayed = false;

            list = new SQLiteHelper(this).getAllFlashcardsFromDatabase();
            Collections.sort(list, new Comparator<Flashcard>() {
                @Override
                public int compare(Flashcard flashcard, Flashcard flashcard2) {
                    return flashcard.getValue().compareTo(flashcard2.getValue());
                }
            });

            if(list.isEmpty()){
                showToast(this.getResources().getString(R.string.LearnEmptyDatabase));
                Intent intent = new Intent(this, MenuActivity.class);
                finish();
                startActivity(intent);
            }else {
                TextView originalWord = (TextView) this.findViewById(R.id.learnOriginalWord);
                originalWord.setText(list.get(position).getOriginalWord());

                preferences = getSharedPreferences(PREFERENCES_NAME, Activity.MODE_PRIVATE);
                setFlashcardsNumber(Integer.valueOf(preferences.getString(PREFERENCES_FLASHCARDS_NUMBER, "5")));

                TextView whichWordLearn = (TextView) this.findViewById(R.id.whichWordLearn);
                whichWordLearn.setText(String.valueOf(position + 1) + "/" + String.valueOf(allFlashcards));
            }
        }
    }

    private void retriveData(Bundle savedInstanceState) {
        position = savedInstanceState.getInt("position");
        points = savedInstanceState.getInt("points");
        learned = savedInstanceState.getInt("learned");
        layout = savedInstanceState.getString("layout");
        isDialogDisplayed = savedInstanceState.getBoolean("isDialogDisplayed");

        if(isDialogDisplayed) {
            showAlertDialog();
        }

        if (layout.equals("activity_translation")) {
            RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);
            ratingBar.setRating(savedInstanceState.getFloat("ratingBar"));
        }


        list = new SQLiteHelper(this).getAllFlashcardsFromDatabase();
        Collections.sort(list, new Comparator<Flashcard>() {
            @Override
            public int compare(Flashcard flashcard, Flashcard flashcard2) {
                return flashcard.getValue().compareTo(flashcard2.getValue());
            }
        });

        preferences = getSharedPreferences(PREFERENCES_NAME, Activity.MODE_PRIVATE);
        setFlashcardsNumber(Integer.valueOf(preferences.getString(PREFERENCES_FLASHCARDS_NUMBER, "5")));

        if(layout.equals("activity_learn")) {
            setContentView(R.layout.activity_learn);
            nextFlashcardLayoutConfiguration();
        }
        else if (layout.equals("activity_learn_translation")) {
            setContentView(R.layout.activity_learn_translation);
            checkTranslationLayoutConfiguration();
        }
        else if (layout.equals("activity_learn_end")) {
            setContentView(R.layout.activity_learn_end);
            endLayoutConfiguration();
        }
    }

    public void checkTranslation(View view) {
        setContentView(R.layout.activity_learn_translation);
        layout = "activity_learn_translation";
        checkTranslationLayoutConfiguration();
    }

    private void checkTranslationLayoutConfiguration() {
        TextView whichWordLearnTranslation = (TextView) this.findViewById(R.id.whichWordLearnTranslation);
        whichWordLearnTranslation.setText(String.valueOf(position + 1) + "/" + String.valueOf(allFlashcards));

        TextView originalWord = (TextView) this.findViewById(R.id.checkOriginalWorld);
        originalWord.setText(list.get(position).getOriginalWord());

        TextView translation = (TextView) this.findViewById(R.id.checkTranslation);
        translation.setText(list.get(position).getTranslation());
    }

    public void setFlashcardsNumber(int number) {
        if (number <= list.size()){
            this.allFlashcards = number;
        }
        else {
            this.allFlashcards = list.size();
        }
    }

    public void nextFlashcard(View view) {
        RatingBar ratingBar = (RatingBar) this.findViewById(R.id.ratingBar);
        float value = ratingBar.getRating();
        points += value;

        if(value > 0) {
            list.get(position).addValue((int) value);
            if (position < allFlashcards-1) {
                position++;
                setContentView(R.layout.activity_learn);
                layout = "activity_learn";
                nextFlashcardLayoutConfiguration();
            }
            else {
                new SQLiteHelper(this).updateValues(list, allFlashcards);
                setContentView(R.layout.activity_learn_end);
                layout = "activity_learn_end";
                endLayoutConfiguration();
            }
        }
    }

    private void nextFlashcardLayoutConfiguration() {
        TextView whichWordLearn = (TextView) this.findViewById(R.id.whichWordLearn);
        whichWordLearn.setText(String.valueOf(position + 1) + "/" + String.valueOf(allFlashcards));
        TextView originalWord = (TextView) this.findViewById(R.id.learnOriginalWord);
        originalWord.setText(list.get(position).getOriginalWord());
    }

    private void endLayoutConfiguration() {
        TextView numberOfFlashcards = (TextView) this.findViewById(R.id.numberOfFlashcards);
        numberOfFlashcards.setText(String.valueOf(allFlashcards));
        ProgressBar progressBar = (ProgressBar) this.findViewById(R.id.progress);
        progressBar.setMax(allFlashcards * 5);
        progressBar.setProgress((int) points);
        setComment((TextView) findViewById(R.id.comment), Float.valueOf((points / (allFlashcards * 5)) * 100));
    }

    private void setComment (TextView textView, Float progress) {
        String comment;

        if (progress < 20) {
            comment = this.getResources().getString(R.string.LearnEndComment020);
        }
        else if (progress < 40) {
            comment = this.getResources().getString(R.string.LearnEndComment2040);
        }
        else if (progress < 60) {
            comment = this.getResources().getString(R.string.LearnEndComment4060);
        }
        else if (progress < 80) {
            comment = this.getResources().getString(R.string.LearnEndComment6080);
        }
        else {
            comment = this.getResources().getString(R.string.LearnEndComment80100);
        }

        textView.setText(comment);
    }

    public void backToMenu(View view) {
        Intent intent = new Intent(this, MenuActivity.class);
        finish();
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if(!layout.equals("activity_learn_end")){
            showAlertDialog();
        }
    }

    private void showAlertDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(this.getResources().getString(R.string.DialogMsgLoseProgress))
                .setCancelable(false)
                .setPositiveButton(this.getResources().getString(R.string.AlertDialogYes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(LearnActivity.this, MenuActivity.class);
                        finish();
                        startActivity(intent);
                        isDialogDisplayed = false;
                    }
                })
                .setNegativeButton(this.getResources().getString(R.string.AlertDialogNo), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                        isDialogDisplayed = false;
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
        isDialogDisplayed = true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("position", position);
        outState.putFloat("points", points);
        outState.putInt("learned", learned);
        outState.putString("layout", layout);
        outState.putBoolean("isDialogDisplayed", isDialogDisplayed);
        if (layout.equals("activity_learn_translation")) {
            RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);
            outState.putFloat("ratingBar",ratingBar.getRating());
        }
    }

    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
