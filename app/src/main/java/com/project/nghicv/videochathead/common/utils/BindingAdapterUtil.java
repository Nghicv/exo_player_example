package com.project.nghicv.videochathead.common.utils;

import android.databinding.BindingAdapter;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.project.nghicv.videochathead.R;
import java.lang.reflect.Field;

public class BindingAdapterUtil {

    private final static String TAG = "DEBUG_BOTTOM_NAV_UTIL";

    @BindingAdapter("bind:imageUrl")
    public static void loadImageUrl(ImageView view, String url) {
        Glide.with(view.getContext()).load(url).error(R.drawable.ic_launcher).into(view);
    }

    @BindingAdapter("bind:shiftMode")
    public static void disableShiftMode(BottomNavigationView view, boolean isShift) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                item.setShiftingMode(false);
                // set once again checked value, so view will be updated
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.d(TAG, "Unable to get shift mode field");
        } catch (IllegalAccessException e) {
            Log.d(TAG, "Unable to change value of shift mode");
        }
    }
}
