package com.astro.androidtuts;



import library.AlertDialogManager;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class Twiiter extends ActionBarActivity {

	private static Twitter twitter;
	private static RequestToken requestToken;
	Dialog auth_dialog;
    WebView web;
    ProgressDialog  progress;
    Bitmap bitmap;
    String oauth_url,oauth_verifier;
    AccessToken accessToken;
    SharedPreferences pref;

	 static String TWITTER_CONSUMER_KEY = "wxNfCzfk0dgzG4QHZWnfAeSPp";
	 static String TWITTER_CONSUMER_SECRET = "rU1W2swmiFnIfbq3farHKLLLGy7LX4N5Rc5KKjvmFmo0m0xxDG";
	 
	

	     
	    // Alert Dialog Manager
	    AlertDialogManager alert = new AlertDialogManager();
	
	
	    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_twiiter);
		
		getSupportActionBar().hide();
		 twitter = new TwitterFactory().getInstance();
		 twitter.setOAuthConsumer(TWITTER_CONSUMER_KEY, TWITTER_CONSUMER_SECRET);
		  web = (WebView)findViewById(R.id.TwitterWeb);
		 new TokenGet().execute();
		 
		 pref = getSharedPreferences("Twitter_Data", MODE_PRIVATE);
		 
		 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.twiiter, menu);
		return true;
	}
	 private class TokenGet extends AsyncTask<String, String, String> {
	        @Override
	        protected String doInBackground(String... args) {
	         try {
	           requestToken = twitter.getOAuthRequestToken();
	           oauth_url = requestToken.getAuthorizationURL();
	      } catch (TwitterException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	      }
	                      return oauth_url;
	        }
	         @SuppressLint("SetJavaScriptEnabled")
			@Override
	         protected void onPostExecute(String oauth_url) {
	           if(oauth_url != null){
	         
	        	   Crouton.showText(Twiiter.this, oauth_url, Style.INFO);

	           
	             web.getSettings().setJavaScriptEnabled(true);
	             web.loadUrl(oauth_url);
	             web.setWebViewClient(new WebViewClient() {
	                   boolean authComplete = false;
	                   @Override
	                   public void onPageStarted(WebView view, String url, Bitmap favicon){
	                    super.onPageStarted(view, url, favicon);
	                   }
	                   @Override
	                   public void onPageFinished(WebView view, String url) {
	                       super.onPageFinished(view, url);
	                       if (url.contains("oauth_verifier") && authComplete == false){
	                         authComplete = true;
	                        Log.e("Url",url);
	                        Uri uri = Uri.parse(url);
	                           oauth_verifier = uri.getQueryParameter("oauth_verifier");
	                  
	                   new AccessTokenGet().execute();
	                   }else if(url.contains("denied")){
	                      
	                     
	                   }
	                       }
	               });
	           
	         }else{
	                 Toast.makeText(getApplicationContext(), "Sorry !, Network Error or Invalid Credentials", Toast.LENGTH_SHORT).show();
	         }
	         }
	    }
	 private class AccessTokenGet extends AsyncTask<String, String, Boolean> {
	      @Override
	        protected void onPreExecute() {
	            super.onPreExecute();
	            progress  = new ProgressDialog(Twiiter.this);
	            progress.setMessage("Fetching Data ...");
	            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	            progress.setIndeterminate(true);
	            progress.show();
	    }
	         @Override
	         protected Boolean doInBackground(String... args) {
	           try {
	           accessToken = twitter.getOAuthAccessToken(requestToken, oauth_verifier);
	           SharedPreferences.Editor edit = pref.edit();
	                 edit.putString("ACCESS_TOKEN", accessToken.getToken());
	                 edit.putString("ACCESS_TOKEN_SECRET", accessToken.getTokenSecret());
	                 User user = twitter.showUser(accessToken.getUserId());
	                 edit.putString("NAME", user.getName());
	                 edit.commit();
	      } catch (TwitterException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	      }
	                       return true;
	         }
	          @Override
	          protected void onPostExecute(Boolean response) {
	            if(response){
	                progress.dismiss();
	                new PostTweet().execute();
	            }
	            }
	          }
	 
	 private class PostTweet extends AsyncTask<String, String, String> {
	        @Override
	            protected void onPreExecute() {
	                super.onPreExecute();
	                progress =new ProgressDialog(Twiiter.this);
	                progress.setMessage("Posting tweet ...");
	                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	                progress.setIndeterminate(true);
	                progress.show();
	        }
	           protected String doInBackground(String... args) {
	              ConfigurationBuilder builder = new ConfigurationBuilder();
	              builder.setOAuthConsumerKey(TWITTER_CONSUMER_KEY);
	              builder.setOAuthConsumerSecret(TWITTER_CONSUMER_SECRET);
	              AccessToken accessToken = new AccessToken(pref.getString("ACCESS_TOKEN", ""), pref.getString("ACCESS_TOKEN_SECRET", ""));
	              Twitter twitter = new TwitterFactory(builder.build()).getInstance(accessToken);
	              try {
	          twitter4j.Status response = twitter.updateStatus("Hi Guys im using the new Code+ - Android Tuts+, A new way to learn android programming @Code_programing");
	          return response.toString();
	        } catch (TwitterException e) {
	          // TODO Auto-generated catch block
	          e.printStackTrace();
	        }
	           return null;
	           }
	           protected void onPostExecute(String res) {
	             if(res != null){
	               progress.dismiss();
	               Crouton.showText(Twiiter.this, "Twiiter Integrated Successfully", Style.CONFIRM);
	              
	             }else{
	               progress.dismiss();
	                  
	                   
	             }
	           }
	       }
	 
}
