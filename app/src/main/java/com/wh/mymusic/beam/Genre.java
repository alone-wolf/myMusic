package com.wh.mymusic.beam;

public class Genre {
    private long id;
    private String name;
    private int songCount;

    public Genre(long id, String name, int songCount) {
        this.id = id;
        this.name = name;
        this.songCount = songCount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSongCount() {
        return songCount;
    }

    public void setSongCount(int songCount) {
        this.songCount = songCount;
    }
}
