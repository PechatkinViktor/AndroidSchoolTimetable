package com.pechatkin.sbt.androidschooltimetable.models;

import androidx.annotation.NonNull;

import java.util.Objects;

public class Lecture {

    private static final int LECTURES_PER_WEEK = 3;

    private final int mNumber;
    private final String mDate;
    private final String mTheme;
    private final String mLecturer;
    private final String mDescription;
    private final int weekIndex;

    public Lecture(int number, @NonNull String date, @NonNull String theme, @NonNull String lecturer, @NonNull String description) {
        mNumber = number;
        mDate = date;
        mTheme = theme;
        mLecturer = lecturer;
        mDescription = description;
        weekIndex = (mNumber - 1) / LECTURES_PER_WEEK;
    }

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


}
