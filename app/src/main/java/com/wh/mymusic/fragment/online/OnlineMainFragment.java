package com.wh.mymusic.fragment.online;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.wh.mymusic.R;
import com.wh.mymusic.fragment.BaseFragment;

public class OnlineMainFragment extends BaseFragment {
    private String TAG = "WH_"+getClass().getSimpleName();

    private ViewPager2 vp_online_main;
    private TabLayout tl_online_main;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_online_main,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        vp_online_main = view.findViewById(R.id.vp_online_main);
        tl_online_main = view.findViewById(R.id.tl_online_main);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        vp_online_main.setAdapter(new FragmentStateAdapter(requireActivity()) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                switch (position){
                    case 0:return new OnlineSongListFragment();
                    default:return new OnlineAboutFragment();
                }
            }

            @Override
            public int getItemCount() {
                return 2;
            }
        });

        tl_online_main.setTabMode(TabLayout.MODE_SCROLLABLE);
        String[] titles = new String[]{"Song","About"};

        new TabLayoutMediator(tl_online_main,vp_online_main,((tab, position) -> tab.setText(titles[position]))).attach();
    }
}
