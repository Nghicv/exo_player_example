package com.project.nghicv.videochathead.common.utils;

import android.databinding.BindingAdapter;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.project.nghicv.videochathead.R;

public class BindingAdapterUtil {

    @BindingAdapter("bind:imageUrl")
    public static void loadImageUrl(ImageView view, String url) {
        Glide.with(view.getContext()).load(url).error(R.drawable.ic_launcher).into(view);
    }
}
