package com.wh.mymusic.activity;

import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.wh.mymusic.viewmodel.MediaListViewModel;

public class BaseMainActivity extends AppCompatActivity {
    private MediaListViewModel mediaListViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        mediaListViewModel = initVideoListViewModel();
    }

    MediaListViewModel initVideoListViewModel(){
        return ViewModelProviders.of(this).get(MediaListViewModel.class);
    }

    public void addMusicServiceEventListener(){

    }

    public void removeMusicServiceEventListener(){

    }
}
