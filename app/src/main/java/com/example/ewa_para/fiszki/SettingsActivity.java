package com.example.ewa_para.fiszki;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {
    private static final String PREFERENCES_NAME = "myPreferences";
    private static final String PREFERENCES_FLASHCARDS_NUMBER = "";
    private static final String PREFERENCES_APPLICATION_COLOR= "";

    private SharedPreferences preferences;

    private Integer flashcardsNumber;
    private SeekBar numberOfFlashcardsSB = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        preferences = getSharedPreferences(PREFERENCES_NAME, Activity.MODE_PRIVATE);
        restoreData();

        TextView numberOfFlashcardsTV = (TextView) findViewById(R.id.numberOfFlashcardsTV);
        numberOfFlashcardsTV.setText(this.getResources().getString(R.string.SettingsFlashcardsNumber) + " " + String.valueOf(flashcardsNumber));

        numberOfFlashcardsSB = (SeekBar) findViewById(R.id.numberOfFlashcardsSB);
        numberOfFlashcardsSB.setProgress(flashcardsNumber-5);

        numberOfFlashcardsSB.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                flashcardsNumber = progress + 5;
                TextView numberOfFlashcardsTV = (TextView) findViewById(R.id.numberOfFlashcardsTV);
                numberOfFlashcardsTV.setText(getApplication().getResources().getString(R.string.SettingsFlashcardsNumber) + " " + String.valueOf(flashcardsNumber));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    private void restoreData() {
        flashcardsNumber = Integer.valueOf(preferences.getString(PREFERENCES_FLASHCARDS_NUMBER, "5"));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
        finish();
    }

    public void onSave(View view) {
        SharedPreferences.Editor preferencesEditor = preferences.edit();
        preferencesEditor.putString(PREFERENCES_FLASHCARDS_NUMBER, String.valueOf(flashcardsNumber));
        preferencesEditor.commit();

        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
        finish();
    }
}
