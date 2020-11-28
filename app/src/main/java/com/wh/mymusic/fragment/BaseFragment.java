package com.wh.mymusic.fragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.wh.mymusic.activity.MainActivity;

public class BaseFragment extends Fragment {
    public AppCompatActivity getMActivity() {
        return (AppCompatActivity) getActivity();
    }
}
