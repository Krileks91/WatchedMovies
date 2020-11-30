package com.ftninformatika.moviesactors.Fragments;

import android.os.Bundle;

import androidx.preference.CheckBoxPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.ftninformatika.moviesactors.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    public static final String technicalDetails = "show_technical_details";
    public static final String watchers = "show_actors_details";

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.root_preferences);
        findPreference(technicalDetails).setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                return onChange(preference, newValue);
            }
        });

        findPreference(watchers).setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                return onChange(preference, newValue);
            }
        });
    }

    public boolean onChange(Preference preference, Object newValue) {
        String key = preference.getKey();
        if (key.equals(technicalDetails)) {
            //Reset other items
            CheckBoxPreference p = (CheckBoxPreference)findPreference(watchers);
            p.setChecked(false);
        }
        else if (key.equals(watchers)) {
            //Reset other items
            CheckBoxPreference p = (CheckBoxPreference)findPreference(technicalDetails);
            p.setChecked(false);
        }

        return (Boolean)newValue;
    }
}