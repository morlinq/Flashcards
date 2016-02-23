package com.example.ewa_para.fiszki;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MenuActivity extends AppCompatActivity {
    public static final int FLASHCARD_ENTRY_REQUEST_CODE = 1;
    private Boolean isDialogDisplayed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        if (savedInstanceState != null) {
            isDialogDisplayed = savedInstanceState.getBoolean("isDialogDisplayed");
            if (isDialogDisplayed) {
                showAlertDialog();
            }
        }
        else {
            isDialogDisplayed = false;
        }
    }

    public void learn(View view) {
        Intent intent = new Intent(this, LearnActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FLASHCARD_ENTRY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                String originalWord = data.getStringExtra("originalWord");
                String translation = data.getStringExtra("translation");
                new SQLiteHelper(this).addNewFlashcard(originalWord, translation, this);
            }
        }
    }

    public void addFlashcard(View view) {
        Intent intent = new Intent(this, AddFlashcardActivity.class);
        startActivityForResult(intent, FLASHCARD_ENTRY_REQUEST_CODE);
    }

    public void seeAll(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void settings(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        showAlertDialog();
    }

    private void showAlertDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(this.getResources().getString(R.string.DialogMsgCloseApplication))
                .setCancelable(false)
                .setPositiveButton(this.getResources().getString(R.string.AlertDialogYes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
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
        outState.putBoolean("isDialogDisplayed", isDialogDisplayed);
    }
}
