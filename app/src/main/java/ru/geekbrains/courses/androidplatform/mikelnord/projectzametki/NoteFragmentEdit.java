package ru.geekbrains.courses.androidplatform.mikelnord.projectzametki;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class NoteFragmentEdit extends Fragment {

    private static final String ARG_NOTE_DATA = "Param_NoteData";

    private Note note;      // Данные по карточке
    private Publisher publisher;

    private TextInputEditText title;
    private TextInputEditText description;
    private UUID mUUID;
    private DatePicker datePicker;

    // Для редактирования данных
    public static NoteFragmentEdit newInstance(Note note) {
        NoteFragmentEdit fragment = new NoteFragmentEdit();
        Bundle args = new Bundle();
        args.putParcelable(ARG_NOTE_DATA, note);
        fragment.setArguments(args);
        return fragment;
    }

    // Для добавления новых данных
    public static NoteFragmentEdit newInstance() {
        NoteFragmentEdit fragment = new NoteFragmentEdit();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            note = getArguments().getParcelable(ARG_NOTE_DATA);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        MainActivity activity = (MainActivity) context;
        publisher = activity.getPublisher();
    }

    @Override
    public void onDetach() {
        publisher = null;
        super.onDetach();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_edit, container, false);
        initView(view);
        // если cardData пустая, то это добавление
        if (note != null) {
            populateView();
        }
        return view;
    }

    // Здесь соберём данные из views
    @Override
    public void onStop() {
        super.onStop();
        note = collectCardData();
    }

    // Здесь передадим данные в паблишер
    @Override
    public void onDestroy() {
        super.onDestroy();
        publisher.notifySingle(note);
    }

    private Note collectCardData() {
        String title = this.title.getText().toString();
        String description = this.description.getText().toString();
        Date date = getDateFromDatePicker();
        mUUID = UUID.randomUUID();
        return new Note(mUUID, title, description, date);
    }

    // Получение даты из DatePicker
    private Date getDateFromDatePicker() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, this.datePicker.getYear());
        cal.set(Calendar.MONTH, this.datePicker.getMonth());
        cal.set(Calendar.DAY_OF_MONTH, this.datePicker.getDayOfMonth());
        return cal.getTime();
    }

    private void initView(View view) {
        title = view.findViewById(R.id.inputTitle);
        description = view.findViewById(R.id.inputDescription);
        datePicker = view.findViewById(R.id.inputDate);
    }

    private void populateView() {
        title.setText(note.getTitle());
        description.setText(note.getDescription());
        initDatePicker(note.getDate());
    }

    // Установка даты в DatePicker
    private void initDatePicker(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        this.datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), null);
    }

}
