package com.astro.androidtuts;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

public class Imagebutton extends Activity {

	ImageButton button;
	RelativeLayout layout;
	int set = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_imagebutton);
		
		button =(ImageButton)findViewById(R.id.imageButton1);
		layout =(RelativeLayout)findViewById(R.id.change);
		
		button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				if (set == 0) {
					layout.setBackgroundColor(Color.WHITE);
					set = 1;
				}else {
					String color = "#ececec";
					layout.setBackgroundColor(Color.parseColor(color));
					set = 0;
				}
				
			}
		});
		
	}



}
