package com.project.nghicv.videochathead.screen.listaudio;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.project.nghicv.videochathead.base.BaseFragment;
import com.project.nghicv.videochathead.databinding.FragmentListAudioBinding;
import com.project.nghicv.videochathead.model.Audio;
import java.util.List;

public class ListAudioFragment extends BaseFragment<ListAudioContract.Presenter> implements
        ListAudioContract.View{

    private FragmentListAudioBinding mBinding;

    public static ListAudioFragment getInstance() {
        return new ListAudioFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        mBinding = FragmentListAudioBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void setPresenter(ListAudioContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onCreatePresenter() {
        mPresenter = new ListAudioPresenter(this);
    }

    @Override
    public void showListAudio(List<Audio> audios) {

    }
}
