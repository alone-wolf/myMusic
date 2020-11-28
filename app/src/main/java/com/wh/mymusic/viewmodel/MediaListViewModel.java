package com.wh.mymusic.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.wh.mymusic.beam.Audio;
import com.wh.mymusic.beam.AudioAlbum;
import com.wh.mymusic.beam.Song;
import com.wh.mymusic.beam.Video;

import java.util.ArrayList;
import java.util.List;

public class MediaListViewModel extends ViewModel {

    MutableLiveData<List<Song>> songsMutableLiveData;
    MutableLiveData<List<Video>> videosMutableLiveData;
    MutableLiveData<List<AudioAlbum>> audioAlbumMutableLiveData;

    public MediaListViewModel(){
        songsMutableLiveData = new MutableLiveData<>();
        songsMutableLiveData.setValue(new ArrayList<>());
        videosMutableLiveData = new MutableLiveData<>();
        videosMutableLiveData.setValue(new ArrayList<>());
        audioAlbumMutableLiveData = new MutableLiveData<>();
        audioAlbumMutableLiveData.setValue(new ArrayList<>());
    }

    public void setSongsMutableLiveData(List<Song> songs) {
        this.songsMutableLiveData.postValue(songs);
    }

    public MutableLiveData<List<Song>> getSongsMutableLiveData(){
        return songsMutableLiveData;
    }

    public void setVideosMutableLiveData(List<Video> videos) {
        this.videosMutableLiveData.postValue(videos);
    }

    public MutableLiveData<List<Video>> getVideosMutableLiveData() {
        return videosMutableLiveData;
    }

    public void setAudioAlbumMutableLiveData(List<AudioAlbum>audioAlbums){
        this.audioAlbumMutableLiveData.postValue(audioAlbums);
    }
    public MutableLiveData<List<AudioAlbum>> getAudioAlbumMutableLiveData(){
        return this.audioAlbumMutableLiveData;
    }
}
