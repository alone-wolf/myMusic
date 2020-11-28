package com.wh.mymusic.beam;


public class Song extends Audio{
    public static final Song EMPTY_SONG = new Song(-1L, "", -1, 1975,
            -1L, -1L, -1L, "-1", -1L, "-1",
            "-1","-1","-1",-1L,-1L);


    public Song(long id, String title, int trackNumber, int year, Long dateAdded, Long dateModified,
                long albumId, String albumName, long artistId, String artistName, String displayName,
                String relativePath, String data, Long duration, Long size) {
        super(id, title, trackNumber, year, dateAdded, dateModified,
                albumId, albumName, artistId, artistName, displayName, relativePath, data, duration, size);
    }
}
