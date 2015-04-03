package com.astro.androidtuts;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.ToggleButton;

public class Toggle extends Activity implements OnCheckedChangeListener {

	ToggleButton toggleButton;
	RelativeLayout layout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_toogle);
		
		layout =(RelativeLayout)findViewById(R.id.MainLearn);
		toggleButton = (ToggleButton)findViewById(R.id.toggleButton1);
		toggleButton.setOnCheckedChangeListener(this);
	}


	@Override
	public void onCheckedChanged(CompoundButton arg0, boolean b) {
		
		if (b) {
			layout.setBackgroundColor(Color.GRAY);
		}else {
			layout.setBackgroundColor(Color.WHITE);
		}
		
	}

}
