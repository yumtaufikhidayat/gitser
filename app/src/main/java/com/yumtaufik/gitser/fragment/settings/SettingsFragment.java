package com.yumtaufik.gitser.fragment.settings;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import com.yumtaufik.gitser.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    SwitchPreferenceCompat switchNotifications;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
    }
}
