package com.yumtaufik.gitser.fragment.settings;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.preference.CheckBoxPreference;
import androidx.preference.ListPreference;
import androidx.preference.PreferenceFragmentCompat;

import com.yumtaufik.gitser.R;

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    String KEY_DEFAULT_VALUE = "KEY_DEFAULT_VALUE";
    String KEY_LIST_LANGUAGES;
    String KEY_CHECKBOX_NOTIFICATIONS;

    ListPreference listPreference;
    CheckBoxPreference checkBoxPreference;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.root_preferences);

        setInit();

        setSummaries();
    }

    private void setInit() {

        KEY_LIST_LANGUAGES = getResources().getString(R.string.keyLanguage);
        KEY_CHECKBOX_NOTIFICATIONS = getResources().getString(R.string.keyNotifications);

        listPreference = findPreference(KEY_LIST_LANGUAGES);
        checkBoxPreference = findPreference(KEY_CHECKBOX_NOTIFICATIONS);
    }

    private void setSummaries() {
        SharedPreferences sharedPreferences = getPreferenceManager().getSharedPreferences();
        listPreference.setEntryValues(new String[]{sharedPreferences.getString(KEY_LIST_LANGUAGES, String.valueOf(2))});
        checkBoxPreference.setChecked(sharedPreferences.getBoolean(KEY_CHECKBOX_NOTIFICATIONS, false));
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        if (key.equals(KEY_LIST_LANGUAGES)) {
            listPreference.setSummary(sharedPreferences.getString(KEY_LIST_LANGUAGES, KEY_DEFAULT_VALUE));
        }

        if (key.equals(KEY_CHECKBOX_NOTIFICATIONS)) {
            checkBoxPreference.setChecked(sharedPreferences.getBoolean(KEY_CHECKBOX_NOTIFICATIONS, false));
        }
    }
}
