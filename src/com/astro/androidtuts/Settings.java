package com.astro.androidtuts;






import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;

import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.PlusShare;
import com.google.android.gms.plus.model.people.Person;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

import library.AlertDialogManager;
import library.ConnectionDetector;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.IntentSender.SendIntentException;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.util.Log;






public class Settings extends PreferenceActivity implements OnSharedPreferenceChangeListener ,ConnectionCallbacks, OnConnectionFailedListener {

	 private static final int RC_SIGN_IN = 0;
	    // Logcat tag
	    private static final String TAG = "MainActivity";
	    
	   
	    // Google client to interact with Google API
	    private GoogleApiClient mGoogleApiClient;
	 
	    /**
	     * A flag indicating that a PendingIntent is in progress and prevents us
	     * from starting further intents.
	     */
	    private boolean mIntentInProgress;
	 
	    private boolean mSignInClicked;
	 
	    private ConnectionResult mConnectionResult;

	private static final String KEY_ANIM = "isAnim";
	private static final String KEY_GOOGLE = "isGoogle+";
	private static final String KEY_TWITTER = "isTwiiter";
	private static final String KEY_NOTI_FB = "isNotifi";
	private static final String KEY_NOTI_TWITTER = "isNotifiTwit";
	private static final String KEY_NOTI_TONE = "isNotifiTone";
	private static final String KEY_POSTING = "posting";
	private static final String KEY_EXTRA = "extraText";
	private static final String KEY_OFFLINE = "isoffline";
	
	
	public static  Boolean isAnim = true;
	public static  Boolean isGoogle = false;
	public static  Boolean isTwitter = false;
	public static  Boolean isNotifyFB = true;
	public static  Boolean isNotifyTwitter = true;
	public static  Boolean isNotifiTone = true;
	public static  String posting = "Any";
	public static  String extraText = null;
	public static  Boolean isoffline = true;
	
	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		 // Initializing google plus api client
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).addApi(Plus.API, null)
                .addScope(Plus.SCOPE_PLUS_LOGIN).build();
        Drawable drawable = getResources().getDrawable(R.drawable.blur);
		getActionBar().setBackgroundDrawable(drawable);
        
		addPreferencesFromResource(R.xml.settings);
		
	}

	
	
	@Override
	public void onSharedPreferenceChanged(final SharedPreferences preferences, String key) {
		
		if (key.equals(KEY_ANIM)) {
			
			isAnim = preferences.getBoolean(KEY_ANIM, true);
		}
		if (key.equals(KEY_GOOGLE)) {
			
			isGoogle = preferences.getBoolean(KEY_GOOGLE, false);
			
			if (isGoogle) {
				
				if (!mGoogleApiClient.isConnecting()) {
			        mSignInClicked = true;
			        resolveSignInError();
			    }
				
			}
			else {
				Builder builder = new Builder(Settings.this);
				builder.setTitle("Google +");
				builder.setIcon(R.drawable.ic_launcher);
				builder.setMessage("Do Want To Logout Google + Account From This App");
				builder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						signOutFromGplus();
					SharedPreferences.Editor editor = preferences.edit();
					editor.putBoolean(KEY_GOOGLE, false);
					editor.apply();	
					SharedPreferences spreferences  = getSharedPreferences("Google_Data", MODE_PRIVATE);
					SharedPreferences.Editor editor2 = spreferences.edit();
					editor2.clear();
					editor2.apply();
					
					}
				});
				builder.setCancelable(false);
				AlertDialog dialog = builder.create();
				dialog.show();
				
			}
		}
		if (key.equals(KEY_TWITTER)) {
			
			@SuppressWarnings("deprecation")
			Preference pr = findPreference(key);
			SharedPreferences sharedPreferences = getSharedPreferences("Twitter_Data", MODE_PRIVATE);
			String Name = sharedPreferences.getString("Name", null);
			if (Name != null) {
				pr.setSummary(Name);
			}
			isTwitter = preferences.getBoolean(KEY_TWITTER, false);
			
			if (isTwitter) {
				 ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
				 
			        AlertDialogManager alert = new AlertDialogManager();
					// Check if Internet present
			        if (!cd.isConnectingToInternet()) {
			            // Internet Connection is not present
			            alert.showAlertDialog(Settings.this, "Internet Connection Error",
			                    "Please connect to working Internet connection", false);
			            // stop executing code by return
			            return;
			        }
			        	Intent intent = new Intent(getApplicationContext(), Twiiter.class);
			        	startActivity(intent);
			        	overridePendingTransition(R.anim.push_left_in, R.anim.fade_out);
			         
			       
			}
			
				
			 
			        
			       
			}
		if (key.equals(KEY_NOTI_FB)) {
			
			isNotifyFB = preferences.getBoolean(KEY_NOTI_FB, true);
		}
		if (key.equals(KEY_NOTI_TWITTER)) {
			
			isNotifyTwitter = preferences.getBoolean(KEY_NOTI_TWITTER, true);
		}
		if (key.equals(KEY_NOTI_TONE)) {
			
			isNotifiTone = preferences.getBoolean(KEY_NOTI_TONE, true);
		}
		if (key.equals(KEY_POSTING)) {
			
			posting = preferences.getString(KEY_POSTING, "Any");
			
		}
		if (key.equals(KEY_EXTRA)) {
			
			extraText = preferences.getString(KEY_EXTRA, null);
			
		}
		if (key.equals(KEY_OFFLINE)) {
			
			isoffline = preferences.getBoolean(KEY_OFFLINE, true);
		}
		
		
			
		}

	
		
	/**
	 * Method to resolve any signin errors
	 * */
	private void resolveSignInError() {
	    if (mConnectionResult.hasResolution()) {
	        try {
	            mIntentInProgress = true;
	            mConnectionResult.startResolutionForResult(this, RC_SIGN_IN);

	            getProfileInformation();

	    	 
	        } catch (SendIntentException e) {
	            mIntentInProgress = false;
	            mGoogleApiClient.connect();
	        }
	    }
	}
	
	@Override
	public void onConnectionFailed(ConnectionResult result) {
	    if (!result.hasResolution()) {
	        GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this,
	                0).show();
	        return;
	    }
	 
	    if (!mIntentInProgress) {
	        // Store the ConnectionResult for later usage
	        mConnectionResult = result;
	 
	        if (mSignInClicked) {
	            // The user has already clicked 'sign-in' so we attempt to
	            // resolve all
	            // errors until the user is signed in, or they cancel.
	            resolveSignInError();
	        }
	    }
	 
	}
	@Override
	protected void onActivityResult(int requestCode, int responseCode,
	        Intent intent) {
	    if (requestCode == RC_SIGN_IN) {
	        if (responseCode != RESULT_OK) {
	            mSignInClicked = false;
	        }
	 
	        mIntentInProgress = false;
	 
	        if (!mGoogleApiClient.isConnecting()) {
	            mGoogleApiClient.connect();
	        }
	    }
	}
	 
	@Override
	public void onConnected(Bundle arg0) {
	    mSignInClicked = false;
	}
	 
	private void getProfileInformation() {
		
		    try {
		        if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
		            Person currentPerson = Plus.PeopleApi
		                    .getCurrentPerson(mGoogleApiClient);
		            String personName = currentPerson.getDisplayName();
		            String personPhotoUrl = currentPerson.getImage().getUrl();
		            String personGooglePlusProfile = currentPerson.getUrl();
		            String email = Plus.AccountApi.getAccountName(mGoogleApiClient);
		            String id = currentPerson.getId();
		            
		            SharedPreferences preferences = getSharedPreferences("Google_data", MODE_PRIVATE);
		            SharedPreferences.Editor editor = preferences.edit();
		            editor.putString("Name", personName);
		            editor.putString("id", id);
		            editor.putString("email", email);
		            editor.apply();
		            
		            Log.e(TAG, "Name: " + personName + ", plusProfile: "
		                    + personGooglePlusProfile + ", email: " + email
		                    + ", Image: " + personPhotoUrl);
		 
		          
		 
		            Intent shareIntent = new PlusShare.Builder(Settings.this)
	                  .setType("text/plain")
	                  .setText("A new Way To Learn Android Programming")
	                  .setContentUrl(Uri.parse("http://www.codeprogramming.tk"))
	                  .getIntent();
	               startActivityForResult(shareIntent, 0);
	               Crouton.showText(Settings.this, "Google+ Successfully Integrated", Style.INFO);
		           
		 
		        } else {
		            
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		
		
	}

	/**
	 * Sign-out from google
	 * */
	private void signOutFromGplus() {
	    if (mGoogleApiClient.isConnected()) {
	        Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
	        mGoogleApiClient.disconnect();
	        mGoogleApiClient.connect();
	      
	    }
	}

	@Override
	public void onConnectionSuspended(int arg0) {
	    mGoogleApiClient.connect();
	   
	}
	 

	 protected void onStart() {
	        super.onStart();
	        mGoogleApiClient.connect();
	    }
	 
	    protected void onStop() {
	        super.onStop();
	        if (mGoogleApiClient.isConnected()) {
	            mGoogleApiClient.disconnect();
	        }
	    }
	 

	

	
	@SuppressWarnings("deprecation")
	@Override
	protected void onResume() {

		super.onResume();
		
		getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onPause() {
		super.onPause();
		getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
	}



	
	     }
	    

	
	

