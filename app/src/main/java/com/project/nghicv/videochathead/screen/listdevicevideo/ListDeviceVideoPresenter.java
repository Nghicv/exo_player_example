package com.project.nghicv.videochathead.screen.listdevicevideo;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import com.project.nghicv.videochathead.VideoPlayerApp;
import com.project.nghicv.videochathead.model.Video;
import java.util.ArrayList;
import java.util.List;

public class ListDeviceVideoPresenter implements ListDeviceVideoContract.Presenter {

    private ListDeviceVideoContract.View mView;

    public ListDeviceVideoPresenter(ListDeviceVideoContract.View view) {
        mView = view;
    }

    @Override
    public void loadVideos() {
        List<Video> videos = loadVideosFromDevice();
        if (videos == null || videos.size() <= 0) {
            mView.showEmptyView();
            return;
        }
        mView.showVideos(videos);
    }

    private List<Video> loadVideosFromDevice() {
        List<Video> videos = new ArrayList<>();
        String[] thumbColumns = { MediaStore.Video.Thumbnails.DATA };
        String[] mediaColumns = {
                MediaStore.Video.Media._ID, MediaStore.Video.Media.DATA,
                MediaStore.Video.Media.TITLE, MediaStore.Video.Media.MIME_TYPE, MediaStore.Video
                .Media.DURATION
        };
        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;

        ContentResolver contentResolver = VideoPlayerApp.getInstance().getContentResolver();
        Cursor cursor = contentResolver.query(uri, mediaColumns, null, null, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {

                int id = cursor.getInt(cursor.getColumnIndex(mediaColumns[0]));

                Cursor thumbCursor =
                        contentResolver.query(MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI,
                                thumbColumns, MediaStore.Video.Thumbnails.VIDEO_ID + "=" + id,
                                null, null);
                String thumbPath = "";
                if (thumbCursor.moveToFirst()) {
                    thumbPath = thumbCursor.getString(thumbCursor.getColumnIndex(thumbColumns[0]));
                }

                String filePath = cursor.getString(cursor.getColumnIndexOrThrow(mediaColumns[1]));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(mediaColumns[2]));
                String mimeType = cursor.getString(cursor.getColumnIndexOrThrow(mediaColumns[3]));
                videos.add(new Video(id, filePath, title, thumbPath, mimeType));
            } while (cursor.moveToNext());
        }

        return videos;
    }
}
