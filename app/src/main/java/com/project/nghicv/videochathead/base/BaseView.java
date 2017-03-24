package com.project.nghicv.videochathead.base;

import android.content.Context;

/**
 * Created by nghicv on 22/03/2017.
 */

public interface BaseView<P> {
    void setPresenter(P presenter);
    Context getContextFragment();
    void onCreatePresenter();
}
