package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import java.util.HashSet;

public class NoteActivity extends AppCompatActivity {

    EditText editTextNote;
    int noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

//        editTextTitle = (EditText) findViewById(R.id.editTextTitle);
        editTextNote =  (EditText) findViewById(R.id.editTextNote);

        Intent intent = getIntent();
        noteId = intent.getIntExtra("noteId", -1);

        if (noteId != -1) {
//            editTextTitle.setText(MainActivity.noteTitles.get(noteId));
            editTextNote.setText(MainActivity.notes.get(noteId));
        }
        else {
            MainActivity.notes.add("");
            noteId = MainActivity.notes.size() - 1;
        }

        editTextNote.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                MainActivity.notes.set(noteId, String.valueOf(s));
                MainActivity.arrayAdapter.notifyDataSetChanged();

                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences
                        ("package com.example.notes", Context.MODE_PRIVATE);
                HashSet<String> notesHashSet = new HashSet<>(MainActivity.notes);
                sharedPreferences.edit().putStringSet("notes", notesHashSet).apply();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}