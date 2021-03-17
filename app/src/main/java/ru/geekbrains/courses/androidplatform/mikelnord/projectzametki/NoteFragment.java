package ru.geekbrains.courses.androidplatform.mikelnord.projectzametki;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.UUID;

public class NoteFragment extends Fragment {

    private static final String ARG_INDEX = "index";
    private ListNote mListNote;
    private UUID id;
    private EditText mTitleField;
    private EditText mDescriptionField;

    public NoteFragment() {
    }

    public static NoteFragment newInstance(UUID id) {
        NoteFragment fragment = new NoteFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_INDEX, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = (UUID) getArguments().getSerializable(ARG_INDEX);
        mListNote = ListNote.get();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_note, container, false);
        Note note = mListNote.getNote(id);
        if (note != null) {
            mTitleField = v.findViewById(R.id.note_title);
            mTitleField.setText(note.getTitle());
            mDescriptionField = v.findViewById(R.id.note_description);
            mDescriptionField.setText(note.getDescription());
        }
        return v;
    }
}