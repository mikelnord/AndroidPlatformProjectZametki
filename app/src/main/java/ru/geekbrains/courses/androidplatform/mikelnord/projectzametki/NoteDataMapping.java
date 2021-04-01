package ru.geekbrains.courses.androidplatform.mikelnord.projectzametki;

import com.google.firebase.Timestamp;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class NoteDataMapping {
    public static class Fields{
        public final static String DATE = "mDate";
        public final static String TITLE = "mTitle";
        public final static String DESCRIPTION = "mDescription";
    }

    public static Note toNoteData(String id, Map<String, Object> doc) {
        Timestamp timeStamp = (Timestamp)doc.get(Fields.DATE);
        Note answer=new Note(UUID.fromString(id),(String) doc.get(Fields.TITLE),(String) doc.get(Fields.DESCRIPTION),timeStamp.toDate());
        return answer;
    }

    public static Map<String, Object> toDocument(Note note){
        Map<String, Object> answer = new HashMap<>();
        answer.put(Fields.TITLE, note.getTitle());
        answer.put(Fields.DESCRIPTION, note.getDescription());
        answer.put(Fields.DATE, note.getDate());
        return answer;
    }

}
