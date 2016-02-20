package com.example.ewa_para.fiszki;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by ewa_para on 2016-02-16.
 */
public class LearnActivity extends AppCompatActivity{
    ArrayList<Flashcard> list;
    int allFlashcards=4;
    int position;
    int points;
    int learned;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);

        position = 0;
        points = 0;
        learned = 0;
        list = new SQLiteHelper(this).getAllFlashcardsFromDatabase(this);
        Collections.sort(list, new Comparator<Flashcard>() {
            @Override
            public int compare(Flashcard flashcard, Flashcard flashcard2) {
                return flashcard.getValue().compareTo(flashcard2.getValue());
            }
        });

        TextView originalWord = (TextView) this.findViewById(R.id.learnOriginalWord);
        originalWord.setText(list.get(position).getOriginalWord());
    }

    public void checkTranslation(View view) {
        setContentView(R.layout.activity_learn_translation);

        TextView originalWord = (TextView) this.findViewById(R.id.checkOriginalWorld);
        originalWord.setText(list.get(position).getOriginalWord());

        TextView translation = (TextView) this.findViewById(R.id.checkTranslation);
        translation.setText(list.get(position).getTranslation());
    }

    public void setNumberFlashcards(int number) {
        this.allFlashcards = number;
    }

    public void nextFlashcard(View view) {
        RatingBar ratingBar = (RatingBar) this.findViewById(R.id.ratingBar);
        float value = ratingBar.getRating();
        if(value > 0) {
            list.get(position).addValue((int)value);
            if (position < allFlashcards-1) {
                position++;
                setContentView(R.layout.activity_learn);
                TextView originalWord = (TextView) this.findViewById(R.id.learnOriginalWord);
                originalWord.setText(list.get(position).getOriginalWord());
            }
            else {
                new SQLiteHelper(this).updateValues(list,allFlashcards);
                setContentView(R.layout.activity_learn_end);
                TextView numberOfFlashcards = (TextView) this.findViewById(R.id.numberOfFlashcards);
                numberOfFlashcards.setText(String.valueOf(allFlashcards));
                ProgressBar progressBar = (ProgressBar) this.findViewById(R.id.progress);
                progressBar.setProgress((int)(points/allFlashcards));
            }
        }
    }
}
