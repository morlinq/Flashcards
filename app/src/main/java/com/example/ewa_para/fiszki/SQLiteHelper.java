package com.example.ewa_para.fiszki;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;

/**
 * Created by ewa_para on 2016-02-16.
 */
public class SQLiteHelper extends SQLiteOpenHelper implements BaseColumns {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Flashcards.db";
    private static final String ID_COLUMN = BaseColumns._ID;

    private static final String TABLE_NAME = "flashcards";
    private static final String ORIGINAL_WORD_COLUMN = "original_word";
    private static final String TRANSLATION_COLUMN = "translation";
    private static final String VALUE = "value";


    private String CREATE_TABLE_SQL_QUERY = "create table " + TABLE_NAME
            + " (" + ID_COLUMN + " integer primary key autoincrement, "
            + ORIGINAL_WORD_COLUMN + " varchar(255), "
            + TRANSLATION_COLUMN + " varchar(255), "
            + VALUE + " integer);";

    private SQLiteDatabase database;

    private String DROP_TABLE_SQL_QUERY = "drop table if exists" + TABLE_NAME + ";";

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SQL_QUERY);
        database = db;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersio) {
        db.execSQL(DROP_TABLE_SQL_QUERY);
        db.execSQL(CREATE_TABLE_SQL_QUERY);
    }

    public void addNewFlashcard(String originalWord, String translation) {
        ContentValues newRow = new ContentValues();
        newRow.put(ORIGINAL_WORD_COLUMN, originalWord);
        newRow.put(TRANSLATION_COLUMN, translation);
        newRow.put(VALUE, 0);

        getWritableDatabase().insert(TABLE_NAME, null, newRow);
    }

    public ArrayList<Flashcard> getAllFlashcardsFromDatabase (Context context) {
        ArrayList<Flashcard> flashcardsList = new ArrayList<>();
        String originalWord;
        String translation;
        Integer value;

        Cursor cursor =
                getWritableDatabase().query(TABLE_NAME, null, null, null, null, null, null);

        if (cursor.moveToFirst())
            while (cursor.isAfterLast() == false){
                originalWord = cursor.getString(cursor.getColumnIndex(ORIGINAL_WORD_COLUMN));
                translation = cursor.getString(cursor.getColumnIndex(TRANSLATION_COLUMN));
                value = cursor.getInt(cursor.getColumnIndex(VALUE));
                flashcardsList.add(new Flashcard(originalWord, translation, value, context));
                cursor.moveToNext();
            }

        return flashcardsList;
    }

    public void updateValues(ArrayList<Flashcard> list, Integer number) {
        for (int i = 0; i < number; i++) {
            //przejdź po wszystkich i zaktualizuj
        }
    }

    public void updateFlashcard(Flashcard flashcard) {
        //zaktualizuj wszystko oprócz ID
    }

}
