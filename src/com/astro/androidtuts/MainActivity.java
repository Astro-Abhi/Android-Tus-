package com.astro.androidtuts;




import library.DatabaseHandler;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import android.content.Intent;
import android.graphics.Typeface;



public class MainActivity extends ActionBarActivity {

	private TextView Logo;
	private static int SPLASH_TIME_OUT = 2000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);
		
		Logo = (TextView)findViewById(R.id.ShimmerText);
		
		Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/ubuntu.ttf");
		Logo.setTypeface(typeface);
		new Handler().postDelayed(new Runnable() {
				
				@Override
				public void run() {
					
					DatabaseHandler databaseHandler = new DatabaseHandler(getApplicationContext());
					if (databaseHandler.getRowCount() == 0) {
						Intent intent = new Intent(getApplicationContext(), Tutorials.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
						finish();
						overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
					}else {
					Intent intent = new Intent(getApplicationContext(), Tutorials.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
					finish();
					overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
					
					}
					
				}
			},SPLASH_TIME_OUT);
		
		
	}

	
	
		
}
