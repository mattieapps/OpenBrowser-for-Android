package com.mattieapps.openbrowser;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
@SuppressLint("SetJavaScriptEnabled")
public class MainActivity extends ActionBarActivity {

    int version = Build.VERSION.SDK_INT ;

    public WebView mWebView;
    //String homeURL = "http://google.com/";

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);







        final ProgressBar loadingProgressBar;

        mWebView = (WebView) findViewById(R.id.webView);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        boolean incognitoCheckBox = sharedPreferences.getBoolean("incognitoCheckBox", false);

        if (incognitoCheckBox == true){
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

        if (version >= Build.VERSION_CODES.HONEYCOMB){
            mWebView.getSettings().setDisplayZoomControls(false);
        }

        mWebView.setWebViewClient(new OBWebViewClient());

//        loadingProgressBar = (ProgressBar) findViewById(R.id.progressBar);
//
//        mWebView.setWebChromeClient(new WebChromeClient() {
//
//            //This will be called on page loading progress
//
//            @Override
//
//            public void onProgressChanged(WebView view, int newProgress) {
//
//                TextView mTextView = (TextView) findViewById(R.id.urlTitleText);
//                EditText mEditText = (EditText) findViewById(R.id.addressText);
//                String title = mWebView.getTitle();
//
//                super.onProgressChanged(view, newProgress);
//
//
//                loadingProgressBar.setProgress(newProgress);
//
//                if (newProgress == 100) {
//                    loadingProgressBar.setVisibility(View.GONE);
//                    mTextView.setVisibility(View.VISIBLE);
//                    if (title == null){
//                        mTextView.setText("OpenBrowser");
//                    }else if (!title.equals(null)) {
//                        mTextView.setText(title + " - OpenBrowser");
//                    }
//
//                    CharSequence address = mWebView.getUrl();
//                    if (!address.equals("file:///android_asset/index.html")){
//                        mEditText.setText(address);
//                    }
//
//                    if (address.equals("file:///android_asset/index_private.html")) {
//                        mEditText.setText("");
//                    } else {
//                        mEditText.setText("");
//                    }
//                    mTextView.setVisibility(View.VISIBLE);
//
//                } else {
//                    loadingProgressBar.setVisibility(View.VISIBLE);
//                    mTextView.setVisibility(View.INVISIBLE);
//
//                }
//            }
//        });

        //Keep Keyboard from auto popping up
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    private class OBWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            view.loadUrl(url);
            return true;
        }
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
}