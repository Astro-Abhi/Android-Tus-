package com.astro.androidtuts;



import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.VideoView;

public class Video extends Activity implements OnClickListener {

	VideoView videoView;
	ImageButton play,pause;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video);
		
		videoView = (VideoView)findViewById(R.id.videoView1);
		play = (ImageButton)findViewById(R.id.imageButton1);
		pause = (ImageButton)findViewById(R.id.imageButton2);
		
		play.setOnClickListener(this);
		pause.setOnClickListener(this);
	}


	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.imageButton1:
			
				String path = "file:///android_asset/html/video/video.mp4";
				videoView.setVideoURI(Uri.parse(path));
				videoView.setMediaController(new MediaController(getApplicationContext()));
				videoView.requestFocus();
				videoView.start();
			break;
		case R.id.imageButton2:
			if (videoView.isPlaying()) {
				videoView.pause();
			}
		default:
			break;
		}
		
	}

}
