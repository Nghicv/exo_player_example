package com.project.nghicv.videochathead.screen.setting;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.project.nghicv.videochathead.R;
import com.project.nghicv.videochathead.base.BaseFragment;
import com.project.nghicv.videochathead.databinding.FragmentSettingBinding;

public class SettingFragment extends BaseFragment<SettingContract.Presenter>
        implements SettingContract.View {

    public static SettingFragment getInstance() {
        return new SettingFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        FragmentSettingBinding binding = DataBindingUtil.inflate(inflater, R.layout
                .fragment_setting, container, false);
        return binding.getRoot();
    }

    @Override
    public void setPresenter(SettingContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onCreatePresenter() {
        mPresenter = new SettingPresenter(this);
    }
}
