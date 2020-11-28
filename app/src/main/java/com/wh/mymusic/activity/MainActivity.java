package com.wh.mymusic.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.permissionx.guolindev.PermissionX;
import com.permissionx.guolindev.callback.ExplainReasonCallback;
import com.permissionx.guolindev.callback.RequestCallback;
import com.permissionx.guolindev.request.ExplainScope;
import com.wh.mymusic.BaseApp;
import com.wh.mymusic.util.MediaPrepareUtils;
import com.wh.mymusic.R;
import com.wh.mymusic.beam.Audio;
import com.wh.mymusic.viewmodel.MediaListViewModel;

import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.Q)
public class MainActivity extends AppCompatActivity {
//    private final String[] PERMISSIONS = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};

    private String TAG = "WH_" + getClass().getSimpleName();

    private MediaListViewModel mediaListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mediaListViewModel = ViewModelProviders.of(this).get(MediaListViewModel.class);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT || newConfig.orientation == ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT) {
            Toast.makeText(MainActivity.this, "现在是竖屏", Toast.LENGTH_SHORT).show();
        }
        if (newConfig.orientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE || newConfig.orientation == ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE) {
            Toast.makeText(MainActivity.this, "现在是横屏", Toast.LENGTH_SHORT).show();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        menu.add("settings");
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        Log.d(TAG, "onOptionsItemSelected: " + item.toString());
//        switch (item.toString()) {
//            case "settings": {
//                startActivity(new Intent(this, SettingsActivity.class));
//            }
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}