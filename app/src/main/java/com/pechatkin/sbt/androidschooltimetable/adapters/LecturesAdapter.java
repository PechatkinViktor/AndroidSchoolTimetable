package com.pechatkin.sbt.androidschooltimetable.adapters;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.pechatkin.sbt.androidschooltimetable.R;
import com.pechatkin.sbt.androidschooltimetable.models.GroupingLecture;
import com.pechatkin.sbt.androidschooltimetable.models.Lecture;

import java.util.ArrayList;
import java.util.List;

public class LecturesAdapter extends RecyclerView.Adapter<LecturesAdapter.BaseViewHolder> {

    private static final int ITEM_LECTURE = 0;
    private static final int ITEM_WEEK = 1;

    private final Resources mResources;
    private List<Object> mAdapterItems;
    private List<Lecture> mLectures;
    private GroupingLecture mSortLecture = GroupingLecture.UNGROUPED;

    public LecturesAdapter(Resources resources) {
        mResources = resources;
    }

    @NonNull
    @Override
    public LecturesAdapter.BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case ITEM_LECTURE: {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_lecture, parent, false);
                return new LectureHolder(view);
            }
            case ITEM_WEEK: {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_week, parent, false);
                return new WeekHolder(view);
            }
            default:
                throw new IllegalArgumentException("ViewType " + viewType + " is not supported");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull LecturesAdapter.BaseViewHolder holder, int position) {
        Object item = mAdapterItems.get(position);
        switch (getItemViewType(position)) {
            case ITEM_LECTURE:
                ((LectureHolder) holder).bindView((Lecture) item);
                break;
            case ITEM_WEEK:
                ((WeekHolder) holder).bindView((String) item);
                break;
            default:
                throw new RuntimeException("The following item is not supported by adapter: " + item);
        }
    }

    @Override
    public int getItemCount() {
        return mAdapterItems == null ? 0 : mAdapterItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        Object item = mAdapterItems.get(position);
        if (item instanceof Lecture) {
            return ITEM_LECTURE;
        } else if (item instanceof String) {
            return ITEM_WEEK;
        } else {
            throw new RuntimeException("The following item is not supported by adapter: " + item);
        }
    }

    public void setLectures(@Nullable List<Lecture> lectures) {
        if (lectures == null) {
            mLectures = new ArrayList<>();
            mAdapterItems = new ArrayList<>();
        } else {
            mLectures = new ArrayList<>(lectures);
            switch (mSortLecture) {
                case GROUP_BY_WEEK:
                    groupLecturesByWeek(lectures);
                    break;
                case UNGROUPED:
                default:
                    mAdapterItems = new ArrayList<Object>(lectures);
            }
        }
        notifyDataSetChanged();
    }

    public void setSortLecture(@NonNull GroupingLecture sortLecture) {
        mSortLecture = sortLecture;
        setLectures(mLectures);
    }

    public int getPositionOf(@NonNull Lecture lecture)
    {
        return mAdapterItems.indexOf(lecture);
    }

    static abstract class BaseViewHolder extends RecyclerView.ViewHolder {
        BaseViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    private void groupLecturesByWeek(@NonNull List<Lecture> lectures) {
        mAdapterItems = new ArrayList<>();
        int weekIndex = -1;
        for (Lecture lecture : lectures) {
            if (lecture.getWeekIndex() > weekIndex) {
                weekIndex = lecture.getWeekIndex();
                mAdapterItems.add(mResources.getString(R.string.week_number, weekIndex + 1));
            }
            mAdapterItems.add(lecture);
        }
    }

    private static class LectureHolder extends BaseViewHolder {
        private final TextView mNumber;
        private final TextView mDate;
        private final TextView mTheme;
        private final TextView mDescription;
        private final TextView mLectorer;

        private LectureHolder(@NonNull View itemView) {
            super(itemView);
            mNumber = itemView.findViewById(R.id.number);
            mDate = itemView.findViewById(R.id.date);
            mTheme = itemView.findViewById(R.id.theme);
            mDescription = itemView.findViewById(R.id.description);
            mLectorer = itemView.findViewById(R.id.lector);
        }

        private void bindView(Lecture lecture) {
            mNumber.setText(String.valueOf(lecture.getNumber()));
            mDate.setText(lecture.getDate());
            mTheme.setText(lecture.getTheme());
            mDescription.setText(lecture.geDescription());
            mLectorer.setText(lecture.getLecturer());
        }
    }

    private static class WeekHolder extends BaseViewHolder {
        private final TextView mWeek;

        private WeekHolder(@NonNull View itemView) {
            super(itemView);
            mWeek = itemView.findViewById(R.id.week);
        }

        private void bindView(String week) {
            mWeek.setText(week);
        }
    }
}
