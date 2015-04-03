package com.astro.androidtuts;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class Checkbox extends Activity {

	CheckBox box;
	Button button;
	TextView textView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_checkbox);
		
		box = (CheckBox)findViewById(R.id.checkBox1);
		button = (Button)findViewById(R.id.go);
		textView = (TextView)findViewById(R.id.tut_t);
		
		button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
//				checkbox status
				if (box.isChecked()) {
					textView.setText("Checked");
				}else {
					textView.setText("Unchecked");
				}
				
			}
		});
		
	}


}
