package com.yumtaufik.gitser.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.preference.CheckBoxPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.yumtaufik.gitser.R;
import com.yumtaufik.gitser.service.AlarmReceiver;

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    String KEY_CHECKBOX_ALARM;

    SharedPreferences sharedPreferences;
    CheckBoxPreference checkBoxPreference;

    AlarmReceiver alarmReceiver;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.root_preferences);

        setInit();

        setPreferences();

        setCheckedAlarm();
    }

    private void setInit() {

        KEY_CHECKBOX_ALARM = getResources().getString(R.string.keyNotifications);

        checkBoxPreference = findPreference(KEY_CHECKBOX_ALARM);

        alarmReceiver = new AlarmReceiver();
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
                    alarmReceiver.setRepeatingAlarmNotification(requireActivity(), "09:00");
                } else {
                    alarmReceiver.cancelRepeatingAlarmNotification(requireActivity());
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

        Log.i("KEY_CHECKBOX_ALARM", "onSharedCheckboxAlarm: ");
    }
}
