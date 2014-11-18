package com.mattieapps.openbrowser;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

    Toolbar mToolbar;
    EditText mAddressEditText;
    Button mGoButton;
    WebView mWebView;

    //String homeURL = "http://google.com/";

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGoButton = (Button) findViewById(R.id.goBtn);
        mAddressEditText = (EditText) findViewById(R.id.addressEditText);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);


        mWebView = (WebView) findViewById(R.id.webView);
        mWebView.setWebViewClient(new OBWebViewClient());


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        boolean incognitoCheckBox = sharedPreferences.getBoolean("incognitoCheckBox", false);

        if (incognitoCheckBox){
            mWebView.loadUrl("file:///android_asset/index_private.html");

            //webView Settings
            mWebView.getSettings().setJavaScriptEnabled(true);
            mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
            mWebView.getSettings().setSupportZoom(true);
            mWebView.getSettings().supportZoom();
            mWebView.getSettings().setBuiltInZoomControls(true);
            mWebView.getSettings().setSaveFormData(false);

            CookieManager.getInstance().setAcceptCookie(false);
            mWebView.getSettings().setCacheMode(mWebView.getSettings().LOAD_NO_CACHE);
            mWebView.getSettings().setAppCacheEnabled(false);

            Context context = getApplicationContext();
            CharSequence text = "Incognito Mode Activated";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        } else {
            mWebView.loadUrl("file:///android_asset/index.html");

            //webView Settings
            mWebView.getSettings().setJavaScriptEnabled(true);
            mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
            mWebView.getSettings().setSupportZoom(true);
            mWebView.getSettings().supportZoom();
            mWebView.getSettings().setBuiltInZoomControls(true);
            mWebView.getSettings().setSaveFormData(true);

            CookieManager.getInstance().setAcceptCookie(true);
            mWebView.getSettings().setCacheMode(mWebView.getSettings().LOAD_DEFAULT);
            mWebView.getSettings().setAppCacheEnabled(true);
        }

        mWebView.getSettings().setDisplayZoomControls(false);

        //Keep Keyboard from auto popping up
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //GBS Functions
        mGoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = mAddressEditText.getText().toString();

                mWebView.loadUrl("http://" + url);
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.settings:
                Intent intent = new Intent(this, Preferences.class);
                startActivity(intent);
                return true;
            case R.id.refreshButton:

                return true;
            case R.id.stopButton:

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class OBWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}