package com.pechatkin.sbt.androidschooltimetable;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pechatkin.sbt.androidschooltimetable.adapters.LectorerSpinnerAdapter;
import com.pechatkin.sbt.androidschooltimetable.adapters.LecturesAdapter;
import com.pechatkin.sbt.androidschooltimetable.adapters.SortLectureSpinnerAdapter;
import com.pechatkin.sbt.androidschooltimetable.dataProvider.AndroidSchoolTimetableProvider;
import com.pechatkin.sbt.androidschooltimetable.models.GroupingLecture;
import com.pechatkin.sbt.androidschooltimetable.models.Lecture;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int POSITION_ALL = 0;

    private AndroidSchoolTimetableProvider mAndroidSchoolTimetableProvider = new AndroidSchoolTimetableProvider();
    private LecturesAdapter mLecturesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        parseLectures(savedInstanceState == null);
    }

    private void initRecyclerView(boolean isFirstCreate) {
        RecyclerView recyclerView = findViewById(R.id.school_timetable_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        mLecturesAdapter = new LecturesAdapter(getResources());
        mLecturesAdapter.setLectures(mAndroidSchoolTimetableProvider.provideLectures());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(mLecturesAdapter);
        if (isFirstCreate) {
            Lecture nextLecture = mAndroidSchoolTimetableProvider.getCurrentLecture(new Date());
            int positionOfNextLecture = mLecturesAdapter.getPositionOf(nextLecture);
            if (positionOfNextLecture != -1) {
                recyclerView.scrollToPosition(positionOfNextLecture);
            }
        }
    }

    private void parseLectures(boolean isFirstCreate) {
        new ParserLectures(this, isFirstCreate).execute();
    }

    private static class ParserLectures extends AsyncTask<Void, Void, List<Lecture>> {

        private final WeakReference<MainActivity> mMainActivityWeakReference ;
        private final AndroidSchoolTimetableProvider mAndroidSchoolTimetableProvider;
        private final boolean mIsFirstCreate;

        private ParserLectures(MainActivity mainActivity, boolean isFirstCreate) {
            mMainActivityWeakReference = new WeakReference<>(mainActivity);
            mAndroidSchoolTimetableProvider = mainActivity.mAndroidSchoolTimetableProvider;
            mIsFirstCreate = isFirstCreate;
        }

        @Override
        protected List<Lecture> doInBackground(Void... voids) {
            return mAndroidSchoolTimetableProvider.parseLectures();
        }

        @Override
        protected void onPostExecute(List<Lecture> lectures) {
            MainActivity activity = mMainActivityWeakReference.get();
            activity.initRecyclerView(mIsFirstCreate);
            activity.initLectorsSpinner();
            activity.initDisplayModeSpinner();
        }
    }

    private void initLectorsSpinner() {
        Spinner spinner = findViewById(R.id.teacher_filter);
        final List<String> spinnerItems = mAndroidSchoolTimetableProvider.providerLectorers();
        Collections.sort(spinnerItems);
        spinnerItems.add(POSITION_ALL, getResources().getString(R.string.all));
        spinner.setAdapter(new LectorerSpinnerAdapter(spinnerItems));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final List<Lecture> lectures = position == POSITION_ALL ? mAndroidSchoolTimetableProvider.provideLectures() : mAndroidSchoolTimetableProvider.filterBy(spinnerItems.get(position));
                mLecturesAdapter.setLectures(lectures);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void initDisplayModeSpinner() {
        Spinner spinner = findViewById(R.id.weekly_group);
        spinner.setAdapter(new SortLectureSpinnerAdapter());
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                GroupingLecture selectedSortLecture = GroupingLecture.values()[position];
                mLecturesAdapter.setSortLecture(selectedSortLecture);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}
