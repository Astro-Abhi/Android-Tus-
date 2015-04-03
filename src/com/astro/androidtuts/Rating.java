package com.astro.androidtuts;

import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.app.Activity;


public class Rating extends Activity {

	RatingBar ratingBar;
	TextView output;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rating);
		
		ratingBar = (RatingBar)findViewById(R.id.RateUs);
		output = (TextView)findViewById(R.id.FB_user);
		
		ratingBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
			
			@Override
			public void onRatingChanged(RatingBar arg0, float rating, boolean arg2) {
			
				output.setText("Rating : "+String.valueOf(rating));
				
			}
		});
		
		
	}

	

}
