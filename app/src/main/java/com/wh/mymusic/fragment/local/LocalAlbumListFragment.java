package com.wh.mymusic.fragment.local;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.wh.mymusic.R;
import com.wh.mymusic.adapter.LocalAlbumListKotlinAdapter;
import com.wh.mymusic.beam.AudioAlbum;
import com.wh.mymusic.fragment.BaseFragment;
import com.wh.mymusic.viewmodel.MediaListViewModel;

import java.util.ArrayList;
import java.util.List;

public class LocalAlbumListFragment extends BaseFragment {

    private RecyclerView rv_local_album_list;
    private MediaListViewModel mediaListViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_local_album,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rv_local_album_list = view.findViewById(R.id.rv_local_album_list);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mediaListViewModel = ViewModelProviders.of(requireActivity()).get(MediaListViewModel.class);


        LocalAlbumListKotlinAdapter localAlbumListKotlinAdapter = new LocalAlbumListKotlinAdapter(getMActivity());

        rv_local_album_list.setLayoutManager(new GridLayoutManager(requireContext(),1));
        rv_local_album_list.setAdapter(localAlbumListKotlinAdapter);

        List<AudioAlbum>audioAlbums = new ArrayList<>();
        audioAlbums.add(new AudioAlbum("",100L,"",""));
        audioAlbums.add(new AudioAlbum("",101L,"",""));
        audioAlbums.add(new AudioAlbum("",1000L,"",""));

        localAlbumListKotlinAdapter.submitList(audioAlbums);

    }
}
