package com.example.ewa_para.fiszki;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by ewa_para on 2016-02-20.
 */
public class SeeFlashcardActivity extends AppCompatActivity {
    private EditText originalWordET;
    private EditText translationET;
    private Button button;
    private Long dbID;
    private Integer value;
    private Boolean editTextIsEnabled = false;
    private Boolean isDialogDisabled = false;
    private String editBtnText = "edit";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            editTextIsEnabled = savedInstanceState.getBoolean("editTextIsEnabled");
            editBtnText = savedInstanceState.getString("editBtnText");
            isDialogDisabled = savedInstanceState.getBoolean("isDialogDisabled");

            if (isDialogDisabled) {
                showAlertDialog();
            }
        }

        setContentView(R.layout.see_flashcard);
        Intent intent = getIntent();
        String originalWord = intent.getStringExtra("originalWord");
        String translation = intent.getStringExtra("translation");
        dbID = Long.valueOf(intent.getStringExtra("dbID"));
        value = Integer.valueOf(intent.getStringExtra("value"));
        button = (Button) findViewById(R.id.editFlashcard);
        if (editBtnText.equals("edit")){
            button.setText(R.string.SeeFlashcardBtnEdit);
        }
        else {
            button.setText(R.string.SeeFlashcardBtnSave);
        }
        originalWordET = (EditText) findViewById(R.id.seeOriginalWorld);
        translationET = (EditText) findViewById(R.id.seeTranslation);
        originalWordET.setText(originalWord);
        translationET.setText(translation);
        originalWordET.setEnabled(editTextIsEnabled);
        translationET.setEnabled(editTextIsEnabled);
    }

    public void editFlashcard(View view) {
        Button button = (Button) findViewById(R.id.editFlashcard);
        String text = button.getText().toString();

        if (text.equals(getResources().getString(R.string.SeeFlashcardBtnEdit))) {
            originalWordET.setEnabled(true);
            translationET.setEnabled(true);
            editTextIsEnabled = true;
            button.setText(R.string.SeeFlashcardBtnSave);
            editBtnText = "save";
        }
        else {
            originalWordET.setEnabled(false);
            translationET.setEnabled(false);
            editTextIsEnabled = false;
            button.setText(R.string.SeeFlashcardBtnEdit);
            editBtnText = "edit";
            new SQLiteHelper(getApplicationContext()).updateFlashcard(
                    new Flashcard(originalWordET.getText().toString(),
                            translationET.getText().toString(), value, dbID, getApplicationContext()));
            showToast(this.getResources().getString(R.string.ToastMsgFlashcardEdited));
        }
    }

    public void onDelete(View view) {
        showAlertDialog();
    }

    private void showAlertDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SeeFlashcardActivity.this);
        alertDialogBuilder.setMessage(this.getResources().getString(R.string.DialogMsgCloseApplication))
                .setCancelable(false)
                .setPositiveButton(this.getResources().getString(R.string.AlertDialogYes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        new SQLiteHelper(getApplicationContext()).deleteFlashcard(dbID);
                        Intent intent = new Intent(SeeFlashcardActivity.this, MainActivity.class);
                        finish();
                        startActivity(intent);
                        isDialogDisabled = false;
                        showToast(getApplication().getResources().getString(R.string.ToastMsgFlashcardDeleted));
                    }
                })
                .setNegativeButton(this.getResources().getString(R.string.AlertDialogNo), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                        isDialogDisabled = false;
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
        isDialogDisabled = true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        finish();
        startActivity(intent);
    }

    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("editTextIsEnabled", editTextIsEnabled);
        outState.putString("editBtnText", editBtnText);
        outState.putBoolean("isDialogDisabled", isDialogDisabled);
    }
}
