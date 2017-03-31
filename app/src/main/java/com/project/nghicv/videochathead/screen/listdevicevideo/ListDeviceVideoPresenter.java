package com.project.nghicv.videochathead.screen.listdevicevideo;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import com.project.nghicv.videochathead.R;
import com.project.nghicv.videochathead.VideoPlayerApp;
import com.project.nghicv.videochathead.common.utils.DateUtil;
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

    @Override
    public void playVideo(Video video) {
        VideoPlayerApp.getInstance().getVideoPlayerService().setupDatasource(video.getFilePath());
    }

    private List<Video> loadVideosFromDevice() {
        List<Video> videos = new ArrayList<>();
        String[] thumbColumns = { MediaStore.Video.Thumbnails.DATA };
        String[] mediaColumns = {
                MediaStore.Video.Media._ID, MediaStore.Video.Media.DATA,
                MediaStore.Video.Media.TITLE, MediaStore.Video.Media.MIME_TYPE,
                MediaStore.Video.Media.DURATION, MediaStore.Video.Media.DATE_ADDED
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
                                thumbColumns, MediaStore.Video.Thumbnails.VIDEO_ID + "=" + id, null,
                                null);
                String thumbPath = "";
                if (thumbCursor.moveToFirst()) {
                    thumbPath = thumbCursor.getString(thumbCursor.getColumnIndex(thumbColumns[0]));
                }

                String filePath = cursor.getString(cursor.getColumnIndexOrThrow(mediaColumns[1]));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(mediaColumns[2]));
                String mimeType = cursor.getString(cursor.getColumnIndexOrThrow(mediaColumns[3]));
                long duration = cursor.getLong(cursor.getColumnIndexOrThrow(mediaColumns[4]));
                String durationString = formatTime(duration);
                String date = DateUtil.formatDate(cursor.getLong(cursor.getColumnIndexOrThrow
                        (mediaColumns[5])));
                videos.add(new Video(id, filePath, title, thumbPath, mimeType, durationString, date));
            } while (cursor.moveToNext());
        }

        return videos;
    }

    private String formatTime(long time) {
        int timeSecond = (int) (time / 1000);

        if (timeSecond < 60) {
            return mView.getContextFragment()
                    .getString(R.string.format_time_minute_video, 0, timeSecond);
        }

        if (timeSecond > 60 && timeSecond <= 3600) {
            int minute = timeSecond / 60;
            int second = timeSecond % 60;
            return mView.getContextFragment()
                    .getString(R.string.format_time_minute_video, minute, second);
        }

        int hour = timeSecond / 3600;
        int minute = (timeSecond % 3600) / 60;
        int second = (timeSecond % 3600) % 60;
        return mView.getContextFragment()
                .getString(R.string.format_time_hour_video, hour, minute, second);
    }
}
