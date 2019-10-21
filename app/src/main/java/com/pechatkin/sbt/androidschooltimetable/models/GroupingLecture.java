package com.pechatkin.sbt.androidschooltimetable.models;


import com.pechatkin.sbt.androidschooltimetable.R;

public enum GroupingLecture {
    UNGROUPED(R.string.ungrouped),
    GROUP_BY_WEEK(R.string.group_by_week);

    private final int mTitleStringResourceId;

    GroupingLecture(int titleStringResourceId) {
        mTitleStringResourceId = titleStringResourceId;
    }

    public int getTitleStringResourceId() {
        return mTitleStringResourceId;
    }
}
