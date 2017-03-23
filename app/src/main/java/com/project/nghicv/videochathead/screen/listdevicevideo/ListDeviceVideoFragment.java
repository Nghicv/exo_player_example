package com.project.nghicv.videochathead.screen.listdevicevideo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.project.nghicv.videochathead.base.BaseFragment;
import com.project.nghicv.videochathead.databinding.FragmentListDeviceVideoBinding;
import com.project.nghicv.videochathead.model.Video;
import java.util.List;

public class ListDeviceVideoFragment extends BaseFragment<ListDeviceVideoContract.Presenter>
        implements ListDeviceVideoContract.View {
    FragmentListDeviceVideoBinding mBinding;
    private static final int REQUEST_CODE_PERMISSION_READ_EXTERNAL_STORAGE = 2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        mBinding = FragmentListDeviceVideoBinding.inflate(inflater, container, false);
        mPresenter = new ListDeviceVideoPresenter(this);
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[] {
                    Manifest.permission.READ_EXTERNAL_STORAGE
            }, REQUEST_CODE_PERMISSION_READ_EXTERNAL_STORAGE);
        } else {
            mPresenter.loadVideos();
        }
        return mBinding.getRoot();
    }

    public static ListDeviceVideoFragment getInstance() {
        return new ListDeviceVideoFragment();
    }

    @Override
    public void setPresenter(ListDeviceVideoContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showVideos(List<Video> videos) {

    }

    @Override
    public void showEmptyView() {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_PERMISSION_READ_EXTERNAL_STORAGE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mPresenter.loadVideos();

                } else {

                    // TODO: 23/03/2017 when user denied permission
                }
                return;
            }
        }

    }
}
