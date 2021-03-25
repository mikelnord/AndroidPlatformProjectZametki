package ru.geekbrains.courses.androidplatform.mikelnord.projectzametki;

import java.util.Date;
import java.util.UUID;

public class Note {
    private UUID mId;
    private String mTitle;
    private String mDescription;
    private Date mDate;

    public Note() {
        mId=UUID.randomUUID();
        mDate=new Date();
    }

    public UUID getId() {
        return mId;
    }

    public void setId(UUID id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }
}
