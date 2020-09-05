package com.yumtaufik.gitser.fragment.settings;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.yumtaufik.gitser.R;

public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
    }
}
