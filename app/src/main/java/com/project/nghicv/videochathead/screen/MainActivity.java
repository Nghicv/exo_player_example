package com.project.nghicv.videochathead.screen;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import com.project.nghicv.videochathead.R;
import com.project.nghicv.videochathead.screen.listdevicevideo.ListDeviceVideoFragment;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_OVERLAY_PERMISSION = 1;

    private BottomNavigationView mBottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*findViewById(R.id.btn_play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (!Settings.canDrawOverlays(MainActivity.this)) {
                        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri
                                .parse("package:" +getPackageName()));
                        startActivityForResult(intent, REQUEST_CODE_OVERLAY_PERMISSION);
                    } else {
                        startPlayerService();
                    }
                } else {
                    startPlayerService();
                }
            }
        });*/
        mBottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_bar);
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView
                .OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_device_video:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,
                                ListDeviceVideoFragment.getInstance()).commit();
                        break;

                    case R.id.menu_music:

                        break;

                    case R.id.menu_setting:

                        break;

                    case R.id.menu_youtube:

                        break;
                }
                return true;
            }
        });
        initContentFirstTime();
    }

   /* @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_OVERLAY_PERMISSION) {
            if (Settings.canDrawOverlays(this)) {
                startPlayerService();
            }
        }
    }*/

    private void initContentFirstTime() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container,
                ListDeviceVideoFragment.getInstance()).commit();
    }
}

