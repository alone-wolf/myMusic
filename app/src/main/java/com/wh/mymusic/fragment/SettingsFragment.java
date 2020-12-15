package com.wh.mymusic.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.wh.mymusic.R;
import com.wh.mymusic.activity.WebDavPathActivity;

public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
    }

    @Override
    public boolean onPreferenceTreeClick(Preference preference) {
        switch (preference.getKey()){
            case "goto_test_act":{
                startActivity(new Intent(getContext(), WebDavPathActivity.class));
                break;
            }
        }

        return super.onPreferenceTreeClick(preference);
    }
}