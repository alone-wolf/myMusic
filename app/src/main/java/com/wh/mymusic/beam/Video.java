package com.wh.mymusic.beam;

import android.net.Uri;

public class Video {
    private final Uri uri;
    private final String name;
    private final int duration;
    private final int size;

    public Video(Uri uri, String name, int duration, int size) {
        this.uri = uri;
        this.name = name;
        this.duration = duration;
        this.size = size;
    }

    public Uri getUri() {
        return uri;
    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }

    public int getSize() {
        return size;
    }

//        @RequiresApi(api = Build.VERSION_CODES.Q)
//    public List<Video> getVideo(Context applicationContext) {
//        List<Video> videoList = new ArrayList<>();
//
//        String[] projection = new String[]{
//                MediaStore.Video.Media._ID,
//                MediaStore.Video.Media.DISPLAY_NAME,
//                MediaStore.Video.Media.DURATION,
//                MediaStore.Video.Media.SIZE
//        };
//        String selection = MediaStore.Video.Media.DURATION +
//                " >= ?";
//        String[] selectionArgs = new String[]{
//                String.valueOf(TimeUnit.MILLISECONDS.convert(5, TimeUnit.MINUTES));
//
//        String sortOrder = MediaStore.Video.Media.DISPLAY_NAME + " ASC";
//
//        Cursor cursor = applicationContext.getContentResolver().query(
//                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
//                projection,
//                null,
//                null,
//                sortOrder);
//
//
//        int idColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID);
//        int nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME);
//        int durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION);
//        int sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE);
//
//        while (cursor.moveToNext()) {
//            // Get values of columns for a given video.
//            long id = cursor.getLong(idColumn);
//            String name = cursor.getString(nameColumn);
//            int duration = cursor.getInt(durationColumn);
//            int size = cursor.getInt(sizeColumn);
//
//            Uri contentUri = ContentUris.withAppendedId(
//                    MediaStore.Video.Media.EXTERNAL_CONTENT_URI, id);
//
//            // Stores column values and the contentUri in a local object
//            // that represents the media file.
//            videoList.add(new Video(contentUri, name, duration, size));
//        }
//        cursor.close();
//        return videoList;
//    }
}
