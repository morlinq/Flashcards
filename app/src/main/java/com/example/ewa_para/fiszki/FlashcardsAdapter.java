package com.example.ewa_para.fiszki;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class FlashcardsAdapter extends BaseAdapter {
    private ArrayList<Flashcard> list = new ArrayList<Flashcard>();

    public FlashcardsAdapter() {
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int index, View view, ViewGroup parent) {
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            view = inflater.inflate(R.layout.flashcards_list_item, parent, false);
        }

        Flashcard flashcard = list.get(index);
        TextView originalWordTextView = (TextView) view.findViewById(R.id.original_word_view);
        TextView translationTextView = (TextView) view.findViewById(R.id.translation_view);

        originalWordTextView.setText(flashcard.getOriginalWord());
        translationTextView.setText(flashcard.getTranslation());

        return view;
    }

    public void addNewFlashcard(Flashcard flashcard) {
        list.add(flashcard);
    }

    public void deleteFlashcard() {

    }
}
