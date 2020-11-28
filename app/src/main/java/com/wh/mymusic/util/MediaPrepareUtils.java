package com.wh.mymusic.util;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.wh.mymusic.BaseApp;
import com.wh.mymusic.beam.Artist;
import com.wh.mymusic.beam.Audio;
import com.wh.mymusic.beam.AudioAlbum;
import com.wh.mymusic.beam.Video;

import java.util.ArrayList;
import java.util.List;

public class MediaPrepareUtils {
    private static String TAG = "WH_MediaPrepareUtils";

    public static List<Video> getVideos(Context applicationContext) {
        List<Video> videos = new ArrayList<>();

        String[] projection = new String[]{
                MediaStore.Video.Media._ID,
                MediaStore.Video.Media.DISPLAY_NAME,
                MediaStore.Video.Media.DURATION,
                MediaStore.Video.Media.SIZE
        };

        String sortOrder = MediaStore.Video.Media.DISPLAY_NAME + " ASC";

        Cursor cursor = applicationContext.getContentResolver().query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                sortOrder);

        int idColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID);
        int nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME);
        int durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION);
        int sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE);

        if (cursor.moveToFirst()) {
            do {
                videos.add(new Video(
                        ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, cursor.getLong(idColumn)),
                        cursor.getString(nameColumn),
                        cursor.getInt(durationColumn),
                        cursor.getInt(sizeColumn)
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();

        return videos;
    }

//    public static List<Artist> getArtists(Context applicationContext){
//        List<Artist> artists = new ArrayList<>();
//
//        String[] projection = new String[]{
//                MediaStore.Audio.Artists._ID,
//                MediaStore.Audio.Artists.ARTIST
//        };
//
//
//    }

    public static List<AudioAlbum> getAudioAlbums(Context applicationContext) {
        List<AudioAlbum> audioAlbums = new ArrayList<>();
        String[] projection = new String[]{
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.ALBUM_ARTIST,
                MediaStore.Audio.Media.ALBUM_ID
        };

        String selection = MediaStore.Audio.Media.RELATIVE_PATH + " not like ? and " + MediaStore.Audio.Media.RELATIVE_PATH + " not like ?";

        String[] selectionArgs = new String[]{"Music/ringtone/gamefree/%", "smartisan/Recorder/"};

        Cursor cursor = applicationContext.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                MediaStore.Audio.Media.DEFAULT_SORT_ORDER
        );

        int idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID);
        int albumColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM);
        int albumArtistColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ARTIST);
        int albumIdColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID);

        Log.d(TAG, "getAudioAlbums: " + cursor.getCount());

        if (cursor.moveToFirst()) {
            do {
                boolean has = false;
                AudioAlbum audioAlbum = new AudioAlbum(
                        cursor.getString(albumColumn),
                        cursor.getInt(albumIdColumn),
                        cursor.getString(albumArtistColumn),
                        ""
                );
                for (AudioAlbum aa : BaseApp.audioAlbums) {
                    if (aa.getAlbumId() == audioAlbum.getAlbumId()) {
                        has = true;
                        break;
                    }
                }
                if (!has) {
                    BaseApp.audioAlbums.add(audioAlbum);
                }
            } while (cursor.moveToNext());
        }

        cursor.close();
        return audioAlbums;
    }

//    public static List<Audio> getAudios(Context applicationContext) {
//        List<Audio> audios = new ArrayList<>();
//
//        String[] projection = new String[]{
//                MediaStore.Audio.Media._ID,
//                MediaStore.Audio.Media.DISPLAY_NAME,
//                MediaStore.Audio.Media.TITLE,
//                MediaStore.Audio.Media.ARTIST,
//                MediaStore.Audio.Media.DURATION,
//                MediaStore.Audio.Media.SIZE,
//                MediaStore.Audio.Media.TRACK,
//                MediaStore.Audio.Media.RELATIVE_PATH,
//                MediaStore.Audio.Media.ALBUM_ID,
//                MediaStore.Audio.Media.ALBUM,
//                MediaStore.Audio.Media.DATE_ADDED,
//                MediaStore.Audio.Media.DATE_MODIFIED,
//                MediaStore.Audio.Media.GENRE,
//                MediaStore.Audio.Media.GENRE_ID,
////                MediaStore.Audio.Media.DATA
//        };
//
//        String selection = MediaStore.Audio.Media.RELATIVE_PATH + " not like ? and " + MediaStore.Audio.Media.RELATIVE_PATH + " not like ?";
//
//        String[] selectionArgs = new String[]{"Music/ringtone/gamefree/%", "smartisan/Recorder/"};
//
//        Cursor cursor = applicationContext.getContentResolver().query(
//                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
//                projection,
//                selection,
//                selectionArgs,
//                MediaStore.Audio.Media.DEFAULT_SORT_ORDER
//        );
//
//        int idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID);
//        int displayNameColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME);
//        int titleColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE);
//        int artistColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST);
//        int durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION);
//        int sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE);
//        int trackColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TRACK);
//        int relativePathColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.RELATIVE_PATH);
//        int albumIdColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID);
//        int albumColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM);
//        int dateAddedColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATE_ADDED);
//        int dateModifiedColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATE_MODIFIED);
//        int genreColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.GENRE);
//        int genreIdColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.GENRE_ID);
//
////      ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, cursor.getLong(idColumn));
//        if (cursor.moveToFirst()) {
//            do {
//                Audio audio = new Audio(
//                        cursor.getString(displayNameColumn),
//                        cursor.getString(titleColumn),
//                        cursor.getString(artistColumn),
//                        cursor.getLong(durationColumn),
//                        cursor.getLong(sizeColumn),
//                        cursor.getString(trackColumn),
//                        cursor.getString(relativePathColumn),
//                        cursor.getInt(albumIdColumn),
//                        cursor.getString(albumColumn),
//                        cursor.getLong(dateAddedColumn),
//                        cursor.getLong(dateModifiedColumn),
//                        cursor.getString(genreColumn),
//                        cursor.getLong(genreIdColumn)
//                );
//                boolean has = false;
//                for (AudioAlbum aa : BaseApp.audioAlbums) {
//                    if (audio.getAlbumId() == aa.getAlbumId()) {
//                        has = true;
//                        break;
//                    }
//                }
//                if (!has) {
//
//                }
//
//            } while (cursor.moveToNext());
//        }
//
//        cursor.close();
//        return audios;
//    }

//    private static String getAlbumCoverSource(Context applicationContext, int albumId) {
//        String mUriAlbums = String.valueOf(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI);
//        String[] projection = new String[]{MediaStore.Audio.Albums.ALBUM_ART};
//
////        String selection = MediaStore.Audio.Media.RELATIVE_PATH + " not like ? and " + MediaStore.Audio.Media.RELATIVE_PATH + " not like ?";
////
////        String[] selectionArgs = new String[]{"Music/ringtone/gamefree/%", "smartisan/Recorder/"};
//
//        Cursor cursor = applicationContext.getContentResolver().query(
//                Uri.parse(mUriAlbums + "/" + albumId),
//                null,
//                null,
//                null,
//                null);
//        Log.d(TAG, "getAlbumCoverSource: getCount" + cursor.getCount());
//        String source = null;
//        if (cursor.moveToFirst()) {
//            source = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));
//            Log.d(TAG, "getAlbumCoverSource: ALBUM_ART " + source);
//            Log.d(TAG, "getAlbumCoverSource: ALBUM " + cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM)));
//            Log.d(TAG, "getAlbumCoverSource: ALBUM_ID " + cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ID)));
//            Log.d(TAG, "getAlbumCoverSource: ARTIST " + cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST)));
//        }else {
//            source="null11";
//        }
//        cursor.close();
//        return source;
//    }
}
