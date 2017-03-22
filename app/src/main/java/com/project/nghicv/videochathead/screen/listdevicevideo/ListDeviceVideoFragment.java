package com.project.nghicv.videochathead.screen.listdevicevideo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.project.nghicv.videochathead.base.BaseFragment;
import com.project.nghicv.videochathead.databinding.FragmentListDeviceVideoBinding;

public class ListDeviceVideoFragment extends BaseFragment {
    FragmentListDeviceVideoBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        mBinding = FragmentListDeviceVideoBinding.inflate(inflater, container, false);

        return mBinding.getRoot();
    }

    public static ListDeviceVideoFragment getInstance() {
        return new ListDeviceVideoFragment();
    }
}
