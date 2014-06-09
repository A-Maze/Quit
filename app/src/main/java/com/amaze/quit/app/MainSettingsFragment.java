package com.amaze.quit.app;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.util.Log;

/**
 * Created by Robin on 9-6-2014.
 */
public class MainSettingsFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Can retrieve arguments from preference XML.
        Log.i("args", "Arguments: " + getArguments());

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.settings);
    }
}
