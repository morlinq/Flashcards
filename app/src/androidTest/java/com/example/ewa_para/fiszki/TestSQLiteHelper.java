package com.example.ewa_para.fiszki;

import android.content.ContentValues;
import android.content.Context;

import java.util.ArrayList;



    public class TestSQLiteHelper extends SQLiteHelper {

        public TestSQLiteHelper(Context context){
            super(context);
        }

        public void addNewFlashcardForTest(String originalWord, String translation) {
            ContentValues newRow = new ContentValues();
            newRow.put(ORIGINAL_WORD_COLUMN, originalWord);
            newRow.put(TRANSLATION_COLUMN, translation);
            newRow.put(VALUE, 0);
            getWritableDatabase().insert(TABLE_NAME, null, newRow);
        }

        public int getNumberOfRows(){
            ArrayList<Flashcard> list = getAllFlashcardsFromDatabase();
            return list.size();
        }

        public void clearDatabase(){
            getWritableDatabase().execSQL("delete from " + TABLE_NAME);
        }
    }