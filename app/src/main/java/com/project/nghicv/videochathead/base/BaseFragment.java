package com.project.nghicv.videochathead.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

public abstract class BaseFragment<T extends BasePresenter> extends Fragment
        implements BaseView<T> {
    protected T mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreatePresenter();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscriber();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unSubscriber();
    }

    @Override
    public Context getContextFragment() {
        return getActivity();
    }
}
