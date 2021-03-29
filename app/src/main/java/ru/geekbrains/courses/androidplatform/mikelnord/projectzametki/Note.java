package ru.geekbrains.courses.androidplatform.mikelnord.projectzametki;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.UUID;

public class Note implements Parcelable {
    private UUID mId;
    private String mTitle;
    private String mDescription;
    private Date mDate;

    public Note() {
        mId=UUID.randomUUID();
        mDate=new Date();
    }

    protected Note(Parcel in) {
        mId= (UUID) in.readSerializable();
        mTitle = in.readString();
        mDescription = in.readString();
        mDate=new Date(in.readLong());
    }

    public Note(UUID id, String title, String description, Date date) {
        mId = id;
        mTitle = title;
        mDescription = description;
        mDate = date;
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeSerializable(mId);
        parcel.writeString(mTitle);
        parcel.writeString(mDescription);
        parcel.writeLong(mDate.getTime());
    }
}
