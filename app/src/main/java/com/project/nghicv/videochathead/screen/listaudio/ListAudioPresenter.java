package com.project.nghicv.videochathead.screen.listaudio;

public class ListAudioPresenter implements ListAudioContract.Presenter {

    ListAudioContract.View mView;

    public ListAudioPresenter(ListAudioContract.View view) {
        mView = view;
    }

    @Override
    public void loadAudios() {

    }
}
