package com.wh.mymusic;

import android.app.Application;
import android.graphics.Bitmap;
import android.util.Log;

import androidx.core.util.Pair;

import com.wh.mymusic.beam.Audio;
import com.wh.mymusic.beam.AudioAlbum;
import com.wh.mymusic.beam.Song;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


public class BaseApp extends Application {
    private static String TAG = "WH_"+ BaseApp.class.getSimpleName();
    public static List<Song> songsAll;
    public static List<Audio> audiosToPlay;
    public static List<AudioAlbum> audioAlbums;
    public static List<Pair<Long, Bitmap>> albumCovers;
    public static long currentSongId = -1L;

    public BaseApp() {
        songsAll = new ArrayList<>();
        audiosToPlay = new ArrayList<>();
        audioAlbums = new ArrayList<>();
        albumCovers = new ArrayList<>();
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate: ");
        super.onCreate();
//        handleSSLHandshake();
    }


    public static void handleSSLHandshake() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }

                @Override
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }};

            SSLContext sc = SSLContext.getInstance("TLS");
            // trustAllCerts信任所有的证书
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> {
                Log.d(TAG, "verify: return true");
                return true;
            });
            Log.d(TAG, "handleSSLHandshake: return true");
        } catch (Exception ignored) {
            Log.d(TAG, "handleSSLHandshake: "+ignored.getLocalizedMessage());
        }
    }

}

