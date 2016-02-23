package com.example.ewa_para.fiszki;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by ewa_para on 2016-02-16.
 */
public class AddFlashcardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_flashcard);
    }

    public void onSave(View view) {
        EditText originalWord = (EditText) findViewById(R.id.originalWord);
        EditText translation = (EditText) findViewById(R.id.translation);
        if (originalWord.getText().toString().length() > 0 && translation.getText().toString().length() > 0){
            ArrayList<Flashcard> list = new SQLiteHelper(this).getAllFlashcardsFromDatabase(this);
            Boolean flag = false;
            for (Flashcard flashcard: list) {
                if (flashcard.getOriginalWord().equals(originalWord.getText().toString()) &&
                        flashcard.getTranslation().equals(translation.getText().toString())){
                    flag = true;
                    showToast(this.getResources().getString(R.string.ToastMsgFlashcardExist));
                }
            }
            if (!flag) {
                Intent intent = getIntent();
                intent.putExtra("originalWord", originalWord.getText().toString());
                intent.putExtra("translation", translation.getText().toString());
                this.setResult(RESULT_OK, intent);
                showToast(this.getResources().getString(R.string.ToastMsgFlashcardAdded));
                finish();
            }

        }
        else {
            showToast(this.getResources().getString(R.string.ToastMsgEmptyFields));
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MenuActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
