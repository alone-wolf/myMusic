package com.wh.mymusic.beam;

import android.net.Uri;

public class Audio {
    private Uri uri;

    private long id;
    private String title;
    private int trackNumber;
    private int year;

    private Long dateAdded;
    private Long dateModified;

    private Long albumId;
    private String albumName;
    private long artistId;
    private String artistName;

    private String displayName;
    private String relativePath;

    private String data;

    private Long duration;
    private Long size;

    public Audio(long id, String title, int trackNumber, int year, Long dateAdded, Long dateModified,
                 long albumId, String albumName, long artistId, String artistName, String displayName,
                 String relativePath, String data, Long duration, Long size) {
        this.id = id;
        this.title = title;
        this.trackNumber = trackNumber;
        this.year = year;
        this.dateAdded = dateAdded;
        this.dateModified = dateModified;
        this.albumId = albumId;
        this.albumName = albumName;
        this.artistId = artistId;
        this.artistName = artistName;
        this.displayName = displayName;
        this.relativePath = relativePath;
        this.data = data;
        this.duration = duration;
        this.size = size;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTrackNumber() {
        return trackNumber;
    }

    public void setTrackNumber(int trackNumber) {
        this.trackNumber = trackNumber;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Long getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Long dateAdded) {
        this.dateAdded = dateAdded;
    }

    public Long getDateModified() {
        return dateModified;
    }

    public void setDateModified(Long dateModified) {
        this.dateModified = dateModified;
    }

    public long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(long albumId) {
        this.albumId = albumId;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public long getArtistId() {
        return artistId;
    }

    public void setArtistId(long artistId) {
        this.artistId = artistId;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getRelativePath() {
        return relativePath;
    }

    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }
}
