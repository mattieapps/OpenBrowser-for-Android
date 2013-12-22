package com.acmapps.openbrowser;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by andrewmattie on 12/20/13.
 */
@SuppressWarnings("deprecation")
public class Preferences extends PreferenceActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setTitle("Settings");
        addPreferencesFromResource(R.xml.settings);
    }
}