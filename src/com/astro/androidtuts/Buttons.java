package com.astro.androidtuts;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Buttons extends Activity {

	TextView textView;
	Button button;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_buttons);
		
		textView = (TextView)findViewById(R.id.tut_t);
		button = (Button)findViewById(R.id.go);
		textView.setText("Button");
		button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				textView.setText("Button Clicked...!");
			}
		});
	}



}
