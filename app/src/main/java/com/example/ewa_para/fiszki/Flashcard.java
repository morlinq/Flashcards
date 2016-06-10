package com.example.ewa_para.fiszki;


public class Flashcard {
    private String originalWord;
    private String translation;
    private Integer value;
    private Long dbID;

    public Flashcard(String originalWord, String translation, Integer value, Long dbID) {
        this.originalWord = originalWord;
        this.translation = translation;
        this.value = value;
        this.dbID = dbID;
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

    public void setValue(Integer value) {
        this.value = value;
    }

    public Long getDbID() {
        return this.dbID;
    }

    public void addValue(Integer value) {
        this.value += value;
    }
}
