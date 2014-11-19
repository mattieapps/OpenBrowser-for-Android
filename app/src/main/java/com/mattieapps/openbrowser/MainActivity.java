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
import android.view.inputmethod.InputMethodManager;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

    Toolbar mToolbar;
    EditText mAddressEditText;
    Button mGoButton;
    WebView mWebView;
    ProgressBar mProgressBar;


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
        boolean incognitoCheckBox = sharedPreferences.getBoolean("privateBrowsingCheckBox", false);

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
            CharSequence text = "Private Browsing Mode Activated";
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

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        mWebView.setWebChromeClient(new WebChromeClient() {

            //This will be called on page loading progress

            @Override

            public void onProgressChanged(WebView view, int newProgress) {

                String title = mWebView.getTitle();

                super.onProgressChanged(view, newProgress);


                mProgressBar.setProgress(newProgress);

                if (newProgress == 100) {
                    mProgressBar.setVisibility(View.GONE);
                    if (title == null){
                        mToolbar.setTitle(R.string.app_name);
                    }else if (!title.equals(null)) {
                        mToolbar.setTitle(title + " - OpenBrowser");
                    }

                    CharSequence address = mWebView.getUrl();
                    if (!address.equals("file:///android_asset/index.html")){
                        mToolbar.setTitle(address);
                    }

                    if (address.equals("file:///android_asset/index_private.html")) {
                        mToolbar.setTitle(R.string.app_name);
                    } else {
                        mToolbar.setTitle(R.string.app_name);
                    }

                } else {
                    mProgressBar.setVisibility(View.VISIBLE);
                }
            }
        });

        //Keep Keyboard from auto popping up
        closeKeyboard();

        //GBS Functions
        mGoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = mAddressEditText.getText().toString();

                mWebView.loadUrl("http://" + url);
                closeKeyboard();
            }
        });

    }

    private void closeKeyboard() {
        InputMethodManager imm = (InputMethodManager)getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mAddressEditText.getWindowToken(), 0);
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
                mWebView.reload();
                return true;
            case R.id.stopButton:
                mWebView.stopLoading();
                Toast.makeText(getApplication(), "Stopping...", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.homeButton:
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                String homeURL = sharedPreferences.getString("homePagePref", "http://google.com");
                mWebView.loadUrl("http://" + homeURL);
                return true;
            case R.id.tabsButton:
                Toast.makeText(getApplication(), "Tabs feature coming soon!", Toast.LENGTH_SHORT).show();
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