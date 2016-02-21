package com.example.ewa_para.fiszki;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

/**
 * Created by ewa_para on 2016-02-16.
 */
public class AddFlashcardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_flashcard);
    }

    public void onCancel(View view) {
        finish();
        new MainActivity();
    }

    public void onSave(View view) {
        EditText originalWord = (EditText) findViewById(R.id.originalWord);
        EditText translation = (EditText) findViewById(R.id.translation);
        Intent intent = getIntent();
        intent.putExtra("originalWord", originalWord.getText().toString());
        intent.putExtra("translation", translation.getText().toString());
        this.setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onBackPressed() { }
}
