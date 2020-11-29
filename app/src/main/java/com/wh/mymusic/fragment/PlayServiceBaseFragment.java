//package com.wh.mymusic.fragment;
//
//import android.content.Context;
//import android.os.Bundle;
//import android.view.View;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.fragment.app.Fragment;
//
//import com.wh.mymusic.activity.BaseMainActivity;
//import com.wh.mymusic.interfaces.MusicServiceEventListener;
//
//public class PlayServiceBaseFragment extends Fragment implements MusicServiceEventListener {
//
//    private BaseMainActivity activity;
//
//    @Override
//    public void onAttach(@NonNull Context context) {
//        super.onAttach(context);
//            activity = (BaseMainActivity) context;
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        activity = null;
//    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        activity.addMusicServiceEventListener(this);
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        activity.removeMusicServiceEventListener(this);
//    }
//
//    @Override
//    public void onPlayingMetaChanged() {
//
//    }
//
//    @Override
//    public void onServiceConnected() {
//
//    }
//
//    @Override
//    public void onServiceDisconnected() {
//
//    }
//
//    @Override
//    public void onQueueChanged() {
//
//    }
//
//    @Override
//    public void onPlayStateChanged() {
//
//    }
//
//    @Override
//    public void onRepeatModeChanged() {
//
//    }
//
//    @Override
//    public void onShuffleModeChanged() {
//
//    }
//
//    @Override
//    public void onMediaStoreChanged() {
//
//    }
//
//
//}
