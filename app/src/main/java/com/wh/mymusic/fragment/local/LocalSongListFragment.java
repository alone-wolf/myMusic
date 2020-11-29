package com.wh.mymusic.fragment.local;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wh.mymusic.R;
import com.wh.mymusic.adapter.LocalSongListKotlinAdapter;
import com.wh.mymusic.fragment.BaseFragment;
import com.wh.mymusic.viewmodel.MediaListViewModel;

import static android.view.View.SCROLLBARS_INSIDE_OVERLAY;


public class LocalSongListFragment extends BaseFragment {
    private String TAG = "WH_"+getClass().getSimpleName();

    private RecyclerView rv_song_list;
    private MediaListViewModel mediaListViewModel;

    private static LocalSongListFragment instance;

    public static LocalSongListFragment getInstance(){
        if(instance==null){
            instance = new LocalSongListFragment();
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_local_song, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rv_song_list = view.findViewById(R.id.rv_local_song_list);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mediaListViewModel = ViewModelProviders.of(requireActivity()).get(MediaListViewModel.class);

        LocalSongListKotlinAdapter songListAdapter = new LocalSongListKotlinAdapter(getMActivity());
        rv_song_list.setLayoutManager(new GridLayoutManager(requireContext(),1));
        rv_song_list.setAdapter(songListAdapter);
        rv_song_list.setScrollBarStyle(SCROLLBARS_INSIDE_OVERLAY);

        mediaListViewModel.getSongsMutableLiveData().observe(getViewLifecycleOwner(), songListAdapter::submitList);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView: ");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");

    }
}
