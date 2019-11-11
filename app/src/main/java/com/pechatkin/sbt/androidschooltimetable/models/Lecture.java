package com.pechatkin.sbt.androidschooltimetable.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

public class Lecture implements Parcelable {

    private static final int LECTURES_PER_WEEK = 3;

    private final int mNumber;
    private final String mDate;
    private final String mTheme;
    private final String mLecturer;
    private final String mDescription;
    private final int weekIndex;

    @JsonCreator
    public Lecture(@JsonProperty("number") int number, @JsonProperty("date") @NonNull String date, @JsonProperty("theme") @NonNull String theme, @JsonProperty("lector") @NonNull String lecturer, @JsonProperty("subtopics") @NonNull List<String> description) {
        mNumber = number;
        mDate = date;
        mTheme = theme;
        mLecturer = lecturer;
        StringBuilder buffer = new StringBuilder();
        for (String s : description) {
            buffer.append(s);
            buffer.append(". ");
        }
        mDescription = buffer.toString();
        weekIndex = (mNumber - 1) / LECTURES_PER_WEEK;
    }

    protected Lecture(Parcel in) {
        mNumber = in.readInt();
        mDate = in.readString();
        mTheme = in.readString();
        mLecturer = in.readString();
        mDescription = in.readString();
        weekIndex = in.readInt();
    }

    public static final Creator<Lecture> CREATOR = new Creator<Lecture>() {
        @Override
        public Lecture createFromParcel(Parcel in) {
            return new Lecture(in);
        }

        @Override
        public Lecture[] newArray(int size) {
            return new Lecture[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lecture lecture = (Lecture) o;
        return mNumber == lecture.mNumber &&
                weekIndex == lecture.weekIndex &&
                mDate.equals(lecture.mDate) &&
                mTheme.equals(lecture.mTheme) &&
                mLecturer.equals(lecture.mLecturer) &&
                mDescription.equals(lecture.mDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mNumber, mDate, mTheme, mLecturer, mDescription, weekIndex);
    }

    public int getNumber() {
        return mNumber;
    }

    public String getDate() {
        return mDate;
    }

    public String getTheme() {
        return mTheme;
    }

    public String getLecturer() {
        return mLecturer;
    }

    public String geDescription() { return mDescription; }

    public int getWeekIndex() {
        return weekIndex;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mNumber);
        parcel.writeString(mDate);
        parcel.writeString(mTheme);
        parcel.writeString(mLecturer);
        parcel.writeString(mDescription);
        parcel.writeInt(weekIndex);
    }
}
