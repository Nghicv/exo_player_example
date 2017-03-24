package com.project.nghicv.videochathead.model;

public class Video {

    private int mId;
    private String mFilePath;
    private String mTitle;
    private String mMimeType;
    private String mThumbPath;
    private String mDuration;
    private String mDate;

    public Video(int id, String filePath, String title, String mimeType, String thumbPath,
            String duration, String date) {
        mId = id;
        mFilePath = filePath;
        mTitle = title;
        mMimeType = mimeType;
        mThumbPath = thumbPath;
        mDuration = duration;
        mDate = date;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getFilePath() {
        return mFilePath;
    }

    public void setFilePath(String filePath) {
        mFilePath = filePath;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getMimeType() {
        return mMimeType;
    }

    public void setMimeType(String mimeType) {
        mMimeType = mimeType;
    }

    public String getThumbPath() {
        return mThumbPath;
    }

    public void setThumbPath(String thumbPath) {
        mThumbPath = thumbPath;
    }

    public String getDuration() {
        return mDuration;
    }

    public void setDuration(String duration) {
        mDuration = duration;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }
}
