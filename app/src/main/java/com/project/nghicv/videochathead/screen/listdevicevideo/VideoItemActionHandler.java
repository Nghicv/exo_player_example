package com.project.nghicv.videochathead.screen.listdevicevideo;

import com.project.nghicv.videochathead.model.Video;

public class VideoItemActionHandler {
    ListDeviceVideoContract.Presenter mPresenter;

    public VideoItemActionHandler(ListDeviceVideoContract.Presenter presenter) {
        mPresenter = presenter;
    }

    public void videoClicked(Video video) {
        mPresenter.playVideo(video);
    }
}
