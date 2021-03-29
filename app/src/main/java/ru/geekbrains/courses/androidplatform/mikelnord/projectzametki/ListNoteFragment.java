package ru.geekbrains.courses.androidplatform.mikelnord.projectzametki;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.UUID;

public class ListNoteFragment extends Fragment {
    private ListNote mNoteList;
    private boolean isLandscape;
    private UUID mId;
    private RecyclerView mRecyclerView;
    private NoteAdapter mAdapter;
    private Navigation navigation;
    private Publisher publisher;
    private boolean moveToLastPosition;
    private static final int MY_DEFAULT_DURATION = 1000;


    public ListNoteFragment() {
        // Required empty public constructor
    }

    public static ListNoteFragment newInstance() {
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
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity activity = (MainActivity) context;
        navigation = activity.getNavigation();
        publisher = activity.getPublisher();
    }

    @Override
    public void onDetach() {
        navigation = null;
        publisher = null;
        super.onDetach();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_list_note, container, false);
        mRecyclerView = view.findViewById(R.id.note_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return view;
    }


    private void updateUI() {
        ListNote listNote = ListNote.get();
        List<Note> notes = listNote.getNotes();
        mAdapter = new NoteAdapter(notes);
        mRecyclerView.setAdapter(mAdapter);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.separator, null));
        mRecyclerView.addItemDecoration(itemDecoration);
        DefaultItemAnimator animator = new DefaultItemAnimator();
        animator.setAddDuration(MY_DEFAULT_DURATION);
        animator.setRemoveDuration(MY_DEFAULT_DURATION);
        mRecyclerView.setItemAnimator(animator);
        if (moveToLastPosition) {
            mRecyclerView.smoothScrollToPosition(mNoteList.getSize() - 1);
            moveToLastPosition = false;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragment_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            navigation.addFragment(NoteFragmentEdit.newInstance(), true, getResources().getConfiguration().orientation);
            publisher.subscribe(new Observer() {
                @Override
                public void updateNoteData(Note note) {
                    mNoteList.addNoteData(note);
                    mAdapter.notifyItemInserted(mNoteList.getSize() - 1);
                    moveToLastPosition = true;
                }
            });
            return true;
        }
        if (item.getItemId() == R.id.action_clear) {
            mNoteList.clearNoteList();
            mAdapter.notifyDataSetChanged();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
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
        NoteFragment noteFragment = NoteFragment.newInstance(index);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, noteFragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(null)
                .commit();
    }


    private class NoteHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener {
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private Note mNote;

        public NoteHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_note, parent, false));
            itemView.setOnClickListener(this);
            mTitleTextView = itemView.findViewById(R.id.note_title);
            mDateTextView = itemView.findViewById(R.id.note_date);
            itemView.setOnCreateContextMenuListener(this);
        }

        public void bind(Note note) {
            mNote = note;
            mTitleTextView.setText(mNote.getTitle());
            mDateTextView.setText(mNote.getDate().toString());
        }

        @Override
        public void onClick(View view) {
            mId = mNote.getId();
            showNote(mId);
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.setHeaderTitle("Select action");
            contextMenu.add("Delete").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    mNoteList.deleteNoteData(mNote);
                    mAdapter.notifyItemRemoved(getLayoutPosition());
                    return true;
                }
            });
            contextMenu.add("Edit").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    navigation.addFragment(NoteFragmentEdit.newInstance(mNote), true, getResources().getConfiguration().orientation);
                    publisher.subscribe(new Observer() {
                        @Override
                        public void updateNoteData(Note note) {
                            int position=getLayoutPosition();
                            mNoteList.updateNote(position,note);
                            mAdapter.notifyItemChanged(position);
                        }
                    });
                    return true;
                }
            });
        }
    }

    private class NoteAdapter extends RecyclerView.Adapter<NoteHolder> {
        private List<Note> mNotes;

        public NoteAdapter(List<Note> notes) {
            mNotes = notes;
        }

        @NonNull
        @Override
        public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new NoteHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
            Note note = mNotes.get(position);
            holder.bind(note);
        }

        @Override
        public int getItemCount() {
            return mNotes.size();
        }
    }
}