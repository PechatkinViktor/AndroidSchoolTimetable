package com.pechatkin.sbt.androidschooltimetable.dataProvider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pechatkin.sbt.androidschooltimetable.models.Lecture;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class AndroidSchoolTimetableProvider {

    private static final String DATE_FORMAT_PATTERN = "dd.MM.yyyy";
    private static final String URL = "http://landsovet.ru/learning_program.json";

    private List<Lecture> mLectures;

    public List<Lecture> parseLectures() {
        try {
            URLConnection urlConnection = new URL(URL).openConnection();
            try(InputStream is = urlConnection.getInputStream()) {
                ObjectMapper mapper = new ObjectMapper();
                Lecture[] lectures = mapper.readValue(is, Lecture[].class);
                mLectures = Arrays.asList(lectures);
                return new ArrayList<>(mLectures);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Lecture> provideLectures() {
        return mLectures == null ? null : new ArrayList<>(mLectures);
    }

    public List<String> providerLectorers() {
        Set<String> lectorersSet = new HashSet<>();
        for (Lecture lecture : mLectures) {
            lectorersSet.add(lecture.getLecturer());
        }
        return new ArrayList<>(lectorersSet);
    }

    public List<Lecture> filterBy(String lectorName) {
        List<Lecture> result = new ArrayList<>();
        for (Lecture lecture : mLectures) {
            if (lecture.getLecturer().equals(lectorName)) {
                result.add(lecture);
            }
        }
        return result;
    }

    public Lecture getCurrentLecture (Date date) {
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT_PATTERN, Locale.getDefault());
            for (Lecture lecture : mLectures) {
            try {
                Date lectureDate = format.parse(lecture.getDate());
                if (lectureDate != null && lectureDate.after(date)) {
                    return lecture;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return mLectures.get(mLectures.size() - 1);
    }
}
