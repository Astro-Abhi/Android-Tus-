package com.astro.androidtuts;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Edittext extends Activity implements OnClickListener {

	
	EditText editText1,editText2;
	Button button1,button2;
	TextView text;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_text);
		
		editText1 = (EditText)findViewById(R.id.URL);
		editText2 = (EditText)findViewById(R.id.EditText01);
		button1 = (Button)findViewById(R.id.go);
		button2 = (Button)findViewById(R.id.Button01);
		text = (TextView)findViewById(R.id.tut_t);
		button1.setOnClickListener(this);
		button2.setOnClickListener(this);
		
		
	}



	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.go:
			String input = editText1.getText().toString();
			text.setText(input);
			break;
		case R.id.Button01:
			String input2 = editText2.getText().toString();
			text.setText(input2);
	
		}
		
	}

}
