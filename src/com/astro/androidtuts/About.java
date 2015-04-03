package com.astro.androidtuts;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;


public class About extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		
		getSupportActionBar().hide();
		
		
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Intent intent = new Intent(getApplicationContext(), Tutorials.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		finish();
		startActivity(intent);
		overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_bottom);
	}

}
