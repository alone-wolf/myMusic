package com.wh.mymusic.fragment;

import androidx.fragment.app.Fragment;

import com.wh.mymusic.activity.MainActivity;

public class BaseFragment extends Fragment {
    public MainActivity getMainActivity() {
        return (MainActivity) getActivity();
    }
}
