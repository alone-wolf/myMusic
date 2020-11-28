package com.wh.mymusic.loader;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.BaseColumns;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.wh.mymusic.beam.Song;
import com.wh.mymusic.util.PreferenceUtil;

import java.util.ArrayList;
import java.util.List;

public class SongLoader {
    protected static final String BASE_SELECTION = MediaStore.Audio.AudioColumns.IS_MUSIC + "=1" + " AND " + MediaStore.Audio.AudioColumns.TITLE + " != ''";
    protected static final String[] BASE_PROJECTION = new String[]{
            BaseColumns._ID,// 0
            MediaStore.Audio.AudioColumns.TITLE,// 1
            MediaStore.Audio.AudioColumns.TRACK,// 2
            MediaStore.Audio.AudioColumns.YEAR,// 3
            MediaStore.Audio.AudioColumns.DATE_ADDED,// 4
            MediaStore.Audio.AudioColumns.DATE_MODIFIED,// 5

            MediaStore.Audio.AudioColumns.ALBUM_ID,// 6
            MediaStore.Audio.AudioColumns.ALBUM,// 7
            MediaStore.Audio.AudioColumns.ARTIST_ID,// 8
            MediaStore.Audio.AudioColumns.ARTIST,// 9

            MediaStore.Audio.AudioColumns.DISPLAY_NAME, // 10
            MediaStore.Audio.AudioColumns.RELATIVE_PATH,// 11

            MediaStore.Audio.AudioColumns.DATA,// 12

            MediaStore.Audio.AudioColumns.DURATION,// 13
            MediaStore.Audio.AudioColumns.SIZE  // 14
    };

    public static Uri getSongFileUri(long songId) {
        return ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, songId);
    }

    @NonNull
    public static List<Song> getAllSongs(@NonNull Context context) {
        Cursor cursor = makeSongCursor(context, null, null);
        return getSongs(cursor);
    }

    @NonNull
    public static List<Song> getSongs(@NonNull final Context context, final String query) {
        Cursor cursor = makeSongCursor(context, MediaStore.Audio.AudioColumns.TITLE + " LIKE ?", new String[]{"%" + query + "%"});
        return getSongs(cursor);
    }

    @NonNull
    public static Song getSong(@NonNull final Context context, final long queryId) {
        Cursor cursor = makeSongCursor(context, MediaStore.Audio.AudioColumns._ID + "=?", new String[]{String.valueOf(queryId)});
        return getSong(cursor);
    }

    @NonNull
    public static List<Song> getSongs(@Nullable final Cursor cursor) {
        List<Song> songs = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                songs.add(getSongFromCursorImpl(cursor));
            } while (cursor.moveToNext());
        }

        if (cursor != null)
            cursor.close();
        return songs;
    }

    @NonNull
    public static Song getSong(@Nullable Cursor cursor) {
        Song song;
        if (cursor != null && cursor.moveToFirst()) {
            song = getSongFromCursorImpl(cursor);
        } else {
            song = Song.EMPTY_SONG;
        }
        if (cursor != null) {
            cursor.close();
        }
        return song;
    }

    @NonNull
    private static Song getSongFromCursorImpl(@NonNull Cursor cursor) {
        final long id = cursor.getLong(0);
        final String title = cursor.getString(1);
        final int trackNumber = cursor.getInt(2);
        final int year = cursor.getInt(3);
        Long dateAdded = cursor.getLong(4);
        Long dateModified = cursor.getLong(5);

        final long albumId = cursor.getLong(6);
        final String albumName = cursor.getString(7);
        final long artistId = cursor.getLong(8);
        final String artistName = cursor.getString(9);

        String displayName = cursor.getString(10);
        String relativePath = cursor.getString(11);

        final String data = cursor.getString(12);

        final long duration = cursor.getLong(13);
        final long size = cursor.getLong(14);

        Song a =  new Song(id,title,trackNumber,year,dateAdded,dateModified,albumId,albumName,artistId,artistName,displayName,relativePath,data,duration,size);
//        a.setUri(ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, cursor.getLong(0)));
        return a;
    }

    @Nullable
    public static Cursor makeSongCursor(@NonNull final Context context, @Nullable final String selection, final String[] selectionValues) {
        return makeSongCursor(context, selection, selectionValues, PreferenceUtil.getInstance(context).getSongSortOrder());
    }

    @Nullable
    public static Cursor makeSongCursor(@NonNull final Context context, @Nullable String selection, String[] selectionValues, final String sortOrder) {
        if (selection != null && !selection.trim().equals("")) {
            selection = BASE_SELECTION + " AND " + selection;
        } else {
            selection = BASE_SELECTION;
        }

        // Blacklist
        List<String> paths = new ArrayList<>();
        paths.add("/storage/emulated/0/smartisan/Recorder");
        paths.add("/storage/emulated/0/trainings");
        if (!paths.isEmpty()) {
            selection = generateBlacklistSelection(selection, paths.size());
            selectionValues = addBlacklistSelectionValues(selectionValues, paths);
        }

        try {
            return context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                    BASE_PROJECTION, selection, selectionValues, sortOrder);
        } catch (SecurityException e) {
            return null;
        }
    }

    private static String generateBlacklistSelection(String selection, int pathCount) {
        StringBuilder newSelection = new StringBuilder(selection != null && !selection.trim().equals("") ? selection + " AND " : "");
        newSelection.append(MediaStore.Audio.AudioColumns.DATA + " NOT LIKE ?");
        for (int i = 0; i < pathCount - 1; i++) {
            newSelection.append(" AND " + MediaStore.Audio.AudioColumns.DATA + " NOT LIKE ?");
        }
        return newSelection.toString();
    }

    private static String[] addBlacklistSelectionValues(String[] selectionValues, List<String> paths) {
        if (selectionValues == null) selectionValues = new String[0];
        String[] newSelectionValues = new String[selectionValues.length + paths.size()];
        System.arraycopy(selectionValues, 0, newSelectionValues, 0, selectionValues.length);
        for (int i = selectionValues.length; i < newSelectionValues.length; i++) {
            newSelectionValues[i] = paths.get(i - selectionValues.length) + "%";
        }
        return newSelectionValues;
    }


}
