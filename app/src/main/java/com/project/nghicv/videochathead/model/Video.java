package com.project.nghicv.videochathead.model;

public class Video {

    private int mId;
    private String mFilePath;
    private String mTitle;
    private String mMimeType;
    private String mThumbPath;

    public Video(int id, String filePath, String title, String mimeType, String thumbPath) {
        mId = id;
        mFilePath = filePath;
        mTitle = title;
        mMimeType = mimeType;
        mThumbPath = thumbPath;
    }
}
