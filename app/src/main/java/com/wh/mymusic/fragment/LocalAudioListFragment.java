package com.wh.mymusic.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wh.mymusic.R;
import com.wh.mymusic.adapter.SongListAdapter;
import com.wh.mymusic.beam.Song;
import com.wh.mymusic.viewmodel.MediaListViewModel;

import java.util.List;

public class LocalAudioListFragment extends BaseFragment {

    private RecyclerView rv_song_list;
    private MediaListViewModel mediaListViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_video_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rv_song_list = view.findViewById(R.id.rv_song_list);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mediaListViewModel = ViewModelProviders.of(requireActivity()).get(MediaListViewModel.class);

        SongListAdapter songListAdapter = new SongListAdapter(getMainActivity());
        rv_song_list.setLayoutManager(new LinearLayoutManager(requireContext()));
        rv_song_list.setAdapter(songListAdapter);

        mediaListViewModel.getSongsMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<Song>>() {
            @Override
            public void onChanged(List<Song> songs) {
                songListAdapter.submitList(songs);
            }
        });


    }
}
