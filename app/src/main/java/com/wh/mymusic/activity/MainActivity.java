package com.wh.mymusic.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.permissionx.guolindev.PermissionX;
import com.wh.mymusic.PlayService;
import com.wh.mymusic.R;
import com.wh.mymusic.loader.SongLoader;
import com.wh.mymusic.util.MediaPrepareUtils;
import com.wh.mymusic.util.Util;
import com.wh.mymusic.viewmodel.MediaListViewModel;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends BaseMainActivity {
    private String TAG = "WH_"+getClass().getSimpleName();

    private AppBarConfiguration mAppBarConfiguration;
    private final String[] PERMISSIONS = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
    private MediaListViewModel mediaListViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            Util.setAllowDrawUnderStatusBar(getWindow());
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            Util.setStatusBarTranslucent(getWindow());

        setContentView(R.layout.activity_drawer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setImageResource(R.drawable.ic_launcher_foreground);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("ctrl")
                        .setItems(new String[]{
                                "previous",
                                "play|pause",
                                "next",
                                "exit"
                        }, (dialog, which) -> {
                            String ACTION = "";
                            switch (which){
                                case 0:{
                                    ACTION = PlayService.ACTION_CTRL_PLAY_PREVIOUS;
                                    break;
                                }
                                case 1:{
                                    ACTION = PlayService.ACTION_CTRL_PLAY_PAUSE;
                                    break;
                                }
                                case 2:{
                                    ACTION = PlayService.ACTION_CTRL_PLAY_NEXT;
                                    break;
                                }
                                case 3:{
                                    ACTION = PlayService.ACTION_CTRL_STOP_THEN_EXIT;
                                    break;
                                }
                            }
                            Intent intent = new Intent();
                            intent.setAction(ACTION);
                            sendBroadcast(intent);
                        })
                        .create()
                        .show();
            }
        });
//        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        mAppBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.localMainFragment, R.id.AFragment,R.id.BFragment,R.id.settingsFragment)
//                .setDrawerLayout(drawer)
//                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        mediaListViewModel = ViewModelProviders.of(this).get(MediaListViewModel.class);


        PermissionX.init(this)
                .permissions(PERMISSIONS)
                .onExplainRequestReason((scope, deniedList) -> {
                    String message = "myMusic need these permissions for moral functions";
                    scope.showRequestReasonDialog(deniedList, message, "ok", "cancel");
                })
                .request((allGranted, grantedList, deniedList) -> {
                    if (allGranted) {
                        Toast.makeText(this, "所有申请的权限都已通过", Toast.LENGTH_SHORT).show();
                        mediaListViewModel.setVideosMutableLiveData(MediaPrepareUtils.getVideos(getApplicationContext()));
                        mediaListViewModel.setSongsMutableLiveData(SongLoader.getAllSongs(getApplicationContext()));
                        mediaListViewModel.setAudioAlbumMutableLiveData(MediaPrepareUtils.getAudioAlbums(getApplicationContext()));
                    } else {
                        Toast.makeText(this, "您拒绝了如下权限：$deniedList", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}