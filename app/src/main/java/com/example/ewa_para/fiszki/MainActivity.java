package com.example.ewa_para.fiszki;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final int FLASHCARD_ENTRY_REQUEST_CODE = 1;
    FlashcardsAdapter adapter = new FlashcardsAdapter();
    private SQLiteHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
        dbHelper = new SQLiteHelper(this);

        final ArrayList<Flashcard> list = dbHelper.getAllFlashcardsFromDatabase(this);

        for (Flashcard flashcard: list) {
            adapter.addNewFlashcard(flashcard);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                View view2 = (View) listView.getSelectedItem();
                TextView textView = (TextView) findViewById(R.id.original_word_view);
                String originalWord = textView.getText().toString();

                // opcja edytuj/usuń
                // może "umiem"?
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_add_flashcard) {
            Intent intent = new Intent(this, AddFlashcardActivity.class);
            startActivityForResult(intent, FLASHCARD_ENTRY_REQUEST_CODE);
            return true;
        }
        if (id == R.id.action_learn) {
            // dialog z pytaniem ile słówek chcę się uczyć
            Intent intent = new Intent(this, LearnActivity.class);
            startActivityForResult(intent, FLASHCARD_ENTRY_REQUEST_CODE);
            return true;
        }

        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FLASHCARD_ENTRY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                String originalWord = data.getStringExtra("originalWord");
                String translation = data.getStringExtra("translation");
                Flashcard flashcard = new Flashcard(originalWord, translation, 0, this);
                dbHelper.addNewFlashcard(originalWord, translation);
                adapter.addNewFlashcard(flashcard);
                adapter.notifyDataSetChanged();
            }
        }
    }
}
