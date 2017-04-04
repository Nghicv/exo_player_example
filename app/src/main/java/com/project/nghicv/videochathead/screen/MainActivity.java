package com.project.nghicv.videochathead.screen;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import com.project.nghicv.videochathead.R;
import com.project.nghicv.videochathead.databinding.ActivityMainBinding;
import com.project.nghicv.videochathead.screen.listaudio.ListAudioFragment;
import com.project.nghicv.videochathead.screen.listdevicevideo.ListDeviceVideoFragment;
import com.project.nghicv.videochathead.screen.listonlinevideo.ListOnlineVideoFragment;
import com.project.nghicv.videochathead.screen.setting.SettingFragment;
import com.project.nghicv.videochathead.services.VideoPlayerService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startService(new Intent(this, VideoPlayerService.class));
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.bottomBar.setOnNavigationItemSelectedListener(mItemSelectedListener);
        initContentFirstTime();
    }

    private void initContentFirstTime() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, ListDeviceVideoFragment.getInstance())
                .commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mItemSelectedListener = item -> {
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.menu_device_video:
                fragment = ListDeviceVideoFragment.getInstance();
                break;

            case R.id.menu_music:
                fragment = ListAudioFragment.getInstance();
                break;

            case R.id.menu_setting:
                fragment = SettingFragment.getInstance();
                break;

            case R.id.menu_youtube:
                fragment = ListOnlineVideoFragment.getInstance();
                break;
        }

        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
        }
        return true;
    };
}

