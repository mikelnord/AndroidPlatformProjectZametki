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
        for (int i = 0; i < 10; i++) {
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

    public Note getElement(int i){
        return mNotes.get(i);
    }

    public int getSize(){
        return mNotes.size();
    }
}
