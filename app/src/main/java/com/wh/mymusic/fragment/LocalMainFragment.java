package com.wh.mymusic.fragment;

import android.Manifest;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.permissionx.guolindev.PermissionX;
import com.wh.mymusic.BaseApp;
import com.wh.mymusic.R;
import com.wh.mymusic.beam.AudioAlbum;
import com.wh.mymusic.beam.Song;
import com.wh.mymusic.loader.SongLoader;
import com.wh.mymusic.util.MediaPrepareUtils;
import com.wh.mymusic.viewmodel.MediaListViewModel;

import java.util.List;

public class LocalMainFragment extends BaseFragment {
    private String TAG = "WH_"+getClass().getSimpleName();

    private final String[] PERMISSIONS = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};

    private ViewPager2 vp_main_local;
    private TabLayout tabLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_local,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vp_main_local = view.findViewById(R.id.vp_main_local);
        tabLayout = view.findViewById(R.id.tl_main_local);
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

        vp_main_local.setAdapter(new FragmentStateAdapter(requireActivity()) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                switch (position) {
                    case 0: {
                        return new LocalAudioListFragment();
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

        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        String[] titles = new String[]{"Audio", "Album", "Artist","Folder"};
        new TabLayoutMediator(tabLayout, vp_main_local, (tab, position) -> tab.setText(titles[position]))
                .attach();


        PermissionX.init(this)
                .permissions(PERMISSIONS)
                .onExplainRequestReason((scope, deniedList) -> {
                    String message = "myMusic need these permissions for moral functions";
                    scope.showRequestReasonDialog(deniedList, message, "ok", "cancel");
                })
                .request((allGranted, grantedList, deniedList) -> {
                    if (allGranted) {
                        Toast.makeText(requireActivity(), "所有申请的权限都已通过", Toast.LENGTH_SHORT).show();
                        mediaListViewModel.setVideosMutableLiveData(MediaPrepareUtils.getVideos(requireContext().getApplicationContext()));
                        mediaListViewModel.setSongsMutableLiveData(SongLoader.getAllSongs(requireContext().getApplicationContext()));
                        mediaListViewModel.setAudioAlbumMutableLiveData(MediaPrepareUtils.getAudioAlbums(requireContext().getApplicationContext()));
                    } else {
                        Toast.makeText(requireActivity(), "您拒绝了如下权限：$deniedList", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
