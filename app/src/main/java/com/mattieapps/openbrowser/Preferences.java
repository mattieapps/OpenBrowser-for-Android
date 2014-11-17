package com.mattieapps.openbrowser;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by andrewmattie on 12/20/13.
 */
@SuppressWarnings("deprecation")
public class Preferences extends PreferenceActivity {

    public void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_Settings);
        super.onCreate(savedInstanceState);
        super.setTitle("Settings");
        addPreferencesFromResource(R.xml.settings);
    }
}