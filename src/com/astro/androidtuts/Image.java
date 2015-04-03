package com.astro.androidtuts;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Image extends Activity {

	
	ImageView View;
	Button button;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_view);
		 
		View= (ImageView) findViewById(R.id.image);
		button = (Button)findViewById(R.id.go);
		
		button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
			
				View.setImageResource(R.drawable.logo);
			
				
			}
		});
	}

}
