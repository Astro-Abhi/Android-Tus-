package com.astro.androidtuts;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;



public class TutorialAdapter extends ArrayAdapter<String> {

	 private int lastPosition = -1;
	String[] titleArray;
	Context context;
	

	TutorialAdapter(Context c,String[] titles){
		
		super(c,R.layout.single_row,R.id.tut_text,titles);
		this.titleArray = titles;
		this.context = c;
		
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		if (row==null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.single_row, null, false);
		}
		Animation animation = AnimationUtils.loadAnimation(context, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
		Typeface typeface= Typeface.createFromAsset(context.getAssets(), "fonts/ubuntu.ttf");
		TextView mytext = (TextView) row.findViewById(R.id.tut_text);
		mytext.setTypeface(typeface);
		mytext.setText(titleArray[position]);
		
		if (position % 2 == 0) {
			
			row.setBackgroundResource(R.drawable.red);
			
		}else {
			row.setBackgroundResource(R.drawable.blue);
		}
		row.setSoundEffectsEnabled(true);
		
		row.startAnimation(animation);
		lastPosition = position;
		return row;
		
	}
	
	

	
	
}
