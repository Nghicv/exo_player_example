package com.project.nghicv.videochathead.screen.listonlinevideo;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.project.nghicv.videochathead.R;
import com.project.nghicv.videochathead.base.BaseFragment;
import com.project.nghicv.videochathead.databinding.FragmentListOnlineVideoBinding;

public class ListOnlineVideoFragment extends BaseFragment<ListOnlineVideoContract.Presenter>
        implements ListOnlineVideoContract.View {

    public static ListOnlineVideoFragment getInstance() {
        return new ListOnlineVideoFragment();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        FragmentListOnlineVideoBinding binding = DataBindingUtil.inflate(inflater, R.layout
                .fragment_list_online_video, container, false);
        return binding.getRoot();
    }

    @Override
    public void setPresenter(ListOnlineVideoContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onCreatePresenter() {
        mPresenter = new ListOnlineVideoPresenter(this);
    }
}
