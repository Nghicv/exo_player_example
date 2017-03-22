package com.project.nghicv.videochathead.base;

import android.support.v4.app.Fragment;

public abstract class BaseFragment<T extends BasePresenter> extends Fragment
        implements BaseView<T> {
    private T mPresenter;
}
