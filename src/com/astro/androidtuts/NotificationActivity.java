package com.astro.androidtuts;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class NotificationActivity extends ActionBarActivity  {

	//Notification Id
	private static final int NOTFI_ID = 1;
	
	private NotificationManager notificationManager;
	
	private Notification notification;
	
	private final String website = "http://astrotuts.cu.cc";
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notification);
		
		Button notify = (Button)findViewById(R.id.notify);
		
		
		notify.setOnClickListener(new OnClickListener() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View arg0) {
			
				notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
				
				notification = new Notification(R.drawable.ic_launcher, "More Cool Tutorials", System.currentTimeMillis());
				
				
				Context context = getApplicationContext();
				
				String title = "More Cool Tutorials";
				
				String text = website;
				
				Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(website));
				
				PendingIntent pendingIntent = PendingIntent.getActivity(NotificationActivity.this, 0, intent, Intent.FLAG_ACTIVITY_NEW_TASK);
				
				notification.setLatestEventInfo(context, title, text, pendingIntent);
				
				notificationManager.notify(NOTFI_ID, notification);
				
			}
		});
		
	}


}
