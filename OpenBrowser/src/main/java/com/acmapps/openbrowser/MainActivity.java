package com.acmapps.openbrowser;

import android.support.v7.app.*;
import android.support.v4.app.*;
import android.view.*;
import android.os.*;
import android.webkit.*;

public class MainActivity extends ActionBarActivity {

    int version = Build.VERSION.SDK_INT ;
    
    mWebView = (WebView) findViewById(R.id.webView);
    mEditText = (EditText) findViewById(R.id.addressEditText);
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new DummyFragment())
                    .commit();
        }

        if (version >= Build.VERSION_CODES.ECLAIR){
            ActionBar actionBar = getSupportActionBar();
            actionBar.hide();
        }
        
        //WebView Code
        mWebView.getSettings.setJavaScriptEnabled(true);
        mWebView.setUrl("http://google.com");
    }
    
    public void goClick(View view){
        //Get URL from EditText
        mWebView.setUrl(mEditText.getText());
    }
    
    public void onLowMemory(){
        if(version <= Build.VERSION_CODES.JELLYBEAN_MR2){
            //Attemts to free memory from the webView
            mWebView.freeMemory();
        } else{
            return;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A dummy fragment containing a simple view.
     */
    public static class DummyFragment extends Fragment {

        public DummyFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

}
