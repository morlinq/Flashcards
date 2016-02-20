package com.example.ewa_para.fiszki;

import android.content.Context;

/**
 * Created by ewa_para on 2016-02-16.
 */
public class Flashcard {
    private String originalWord;
    private String translation;
    private Integer value;
    private Integer dbID;
    private Context context;

    public Flashcard(String originalWord, String translation, Integer value, Context context) {
        this.originalWord = originalWord;
        this.translation = translation;
        this.value = value;
    }

    public String getOriginalWord() {
        return originalWord;
    }

    public String getTranslation() {
        return translation;
    }

    public Integer getValue() {
        return value;
    }

    public Integer getDbID() {
        return this.dbID;
    }

    public void setDbID (Integer id) {
        dbID = id;
    }

    public void editOriginalWord (String originalWord) {
        this.originalWord = originalWord;
        new SQLiteHelper(this.context).updateFlashcard(this);
    }

    public void editTranslation (String translation) {
        this.originalWord = translation;
        new SQLiteHelper(this.context).updateFlashcard(this);
    }

    public void addValue(Integer value) {
        this.value += value;
    }

}
