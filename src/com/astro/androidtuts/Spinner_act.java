package com.astro.androidtuts;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.app.Activity;


public class Spinner_act extends Activity {

	
	Spinner spinner;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_spinner_act);
		
		spinner = (Spinner)findViewById(R.id.SocialTo);
		
		//Array for Spinner
		final String[] Social = {"Facebook","Twitter","Google+","Pininterst","Linkedin"};
		
		spinner.setPrompt("Social");
		
		//set the arrayadapter
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, Social);
		
		spinner.setAdapter(adapter);
		
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				String name = Social[position];
				Toast.makeText(getApplicationContext(), name, Toast.LENGTH_SHORT).show();
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
				
			}
		});
		
		
	}



}
