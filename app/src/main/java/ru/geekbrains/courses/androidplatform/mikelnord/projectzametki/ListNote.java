package ru.geekbrains.courses.androidplatform.mikelnord.projectzametki;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ListNote {
    private static ListNote sListNote;
    private List<Note> mNotes;

    public static ListNote get() {
        if (sListNote == null) {
            sListNote = new ListNote();
        }
        return sListNote;
    }

    private ListNote() {
        mNotes = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            Note note = new Note();
            note.setTitle("Note #" + i);
            note.setDescription("Description " + i);
            mNotes.add(note);
        }
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

    public void deleteNoteData(Note note) {
        mNotes.remove(note);
    }

    public void updateNote(int position, Note note) {
        mNotes.set(position, note);
    }

    public void addNoteData(Note note) {
        mNotes.add(note);
    }

    public void clearNoteList() {
        mNotes.clear();
    }

    public int getSize() {
        return mNotes.size();
    }
}
