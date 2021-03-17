package ru.geekbrains.courses.androidplatform.mikelnord.projectzametki;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import java.util.UUID;

public class ListNoteFragment extends Fragment {
    private ListNote mNoteList;
    private boolean isLandscape;
    private UUID mId;
    private static final String CURRENT_ID = "CurrentId";

    public ListNoteFragment() {
        // Required empty public constructor
    }

    public static ListNoteFragment newInstance(String param1, String param2) {
        ListNoteFragment fragment = new ListNoteFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNoteList = ListNote.get();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_note, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initList(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        if (savedInstanceState != null) {
            // Восстановление текущей позиции.
            mId = (UUID) savedInstanceState.getSerializable(CURRENT_ID);

        } else {
            if (isLandscape) {
                showLandNote(mNoteList.getElement(0).getId());
            }
        }
    }

    private void initList(View view) {
        LinearLayout layoutView = (LinearLayout) view;
        for (int i = 0; i < mNoteList.getSize(); i++) {
            TextView tv = new TextView(getContext());
            Note note = mNoteList.getElement(i);
            tv.setText(note.getTitle());
            tv.setTextSize(20);
            layoutView.addView(tv);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mId = note.getId();
                    showNote(mId);
                }
            });
        }
    }

    private void showNote(UUID index) {
        if (isLandscape) {
            showLandNote(index);
        } else {
            showPortNote(index);
        }
    }

    private void showLandNote(UUID index) {
        NoteFragment detail = NoteFragment.newInstance(index);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.note, detail);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();

    }

    private void showPortNote(UUID index) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), ActivityNote.class);
        intent.putExtra(ActivityNote.EXTRA_ID, index);
        startActivity(intent);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putSerializable(CURRENT_ID, mId);
        super.onSaveInstanceState(outState);

    }
}