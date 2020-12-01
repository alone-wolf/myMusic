package com.wh.mymusic;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.MediaMetadata;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.wh.mymusic.activity.MainActivity;
import com.wh.mymusic.beam.Audio;
import com.wh.mymusic.loader.SongLoader;
import com.wh.mymusic.player.MyExoPlayer;

import java.util.ArrayList;
import java.util.List;

public class PlayService extends Service {
    private String TAG = "WH_" + getClass().getSimpleName();
    private SimpleExoPlayer player;
    private MusicBinder musicBinder = new MusicBinder();
    private NotificationCompat.Builder frontActivityNotificationBuilder;
    private NotificationManager notificationManager;
    public static final String ACTION_CTRL_ORIGINAL_PLAY = "ACTION_CTRL_ORIGINAL_PLAY"; // 启动程序后初次点击播放 包含播放方式：列表循环播放 单曲循环播放 播放结束即停止（列表或单曲） 发送id
    public static final String ACTION_CTRL_PLAY_PAUSE = "ACTION_CTRL_PLAY_PAUSE"; //
    public static final String ACTION_CTRL_STOP_THEN_EXIT = "ACTION_CTRL_STOP_THEN_EXIT";
    public static final String ACTION_CTRL_PLAY_PREVIOUS = "ACTION_CTRL_PLAY_PREVIOUS";
    public static final String ACTION_CTRL_PLAY_NEXT = "ACTION_CTRL_PLAY_NEXT";
    private BroadcastReceiver broadcastReceiver;
    //    public static final String ACTION_CTRL_UPDATE_PLAY_LIST= "ACTION_CTRL_UPDATE_PLAY_LIST";
//    public static final String ACTION_CTRL_ADD_TO_PLAY_LIST= "ACTION_CTRL_ADD_TO_PLAY_LIST";

    public PlayService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
//         TODO: Return the communication channel to the service.
        return musicBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String ACTION = intent.getAction() + "";
                switch (ACTION) {
                    case ACTION_CTRL_STOP_THEN_EXIT: {
                        Log.d(TAG, "onReceive: ACTION_CTRL_STOP_THEN_EXIT");
                        PlayService.this.stopSelf();
                        break;
                    }
                    case ACTION_CTRL_PLAY_PAUSE: {
                        Log.d(TAG, "onReceive: ACTION_CTRL_PLAY_PAUSE");
                        if (player.isPlaying()) {
                            player.pause();
                        } else {
                            player.play();
                        }
                        break;
                    }
                    case ACTION_CTRL_PLAY_NEXT: {
                        Log.d(TAG, "onReceive: " + ACTION);
                        if (player.hasNext()) {
                            player.next();
                            if (!player.isPlaying()) {
                                player.play();
                            }
                        }
                        break;
                    }
                    case ACTION_CTRL_PLAY_PREVIOUS: {
                        Log.d(TAG, "onReceive: " + ACTION);
                        if (player.hasPrevious()) {
                            player.previous();
                            if (!player.isPlaying()) {
                                player.play();
                            }
                        }
                        break;
                    }
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_CTRL_STOP_THEN_EXIT);
        intentFilter.addAction(ACTION_CTRL_PLAY_PAUSE);
        intentFilter.addAction(ACTION_CTRL_PLAY_NEXT);
        intentFilter.addAction(ACTION_CTRL_PLAY_PREVIOUS);
        registerReceiver(broadcastReceiver, intentFilter);


        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(
                        new NotificationChannel("playing", "name",
                        NotificationManager.IMPORTANCE_LOW));
            }
        }

        Intent intentExitService = new Intent();
        intentExitService.setAction(ACTION_CTRL_STOP_THEN_EXIT);

        Intent intentPlayPause = new Intent();
        intentPlayPause.setAction(ACTION_CTRL_PLAY_PAUSE);

        Intent intentPlayNext = new Intent();
        intentPlayNext.setAction(ACTION_CTRL_PLAY_NEXT);

        Intent intentPlayPrevious = new Intent();
        intentPlayPrevious.setAction(ACTION_CTRL_PLAY_PREVIOUS);

        Intent intent_start_main_activity = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent_start_main_activity = PendingIntent.getActivity(this, 0, intent_start_main_activity, 0);

        frontActivityNotificationBuilder = new NotificationCompat.Builder(this, "playing")
                .setContentIntent(pendingIntent_start_main_activity)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .addAction(R.drawable.ic_launcher_foreground, "exit", PendingIntent.getBroadcast(this, 1, intentExitService, 0))
                .addAction(R.drawable.ic_launcher_foreground, "playpause", PendingIntent.getBroadcast(this, 2, intentPlayPause, 0))
                .addAction(R.drawable.ic_launcher_foreground, "next", PendingIntent.getBroadcast(this, 3, intentPlayNext, 0))
                .addAction(R.drawable.ic_launcher_foreground, "previous", PendingIntent.getBroadcast(this, 4, intentPlayPrevious, 0));
        startForeground(1, frontActivityNotificationBuilder.build());

        player = MyExoPlayer.getInstance(getApplicationContext());
        player.addListener(new SimpleExoPlayer.EventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, int reason) {
                Log.d(TAG, "onTimelineChanged: " + reason);
            }

            @Override
            public void onIsPlayingChanged(boolean isPlaying) {
                // 发送广播用来给主界面更新控制器
            }

            @Override
            public void onMediaItemTransition(@Nullable MediaItem mediaItem, int reason) {
                if (mediaItem != null) {
                    frontActivityNotificationBuilder.setContentTitle(mediaItem.mediaMetadata.title);
                    notificationManager.notify(1, frontActivityNotificationBuilder.build());
                }
            }
        });

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: " + BaseApp.songsAll.size());
        String ACTION = intent.getAction() + "";

        int position = intent.getIntExtra("id", 0);
        String title = intent.getStringExtra("title") + "";

        player.prepare();

        List<MediaItem> mediaItems = new ArrayList<>();

        for (int i = position; i < BaseApp.songsAll.size(); i++) {
            Audio a = BaseApp.songsAll.get(i);
            MediaItem m = new MediaItem.Builder()
                    .setUri(SongLoader.getSongFileUri(a.getId()))
                    .setMediaMetadata(new MediaMetadata.Builder().setTitle(a.getTitle()).build())
                    .build();
            mediaItems.add(m);
        }

        frontActivityNotificationBuilder.setContentTitle(title);
        notificationManager.notify(1, frontActivityNotificationBuilder.build());
        player.setMediaItems(mediaItems);

        if (player.isPlaying()) {
            player.stop();
        } else {
            player.play();
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        player.stop();
        player.release();
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            stopForeground(Service.STOP_FOREGROUND_REMOVE);
        }
    }

    public class MusicBinder extends Binder {
        @NonNull
        public PlayService getService() {
            return PlayService.this;
        }
    }
}