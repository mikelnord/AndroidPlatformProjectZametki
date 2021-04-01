package ru.geekbrains.courses.androidplatform.mikelnord.projectzametki;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.UUID;

public class NoteFragment extends Fragment {

    private static final String ARG_INDEX = "index";
    private static final String UUID_INDEX = "uuid_index";
    private ListNote mListNote;
    private UUID id;
    private TextView mTitleField;
    private TextView mDescriptionField;
    private TextView mDateField;

    public NoteFragment() {
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(UUID_INDEX, id);
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
//        setRetainInstance(true);
        id = (UUID) getArguments().getSerializable(ARG_INDEX);
        mListNote = ListNote.get();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            id = (UUID) savedInstanceState.getSerializable(UUID_INDEX);
        }
        View v = inflater.inflate(R.layout.fragment_note, container, false);
        setHasOptionsMenu(true);
        Note note = mListNote.getNote(id);
        if (note != null) {
            mTitleField = v.findViewById(R.id.note_title);
            mTitleField.setText(note.getTitle());
            mDescriptionField = v.findViewById(R.id.note_description);
            mDescriptionField.setText(note.getDescription());
            mDateField = v.findViewById(R.id.note_date);
            mDateField.setText(note.getDate().toString());
        }
        return v;
    }
}