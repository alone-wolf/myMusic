package com.wh.mymusic.beam;

public class AudioAlbum {
    private String albumName;
    private Long albumId;
    private String albumArtist;
    private String albumCoverResource;

    public AudioAlbum(String albumName, Long albumId, String albumArtist, String albumCoverResource) {
        this.albumName = albumName;
        this.albumId = albumId;
        this.albumArtist = albumArtist;
        this.albumCoverResource = albumCoverResource;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }

    public String getAlbumArtist() {
        return albumArtist;
    }

    public void setAlbumArtist(String albumArtist) {
        this.albumArtist = albumArtist;
    }

    public String getAlbumCoverResource() {
        return albumCoverResource;
    }

    public void setAlbumCoverResource(String albumCoverResource) {
        this.albumCoverResource = albumCoverResource;
    }
}
