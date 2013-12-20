package com.acmapps.openbrowser;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.*;
import android.app.ActionBar;
import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.view.*;
import android.os.*;
import android.webkit.*;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("SetJavaScriptEnabled")
public class MainActivity extends Activity {

    int version = Build.VERSION.SDK_INT ;

    private WebView mWebView;
    String homeURL = "http://google.com/";

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_layout);

        final ProgressBar loadingProgressBar;

        mWebView = (WebView) findViewById(R.id.webView);
        mWebView.loadUrl("file:///android_asset/index.html");

        //webView Settings
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().supportZoom();
        mWebView.getSettings().setBuiltInZoomControls(true);
        if (version >= Build.VERSION_CODES.HONEYCOMB){
            mWebView.getSettings().setDisplayZoomControls(false);
        }

        mWebView.setWebViewClient(new OBWebViewClient());

        loadingProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        mWebView.setWebChromeClient(new WebChromeClient() {

            // this will be called on page loading progress

            @Override

            public void onProgressChanged(WebView view, int newProgress) {

                TextView mTextView = (TextView) findViewById(R.id.urlTitleText);
                EditText mEditText = (EditText) findViewById(R.id.addressText);
                String title = mWebView.getTitle();

                super.onProgressChanged(view, newProgress);


                loadingProgressBar.setProgress(newProgress);

                if (newProgress == 100) {
                    loadingProgressBar.setVisibility(View.GONE);
                    mTextView.setVisibility(View.VISIBLE);
                    if (title == null){
                        mTextView.setText("OpenBrowser");
                    }else if (!title.equals(null)) {
                        mTextView.setText(title + " - OpenBrowser");
                    }

                    CharSequence address = mWebView.getUrl();
                    if (!address.equals("file:///android_asset/index.html")){
                        mEditText.setText(address);
                    }
                    mTextView.setVisibility(View.VISIBLE);

                } else {
                    loadingProgressBar.setVisibility(View.VISIBLE);
                    mTextView.setVisibility(View.INVISIBLE);

                }
            }
        });

        hideActionBar();

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

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void hideActionBar(){
        if (version >= Build.VERSION_CODES.HONEYCOMB){
            ActionBar actionBar = getActionBar();
            if (actionBar != null) {
                actionBar.hide();
            }
        }
    }

    @SuppressWarnings("ConstantConditions")
    public void goClick(View view){
        //Get URL from EditText
        mWebView = (WebView) findViewById(R.id.webView);
        EditText mEditText = (EditText) findViewById(R.id.addressText);

        String url = mEditText.getText().toString();

        mWebView.loadUrl("http://" + url);
    }

    public void backButton(View view){
        mWebView.goBack();
    }

    public void fwdButton(View view){
        mWebView.goForward();
    }

    public void stopButton(View view){
        mWebView.stopLoading();

        Context context = getApplicationContext();
        CharSequence text = "Stopping Page Load";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public void refreshButton(View view){
        mWebView.reload();
    }

    public void homeButton(View view){
        mWebView.loadUrl(homeURL);
    }

    public void showPopup(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.popup_actions, popup.getMenu());
        popup.show();
    }

    @SuppressWarnings("deprecation")
    public void onLowMemory(){
        WebView mWebView = (WebView) findViewById(R.id.webView);

        if(version <= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1 ){
            //Attemts to free memory from the webView
            mWebView.freeMemory();
        }
    }
}