package com.yumtaufik.gitser.fragment.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.preference.CheckBoxPreference;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.yumtaufik.gitser.R;

import es.dmoral.toasty.Toasty;

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    String KEY_DEFAULT_VALUE = "KEY_DEFAULT_VALUE";
    String KEY_LIST_LANGUAGES, KEY_CHECKBOX_ALARM;

    SharedPreferences sharedPreferences;

    ListPreference listPreference;
    CheckBoxPreference checkBoxPreference;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.root_preferences);

        setInit();

        setPreferences();

        setCheckedAlarm();

        setListOfLanguages();
    }

    private void setInit() {

        KEY_LIST_LANGUAGES = getResources().getString(R.string.keyLanguage);
        KEY_CHECKBOX_ALARM = getResources().getString(R.string.keyNotifications);

        listPreference = findPreference(KEY_LIST_LANGUAGES);
        checkBoxPreference = findPreference(KEY_CHECKBOX_ALARM);
    }

    private void setPreferences() {
        sharedPreferences = getPreferenceManager().getSharedPreferences();
    }

    private void setCheckedAlarm() {

        checkBoxPreference.setChecked(sharedPreferences.getBoolean(KEY_CHECKBOX_ALARM, false));
        checkBoxPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object object) {

                boolean isAlarmOn = (boolean) object;
                if (isAlarmOn) {
                    //Insert code here to call alarm manager at 9 AM
                    Toasty.success(requireActivity(), "Alarm activated", Toast.LENGTH_SHORT, true).show();
                } else {
                    Toasty.success(requireActivity(), "Alarm deactivated", Toast.LENGTH_SHORT, true).show();
                }

                return true;
            }
        });
    }

    private void setListOfLanguages() {

        String languages = sharedPreferences.getString(KEY_LIST_LANGUAGES, KEY_DEFAULT_VALUE);

        if ("1".equals(languages)) {
            Log.i("langIndonesia", "setLangIndonesia: ");
        } else if ("2".equals(languages)) {
            Log.i("langEngUS", "setLangEngUS: ");
        }

        listPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object object) {

                String languageItemSelected = (String) object;
                if (preference.getKey().equals(KEY_LIST_LANGUAGES)) {
                    switch (languageItemSelected) {
                        case "1":
                            //Insert code here to call Indonesia language
                            Toasty.success(requireActivity(), "Indonesia selected", Toast.LENGTH_SHORT, true).show();
                            break;

                        case "2":
                            //Insert code here to call English(US) language
                            Toasty.success(requireActivity(), "English (US) selected", Toast.LENGTH_SHORT, true).show();
                            break;
                    }
                }
                return true;
            }
        });
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

        Log.i("KEY_LIST_LANGUAGES", "onSharedListLanguages: ");
        Log.i("KEY_CHECKBOX_ALARM", "onSharedCheckboxAlarm: ");
    }
}
