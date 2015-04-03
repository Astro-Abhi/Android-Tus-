package com.astro.androidtuts;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;



public class WebActivity extends ActionBarActivity {

	WebView preview;
	Button go;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web);
		
		
		preview = (WebView)findViewById(R.id.preview);
	
		go =(Button)findViewById(R.id.browse);
		
		
		go.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
			
				preview.loadUrl("http://wwww.facebook.com/astromj");
				preview.getSettings().setJavaScriptEnabled(true);
			}
		});
		
		
	}


}
