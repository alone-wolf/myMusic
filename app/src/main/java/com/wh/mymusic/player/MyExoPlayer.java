package com.wh.mymusic.player;

import android.content.Context;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.SimpleExoPlayer;

public class MyExoPlayer {
//    public static SimpleExoPlayer instance;

//    public static void initInstance(Context applicationContext){
//        if(instance==null){
//            instance = new SimpleExoPlayer.Builder(applicationContext).build();
//        }
//    }

    public static SimpleExoPlayer getInstance(Context applicationContext){
         return new SimpleExoPlayer.Builder(applicationContext).build();
    }
}
