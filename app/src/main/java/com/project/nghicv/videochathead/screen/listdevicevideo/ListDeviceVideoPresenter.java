package com.project.nghicv.videochathead.screen.listdevicevideo;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import com.project.nghicv.videochathead.R;
import com.project.nghicv.videochathead.VideoPlayerApp;
import com.project.nghicv.videochathead.common.utils.DateUtil;
import com.project.nghicv.videochathead.model.Video;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;

public class ListDeviceVideoPresenter implements ListDeviceVideoContract.Presenter {

    private ListDeviceVideoContract.View mView;
    private CompositeDisposable mDisposable = new CompositeDisposable();

    public ListDeviceVideoPresenter(ListDeviceVideoContract.View view) {
        mView = view;
    }

    @Override
    public void loadVideos() {
        mDisposable.clear();
        Disposable disposable = Observable.defer(() -> {
            return Observable.fromArray(loadVideosFromDevice());
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(videos -> {
                    mView.showVideos(videos);
                }, error -> {
                    mView.showEmptyView();
                });
        mDisposable.add(disposable);
    }

    @Override
    public void playVideo(Video video) {
        VideoPlayerApp.getInstance().getVideoPlayerService().setupDatasource(video.getFilePath());
    }

    private List<Video> loadVideosFromDevice() {
        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        List<Video> videos = new ArrayList<>();
        String[] thumbColumns = { MediaStore.Video.Thumbnails.DATA };
        String[] mediaColumns = {
                MediaStore.Video.Media._ID, MediaStore.Video.Media.DATA,
                MediaStore.Video.Media.TITLE, MediaStore.Video.Media.MIME_TYPE,
                MediaStore.Video.Media.DURATION, MediaStore.Video.Media.DATE_ADDED
        };

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
                String date = DateUtil.formatDate(
                        cursor.getLong(cursor.getColumnIndexOrThrow(mediaColumns[5])));
                videos.add(
                        new Video(id, filePath, title, thumbPath, mimeType, durationString, date));
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

    @Override
    public void subscriber() {

    }

    @Override
    public void unSubscriber() {
        mDisposable.clear();
    }
}
