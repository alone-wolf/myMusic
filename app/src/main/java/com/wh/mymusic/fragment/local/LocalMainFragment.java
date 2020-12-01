package com.wh.mymusic.fragment.local;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.wh.mymusic.BaseApp;
import com.wh.mymusic.R;
import com.wh.mymusic.beam.AudioAlbum;
import com.wh.mymusic.beam.Song;
import com.wh.mymusic.fragment.BaseFragment;
import com.wh.mymusic.fragment.BlankFragment;
import com.wh.mymusic.viewmodel.MediaListViewModel;

import java.util.List;

public class LocalMainFragment extends BaseFragment {
    private String TAG = "WH_"+getClass().getSimpleName();


    private ViewPager2 vp_local_main;
    private TabLayout tl_local_main;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_local_main,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vp_local_main = view.findViewById(R.id.vp_local_main);
        tl_local_main = view.findViewById(R.id.tl_local_main);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        MediaListViewModel mediaListViewModel = ViewModelProviders.of(requireActivity()).get(MediaListViewModel.class);
        mediaListViewModel.getSongsMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<Song>>() {
            @Override
            public void onChanged(List<Song> songs) {
                BaseApp.songsAll = songs;
            }
        });

        mediaListViewModel.getAudioAlbumMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<AudioAlbum>>() {
            @Override
            public void onChanged(List<AudioAlbum> audioAlbums) {
                BaseApp.audioAlbums = audioAlbums;
            }
        });

        vp_local_main.setAdapter(new FragmentStateAdapter(requireActivity()) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                switch (position) {
                    case 0: {
                        return new LocalSongListFragment();
                    }
                    case 1:{
                        return new LocalAlbumListFragment();
                    }
                    case 2:{
                        return new LocalArtistListFragment();
                    }
                    case 3:{
                        return new LocalFolderFragment();
                    }
                    default: {
                        return new BlankFragment();
                    }
                }
            }

            @Override
            public int getItemCount() {
                return 4;
            }
        });

        tl_local_main.setTabMode(TabLayout.MODE_SCROLLABLE);
        String[] titles = new String[]{"Audio", "Album", "Artist","Folder"};
        new TabLayoutMediator(tl_local_main, vp_local_main, (tab, position) -> tab.setText(titles[position]))
                .attach();

    }
}
