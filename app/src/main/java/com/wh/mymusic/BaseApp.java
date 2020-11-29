package com.wh.mymusic;

import android.app.Application;
import android.graphics.Bitmap;
import android.net.Uri;

import androidx.core.util.Pair;

import com.wh.mymusic.beam.Audio;
import com.wh.mymusic.beam.AudioAlbum;
import com.wh.mymusic.beam.Song;

import java.util.ArrayList;
import java.util.List;

public class BaseApp extends Application {
    public static List<Song> songsAll;
    public static List<Audio> audiosToPlay;
    public static List<AudioAlbum> audioAlbums;
    public static List<Pair<Long, Bitmap>>albumCovers;
    public static long currentSongId = -1L;

    public BaseApp(){
        songsAll = new ArrayList<>();
        audiosToPlay = new ArrayList<>();
        audioAlbums = new ArrayList<>();
        albumCovers = new ArrayList<>();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
