package com.project.nghicv.videochathead.screen;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import com.project.nghicv.videochathead.R;
import com.project.nghicv.videochathead.common.utils.BottomNavigationShiftHelper;
import com.project.nghicv.videochathead.screen.listaudio.ListAudioFragment;
import com.project.nghicv.videochathead.screen.listdevicevideo.ListDeviceVideoFragment;
import com.project.nghicv.videochathead.services.VideoPlayerService;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView mBottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startService(new Intent(this, VideoPlayerService.class));
        setContentView(R.layout.activity_main);
        mBottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_bar);
        BottomNavigationShiftHelper.disableShiftMode(mBottomNavigationView);
        mBottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment fragment = null;
                        switch (item.getItemId()) {
                            case R.id.menu_device_video:
                                fragment = ListDeviceVideoFragment.getInstance();
                                break;

                            case R.id.menu_music:
                                fragment = ListAudioFragment.getInstance();
                                break;

                            case R.id.menu_setting:

                                break;

                            case R.id.menu_youtube:

                                break;
                        }

                        if (fragment != null) {
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.container, fragment)
                                    .commit();
                        }
                        return true;
                    }
                });
        initContentFirstTime();
    }

    private void initContentFirstTime() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, ListDeviceVideoFragment.getInstance())
                .commit();
    }
}

