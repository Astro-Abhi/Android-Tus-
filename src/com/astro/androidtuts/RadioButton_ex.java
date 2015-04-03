package com.astro.androidtuts;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class RadioButton_ex extends Activity {

	RadioGroup group;
	Button button;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_radio_button_ex);
		
		group = (RadioGroup)findViewById(R.id.ShareTo);
		button = (Button)findViewById(R.id.go);
		
		button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
			
				int id = group.getCheckedRadioButtonId();
				RadioButton rbutton = (RadioButton)findViewById(id);
				String text = rbutton.getText().toString();
				Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
				
			}
		});
	}



}
