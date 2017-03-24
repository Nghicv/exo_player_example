package com.project.nghicv.videochathead.screen.listdevicevideo;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.project.nghicv.videochathead.base.BaseFragment;
import com.project.nghicv.videochathead.databinding.FragmentListDeviceVideoBinding;
import com.project.nghicv.videochathead.model.Video;
import com.project.nghicv.videochathead.services.VideoPlayerService;
import java.util.ArrayList;
import java.util.List;

public class ListDeviceVideoFragment extends BaseFragment<ListDeviceVideoContract.Presenter>
        implements ListDeviceVideoContract.View {

    private static final int REQUEST_CODE_OVERLAY_PERMISSION = 1;
    private static final int REQUEST_CODE_PERMISSION_READ_EXTERNAL_STORAGE = 2;

    private FragmentListDeviceVideoBinding mBinding;
    private VideosAdapter mAdapter;
    private final List<Video> mVideos = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        mBinding = FragmentListDeviceVideoBinding.inflate(inflater, container, false);
        mAdapter = new VideosAdapter(mVideos, mPresenter);
        RecyclerView recyclerView = mBinding.rvDeviceVideos;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(mAdapter);
        mBinding.setActionHandler(mPresenter);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[] {
                    Manifest.permission.READ_EXTERNAL_STORAGE
            }, REQUEST_CODE_PERMISSION_READ_EXTERNAL_STORAGE);
        } else {
            mPresenter.loadVideos();
        }
    }

    public static ListDeviceVideoFragment getInstance() {
        return new ListDeviceVideoFragment();
    }

    @Override
    public void setPresenter(ListDeviceVideoContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public Context getContextFragment() {
        return getActivity();
    }

    @Override
    public void onCreatePresenter() {
        mPresenter = new ListDeviceVideoPresenter(this);
    }

    @Override
    public void showVideos(List<Video> videos) {
        mBinding.refreshLayout.setRefreshing(false);
        mVideos.clear();
        mVideos.addAll(videos);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showEmptyView() {
        Snackbar.make(mBinding.getRoot(), "Empty", 1000).show();
    }

    @Override
    public void showVideoPlay(Video video) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(getActivity())) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getActivity().getPackageName()));
                startActivityForResult(intent, REQUEST_CODE_OVERLAY_PERMISSION);
            } else {
                startPlayerService();
            }
        } else {
            startPlayerService();
        }
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

    private void startPlayerService() {
        getActivity().startService(new Intent(getActivity(), VideoPlayerService.class));
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_OVERLAY_PERMISSION) {
            if (Settings.canDrawOverlays(getActivity())) {
                startPlayerService();
            }
        }
    }
}
