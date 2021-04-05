package ru.geekbrains.courses.androidplatform.mikelnord.projectzametki;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ListNote {
    private static ListNote sListNote;
    private List<Note> mNotes;
    private static final String NOTES_COLLECTION = "notes";
    private FirebaseFirestore store = FirebaseFirestore.getInstance();
    private final CollectionReference collection = store.collection(NOTES_COLLECTION);
    private String TAG = "NotesSourceFirebaseImpl";

    public static ListNote get() {
        if (sListNote == null) {
            sListNote = new ListNote();
        }
        return sListNote;
    }

    private ListNote() {
        mNotes = new ArrayList<>();
    }


    public List<Note> getNotes() {
        return mNotes;
    }

    public Note getNote(UUID id) {
        for (Note note : mNotes) {
            if (note.getId().equals(id)) {
                return note;
            }
        }
        return null;
    }

    public Note getElement(int i) {
        return mNotes.get(i);
    }

    public void deleteNoteData(int position) {
        collection.document(String.valueOf(mNotes.get(position).getId())).delete();
        mNotes.remove(position);
    }

    public void updateNote(int position, Note note) {
        collection.document(String.valueOf(mNotes.get(position).getId())).delete();
        collection.document(String.valueOf(note.getId())).set(NoteDataMapping.toDocument(note));
        mNotes.set(position, note);
    }

    public void addNoteData(Note note) {
        mNotes.add(note);
        collection.document(String.valueOf(note.getId())).set(NoteDataMapping.toDocument(note));
    }

    public void clearNoteList() {
        for (Note note : mNotes) {
            collection.document(String.valueOf(note.getId())).delete();
        }
        mNotes.clear();
    }

    public int getSize() {
        return mNotes.size();
    }

    public CollectionReference getCollection() {
        return collection;
    }
}
