package com.astro.androidtuts;



import java.util.Arrays;
import java.util.Locale;
import com.facebook.FacebookRequestError;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.RequestAsyncTask;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.Request.Callback;
import com.facebook.Session.StatusCallback;
import com.facebook.model.GraphUser;
import com.facebook.widget.FacebookDialog;
import com.facebook.widget.LoginButton;

import library.DatabaseHandler;
import android.os.Bundle;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.TextView;








public class Login extends ActionBarActivity implements TextToSpeech.OnInitListener  {


	
	TextToSpeech speech;
	private SharedPreferences preferences;
	TextView text;
	private LoginButton fblogin;
	private UiLifecycleHelper helper;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
	
		speech = new TextToSpeech(this, this);
		
		helper = new UiLifecycleHelper(this, null);
		helper.onCreate(savedInstanceState);
		
		preferences = getPreferences(MODE_PRIVATE);
		final SharedPreferences.Editor editor = preferences.edit();
	

		getSupportActionBar().hide();
		
		fblogin = (LoginButton)findViewById(R.id.facebook);
		
		 
		
		fblogin.setReadPermissions(Arrays.asList("public_profile","email","user_birthday","user_friends"));
		
		fblogin.setSessionStatusCallback(new StatusCallback() {
			
		
			
			@Override
			public void call(final Session session, SessionState state, Exception exception) {
				
				if (session.isOpened() && session != null) {
					
					
					
					Request request = Request.newMeRequest(session, new Request.GraphUserCallback() {
						
						@Override
						public void onCompleted(GraphUser user, Response response) {
							
							if (session == Session.getActiveSession()) {
								
								if (user != null) {
								editor.putString("access_token", session.getAccessToken());
								editor.putString("expires", session.getExpirationDate().toString());
								editor.commit();
								String id = user.getId();
								String email = user.getProperty("email").toString();
								String fname = user.getFirstName();
								String lname = user.getLastName();
								String username = user.getUsername();
								
								DatabaseHandler databaseHandler = new DatabaseHandler(getApplicationContext());
								databaseHandler.addUser(fname, lname, email, username, id);
								

									publishFeedDialog();
																
								
								
								}
								if (response.getError() != null) {
									
								}
								
							}
							
						}
					});
				request.executeAsync();	
					
				}
				
			}
			
			
		});
		
		text = (TextView)findViewById(R.id.text);
		Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/claritty.ttf");
		text.setTypeface(typeface);
		
		
		
		

	}
	
	private void publishFeedDialog() {
	    Bundle params = new Bundle();
	    params.putString("name", "Code+ ");
	    params.putString("caption", "Best Tutorials To Build Your Development Abilities.");
	    params.putString("description", "Learn in a new way by using our Android Tuts+ app");
	    params.putString("link", "https://facebook.com/codeprogramming");
	   
	    Session session = Session.getActiveSession();
	    Request request = new Request(session, "me/feed", params, HttpMethod.POST, new Callback() {
			
			@Override
			public void onCompleted(Response response) {
				
				FacebookRequestError error = response.getError();
				
				if (error ==null) {
					
					Intent tutorials = new Intent(Login.this, Tutorials.class);
					tutorials.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					finish();
					startActivity(tutorials);
					overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_bottom);
				}
				
			}
		});
		
		RequestAsyncTask task = new RequestAsyncTask(request);
		task.execute();
					
	}
	
	@Override

	 public void onActivityResult(int requestCode, int resultCode, Intent data) {

	     super.onActivityResult(requestCode, resultCode, data);

	     Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);

	     
	     helper.onActivityResult(requestCode, resultCode, data, new FacebookDialog.Callback() {
	         @Override
	         public void onError(FacebookDialog.PendingCall pendingCall, Exception error, Bundle data) {
	             Log.e("Activity", String.format("Error: %s", error.toString()));
	         }

	         @Override
	         public void onComplete(FacebookDialog.PendingCall pendingCall, Bundle data) {
	             Log.i("Activity", "Success!");
	         }
	     });
	     
	 }
	
	@Override
	protected void onResume() {
	    super.onResume();
	    helper.onResume();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
	    super.onSaveInstanceState(outState);
	    helper.onSaveInstanceState(outState);
	}

	@Override
	public void onPause() {
	    super.onPause();
	    helper.onPause();
	}
	
	@Override
	protected void onDestroy() {
		
		super.onDestroy();
		if (speech != null) {
			speech.stop();
			speech.shutdown();
		}
		helper.onDestroy();
	}
	
	@Override
	public void onInit(int status) {
		
		if (status == TextToSpeech.SUCCESS) {
			
			int result = speech.setLanguage(Locale.US);
			if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
				
			}else {
				
			}
			
		}
		
	}


	
	
}

