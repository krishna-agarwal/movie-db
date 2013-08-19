package com.example.my_movie_db;

import android.webkit.WebView;
import android.webkit.WebViewClient;

public class OurViewClient extends WebViewClient {
	@Override
	public boolean shouldOverrideUrlLoading(WebView v, String data){
		v.loadUrl(data);
	  //v.loadData(data, "text/html", null);
		return true;
	}

}
