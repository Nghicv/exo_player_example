package com.project.nghicv.videochathead.screen.listaudio;

import com.project.nghicv.videochathead.base.BasePresenter;
import com.project.nghicv.videochathead.base.BaseView;
import com.project.nghicv.videochathead.model.Audio;
import java.util.List;

public interface ListAudioContract {

    interface Presenter extends BasePresenter {
        void loadAudios();
    }

    interface View extends BaseView<Presenter> {
        void showListAudio(List<Audio> audios);
    }
}
