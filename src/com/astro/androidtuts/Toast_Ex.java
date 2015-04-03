package com.astro.androidtuts;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Toast_Ex extends Activity {

	EditText editText;
	Button button;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_toast__ex);
		
		editText = (EditText)findViewById(R.id.URL);
		button = (Button)findViewById(R.id.go);
		
		button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
			
				String input = editText.getText().toString();
				//Making Toast
				Toast.makeText(getApplicationContext(), input, Toast.LENGTH_LONG).show();
				
			}
		});
		
	}



}
