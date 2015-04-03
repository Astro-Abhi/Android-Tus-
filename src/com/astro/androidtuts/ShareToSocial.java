package com.astro.androidtuts;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import library.ConnectionDetector;
import library.DatabaseHandler;
import library.FacebookFriendsDB;
import library.PreferenceData;
import library.Utills;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;
import com.facebook.FacebookRequestError;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Request.Callback;
import com.facebook.widget.ProfilePictureView;
import com.facebook.widget.ToolTipPopup;
import com.facebook.widget.WebDialog;
import com.facebook.RequestAsyncTask;
import com.facebook.Response;
import com.facebook.Session;
import com.google.android.gms.plus.PlusShare;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import de.keyboardsurfer.android.widget.crouton.Configuration;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

public class ShareToSocial extends ActionBarActivity {

	
	private TextView Title,Code;
	private ToggleButton CodeToShare;
	private String[] names;
	private int tutid;
	private String ToPost;
	private String[] tutname;
	private ImageButton fb,gp,tw;
	 static int TO_SHARE = 1;
	private Spinner spinner;
	 SharedPreferences preferencesTw;
	   ProgressDialog progress;
	   private ConnectionDetector detector;
		private PreferenceData preferenceData = new PreferenceData();
	static String TWITTER_CONSUMER_KEY = "wxNfCzfk0dgzG4QHZWnfAeSPp";
	 static String TWITTER_CONSUMER_SECRET = "rU1W2swmiFnIfbq3farHKLLLGy7LX4N5Rc5KKjvmFmo0m0xxDG";
	 private String SHARE;
	 private DrawerLayout PicFrnds;
	 private ListView SelectFrnds;
	  ArrayList<String> Frnds;
	 ArrayList<String> ids;
	 private ArrayList<String> selectedFrnds;
		private ActionBarDrawerToggle actionBarDrawerToggle;
		private Menu AccessMenu;
	@Override
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_share_to_fb);
		Drawable drawable = getResources().getDrawable(R.drawable.blur);
		getActionBar().setBackgroundDrawable(drawable);
		ConnectionDetector.StartNetworkServiceFor(ShareToSocial.this, getApplicationContext());
		
		Frnds = new ArrayList<String>();
		ids = new ArrayList<String>();
		selectedFrnds = new ArrayList<String>();
		final FacebookFriendsDB friendsDB = new FacebookFriendsDB(getApplicationContext());
		
	
	try {	
						
		//Log.i("res", response.getGraphObject().getInnerJSONObject().optString("data"));
			
			JSONObject data = new JSONObject(friendsDB.getFriendsDetails());
			JSONArray array = new JSONArray( data.optString("data"));
			Log.i("data", array.toString());
			
			for (int i = 0; i < array.length(); i++) {
				
				JSONObject jsonObject = array.getJSONObject(i);
				String name = jsonObject.getString("name");
				String id = jsonObject.getString("id");
				Frnds.add(name);
				ids.add(id);
				//Log.i("name", name);
			}
			
			}catch(Exception exception) {
				exception.printStackTrace();
			}
					

		PicFrnds = (DrawerLayout)findViewById(R.id.FrndDrawer);
		SelectFrnds = (ListView)findViewById(R.id.SelectFrnds);
		
actionBarDrawerToggle = new ActionBarDrawerToggle(ShareToSocial.this, PicFrnds, R.drawable.ic_drawer, R.string.draw_open, R.string.draw_close){
			
			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
			}
			
			@Override
			public void onDrawerClosed(View drawerView) {
			}
			
			@Override
			public void onConfigurationChanged(
			android.content.res.Configuration newConfig) {
				actionBarDrawerToggle.onConfigurationChanged(newConfig);
			super.onConfigurationChanged(newConfig);
			}
			
		};
		PicFrnds.setDrawerListener(actionBarDrawerToggle);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		Listadapter listAdapter = new Listadapter(getApplicationContext());
		View Header = getLayoutInflater().inflate(R.layout.sharetofrnd_top_header, null);
		SelectFrnds.addHeaderView(Header);
		
		final Button button = (Button)Header.findViewById(R.id.send_req);
		
		button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
			
				Bundle params = new Bundle();
			    params.putString("message", "Android Tuts+.. A new to learning");
			    WebDialog.RequestsDialogBuilder requestsDialog = new WebDialog.RequestsDialogBuilder(ShareToSocial.this, Session.getActiveSession(), params);       
			   requestsDialog.setTheme(0);
			   requestsDialog.setTitle("Code+");
			   WebDialog dialog = requestsDialog.build();
			   dialog.show();
			}
		});
		
		button.setOnLongClickListener(new View.OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				
				ToolTipPopup popup = new ToolTipPopup("Send Request To Your Friends To Use Android Tuts+ App.. So You Can Share Easily", button);
				popup.setStyle(com.facebook.widget.ToolTipPopup.Style.BLUE);
				popup.show();
				return false;
			}
		});
		
		SelectFrnds.setAdapter(listAdapter);
		SelectFrnds.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		
		
		
		
		detector  = new ConnectionDetector(getApplicationContext());
		
		if (!detector.isConnectingToInternet()) {
			
			Configuration configuration;
			Configuration.Builder builder = new Configuration.Builder();
			builder.setDuration(de.keyboardsurfer.android.widget.crouton.Configuration.DURATION_INFINITE);
			configuration = builder.build();
			Crouton.showText(ShareToSocial.this, "No Internet Connection", Style.ALERT, 0, configuration);
		}
		if (detector.isConnectingToInternet()) {
			Crouton.clearCroutonsForActivity(ShareToSocial.this);
		}

		 SharedPreferences preferences = getSharedPreferences("Google_data", MODE_PRIVATE);
		 preferencesTw = getSharedPreferences("Twitter_Data", MODE_PRIVATE);
		 
		 final String[] Share = {"Facebook","Google+","Twitter"};
		 
		spinner = (Spinner)findViewById(R.id.SocialTo);
		fb = (ImageButton)findViewById(R.id.facebook);
		gp = (ImageButton)findViewById(R.id.google);
		tw = (ImageButton)findViewById(R.id.Twitter);
		
		
		
		spinner.setPrompt("Share To");
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, Share);
		
		spinner.setAdapter(adapter);
		
		
		
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {


			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				Animation fadein = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
				Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.push_left_in);
				String get = spinner.getItemAtPosition(position).toString();
				
				if (get != "Select") {
					
					if (get == "Facebook") {
						
						if (fb.getVisibility() == View.INVISIBLE) {
							
							fb.startAnimation(fadein);
							fb.setVisibility(View.VISIBLE);
							spinner.setBackgroundResource(R.drawable.com_facebook_button_blue);
							spinner.startAnimation(animation);
							TO_SHARE = 1;
							gp.setVisibility(View.INVISIBLE);
							tw.setVisibility(View.INVISIBLE);
							MenuItem item = AccessMenu.findItem(R.id.ShareToWall);
							item.setTitle("Share To Wall");
							MenuItem item2 = AccessMenu.findItem(R.id.ShareToFrnd);
							item2.setVisible(true);
						}
						
					}
					if (get == "Google+") {
						
						if (gp.getVisibility() == View.INVISIBLE) {
							
							gp.startAnimation(fadein);
							gp.setVisibility(View.VISIBLE);
							spinner.setBackgroundResource(R.drawable.common_signin_btn_text_dark);
							spinner.startAnimation(animation);
							TO_SHARE = 2;
							fb.setVisibility(View.INVISIBLE);
							tw.setVisibility(View.INVISIBLE);
							MenuItem item = AccessMenu.findItem(R.id.ShareToWall);
							item.setTitle("Share To G+");
							MenuItem item2 = AccessMenu.findItem(R.id.ShareToFrnd);
							item2.setVisible(false);
					
								
							
						}
						
					}
					if (get == "Twitter") {
						
						if (tw.getVisibility() == View.INVISIBLE) {
							
							tw.startAnimation(fadein);
							tw.setVisibility(View.VISIBLE);
							spinner.setBackgroundColor(Color.parseColor("#4099FF"));
							spinner.startAnimation(animation);
							TO_SHARE = 3;
							gp.setVisibility(View.INVISIBLE);
							fb.setVisibility(View.INVISIBLE);
							MenuItem item = AccessMenu.findItem(R.id.ShareToWall);
							item.setTitle("Tweet");
							MenuItem item2 = AccessMenu.findItem(R.id.ShareToFrnd);
							item2.setVisible(false);
						}
						
					}
					
				}
	
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
				
			}
		});
		
		if (!preferences.contains("id") && preferences.getString("id", null) == null) {
			gp.setBackgroundResource(R.drawable.com_facebook_button_grey_pressed);
			gp.setEnabled(false);
		}
		if (!preferencesTw.contains("NAME") && preferencesTw.getString("NAME", null) == null) {
			tw.setBackgroundResource(R.drawable.com_facebook_button_grey_pressed);
			tw.setEnabled(false);
		}
		
		
		
		getSupportActionBar().setLogo(R.drawable.ic_launcher);
		getSupportActionBar().setTitle("Social Share");
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		Bundle bundle = getIntent().getExtras();
		tutid = bundle.getInt("TutName");
		names = getResources().getStringArray(R.array.load_tuts);
		tutname =getResources().getStringArray(R.array.tuts_titles);
		
		Title = (TextView)findViewById(R.id.TutTitle);
		CodeToShare = (ToggleButton)findViewById(R.id.CodeToShare);
		
		Code = (TextView)findViewById(R.id.Code);
		
		initAndroidCode();
		
		CodeToShare.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean toShow) {
				
				if (!toShow) {
					initAndroidCode();
					SHARE = "Android";
				}else {
					initXMLCode();
					SHARE = "XML";
				}
				
			}
		});
		
		Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/ubuntu.ttf");
		
		
		Title.setText(tutname[tutid]);
		Title.setTypeface(typeface);
		Code.setTypeface(typeface);
		
		
		
	}

	private void initAndroidCode() {
		InputStream path;
		try {
			path = getResources().getAssets().open("html/"+names[tutid]);
			Document document = Jsoup.parse(path, "UTF-8", " ");
			Elements code = document.select("#android pre");
			Code.setText(code.html());
			ToPost = code.html();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void initXMLCode() {
		InputStream path;
		try {
			path = getResources().getAssets().open("html/"+names[tutid]);
			Document document = Jsoup.parse(path, "UTF-8", " ");
			Elements code = document.select("#xml pre");
			Code.setText(code.html());
			ToPost = code.html();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		
		
	
		
		return super.onPrepareOptionsMenu(menu);
		
		
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		new MenuInflater(getApplicationContext()).inflate(R.menu.share_to_fb, menu);
		AccessMenu = menu;
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.getItemId() == R.id.home) {
			
			finish();
			overridePendingTransition(R.anim.fade_in, R.anim.push_right_out);
			
		}
		
		if (item.getItemId() == R.id.ShareToWall) {
			
			switch (TO_SHARE) {
			case 1:
				String iffrnds = "Yes";
				
				if (selectedFrnds.size() != 0) {
					int c = selectedFrnds.size();
					iffrnds = "Share To "+String.valueOf(c)+" Friends";
				}else {
					iffrnds = "Yes";
				}
				
				AlertDialog.Builder builder = new AlertDialog.Builder(ShareToSocial.this, AlertDialog.THEME_HOLO_LIGHT);
				builder.setCancelable(false)
				.setIcon(R.drawable.ic_launcher_web)
				.setMessage("Do Want Tag Your Friends To This Post..")
				.setPositiveButton(iffrnds, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						
					if (selectedFrnds.size() != 0) {
						String Tags =  StringUtil.join(selectedFrnds, ",");
						Bundle parameters = new Bundle();
						parameters.putString("tags", Tags);
						parameters.putString("place", "155021662189");
						parameters.putString("message", ToPost);
						parameters.putString("name", "Code+");
						parameters.putString("link", "https://www.facebook.com/codeprograming");
						Request request = new Request(Session.getActiveSession(), "me/feed", parameters, HttpMethod.POST, new Request.Callback() {
							
							@Override
							public void onCompleted(Response response) {
								
								if (response.getError() == null) {
									
									try {

										NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
										builder.setSmallIcon(R.drawable.ic_launcher_web);
										builder.setContentTitle("Android Tuts+");
										builder.setContentText(tutname[tutid]+" Posted To Wall");
										Uri defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
										builder.setSound(defaultSound);
										builder.setAutoCancel(true);
										builder.setLights(10, 10, 10);
										Intent Fb = null;
										JSONObject object = new JSONObject(response.getRawResponse());
										String id = object.optString("id");
										DatabaseHandler handler = new DatabaseHandler(getApplicationContext());
										HashMap<String, String> user_info = handler.getUserDetails();
										String uid = user_info.get("uid");
										
										if (Utills.isAppInstalled(Utills.FACEBOOK, getApplicationContext())) {
											Fb = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://post/"+id+"?owner="+uid));
										}else {
											Fb = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/post="+id));
										}
										PendingIntent pendingIntent =  PendingIntent.getActivity(getApplicationContext(), 0, Fb, 0);
										
										builder.setContentIntent(pendingIntent);

										NotificationManager manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

										manager.notify(001, builder.build());
										
										}
										catch(Exception exception) {
											exception.printStackTrace();
										}
										
									}
									
								}
								
							
						});
						
						RequestAsyncTask task = new RequestAsyncTask(request);
						task.execute();
					}else {
						PicFrnds.openDrawer(Gravity.LEFT);
					}
					}
				})
				.setNegativeButton("Cancel", null)
				.setNeutralButton("No", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						PostToWall();
						
					}
				});
				
				AlertDialog alertDialog = builder.create();
				alertDialog.show();
				break;
			case 2:
				PostToGoogle();
				break;
			case 3:
				new PostTweet().execute();
			}
	
		}
		if (item.getItemId() == R.id.ShareToFrnd) {
			
			Intent intent = new Intent(getApplicationContext(), ShareToFrnd.class);
			intent.putExtra("tutid", tutid);
			intent.putExtra("toshare", SHARE);
			IntentAnimation(intent);
			
			
		}
		
		
		
		return super.onOptionsItemSelected(item);
	}
	private void IntentAnimation(Intent intent) {
		if (preferenceData.isAnimation()) {
			startActivity(intent);
			 overridePendingTransition(R.anim.push_left_in, R.anim.fade_out);
			
		}else {
			startActivity(intent);
		}
	}
	
	@Override
	public void onBackPressed() {
		
		finish();
		overridePendingTransition(R.anim.fade_in, R.anim.push_right_out);
		
		super.onBackPressed();
	}
	
	private void PostToWall() {
		
		getSupportActionBar().setTitle("Posting..");
		Session session = Session.getActiveSession();
		Bundle parameters = new Bundle();
		parameters.putString("message", ToPost);
		parameters.putString("name", names[tutid]);
		Request request = new Request(session, "me/feed", parameters, HttpMethod.POST, new Callback() {
			
			@Override
			public void onCompleted(Response response) {
				
				FacebookRequestError error = response.getError();
				
				if (error ==null) {
					
					try {
					
					getSupportActionBar().setTitle("Share To Facebook");
					NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
					builder.setSmallIcon(R.drawable.ic_launcher);
					builder.setContentTitle("Facebook");
					builder.setContentText(tutname[tutid]+" Posted To Wall");
					Uri sound = Uri.parse("android:resource://"+getApplicationContext().getPackageName()+"/"+R.raw.tone);
					builder.setSound(sound);
					builder.setAutoCancel(true);
					builder.setLights(10, 10, 10);
					Intent Fb = null;
						JSONObject object = new JSONObject(response.getRawResponse());
						String id = object.optString("id");
					DatabaseHandler handler = new DatabaseHandler(getApplicationContext());
					HashMap<String, String> user_info = handler.getUserDetails();
					String uid = user_info.get("uid");
					
					if (Utills.isAppInstalled(Utills.FACEBOOK, getApplicationContext())) {
						Fb = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://post/"+id+"?owner="+uid));
					}else {
						Fb = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/post="+id));
					}
					PendingIntent pendingIntent =  PendingIntent.getActivity(getApplicationContext(), 0, Fb, 0);
					
					builder.setContentIntent(pendingIntent);
		
					NotificationManager manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
					manager.notify(001, builder.build());
					
					}
					catch(Exception exception) {
						exception.printStackTrace();
					}
					
				}
				
			}
		});
		
		RequestAsyncTask task = new RequestAsyncTask(request);
		task.execute();
		
	}
	
	public Boolean PostToGoogle() {
		
		  Intent shareIntent = new PlusShare.Builder(ShareToSocial.this)
          .setType("text/plain")
          .setText(ToPost)
          .setContentUrl(Uri.parse("http://www.codeprogramming.tk"))
          .getIntent();
       startActivityForResult(shareIntent, 0);
		
		return true;
		
	}
	
	
	 private class PostTweet extends AsyncTask<String, String, String>{
		 @Override
	        protected void onPreExecute() {
	                super.onPreExecute();
	                progress = new ProgressDialog(ShareToSocial.this);
	                progress.setMessage("Posting tweet ...");
	                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	                progress.setIndeterminate(true);
	                progress.show();
	        }
	           protected String doInBackground(String... args) {
	              ConfigurationBuilder builder = new ConfigurationBuilder();
	              builder.setOAuthConsumerKey(TWITTER_CONSUMER_KEY);
	              builder.setOAuthConsumerSecret(TWITTER_CONSUMER_SECRET);
	              AccessToken accessToken = new AccessToken(preferencesTw.getString("ACCESS_TOKEN", ""), preferencesTw.getString("ACCESS_TOKEN_SECRET", ""));
	              Twitter twitter = new TwitterFactory(builder.build()).getInstance(accessToken);
	              try {
	          twitter4j.Status response = twitter.updateStatus(ToPost);
	          return response.toString();
	        } catch (TwitterException e) {
	  
	          e.printStackTrace();
	        }
	           return null;
	           }
	           
	         
	           protected void onPostExecute(String res) {
	             if(res != null){
	               progress.dismiss();
	               NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
					builder.setSmallIcon(R.drawable.twitternav);
					builder.setContentTitle("Share To Twitter");
					builder.setContentText(tutname[tutid]+" Twitted");
					Uri defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
					builder.setSound(defaultSound);
					builder.setAutoCancel(true);
					builder.setLights(10, 10, 10);
					long[] pattern = {0,1200};
					builder.setVibrate(pattern);
	              
	             }else{
	               progress.dismiss();
	                 Crouton.showText(ShareToSocial.this, "Error Occured", Style.ALERT); 
	                   
	             }
	           }
	       }
	 
	 
	private class Listadapter extends ArrayAdapter<String> {

		Context context;
		 private int lastPosition = -1;
		public Listadapter(Context context) {
			super(context,R.layout.nav_frnd_selector,Frnds);
			this.context = context;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View row = convertView;
			if (row==null) {
				LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				row = inflater.inflate(R.layout.nav_frnd_selector, null, false);
			}
			Animation animation = AnimationUtils.loadAnimation(context, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
			Typeface typeface= Typeface.createFromAsset(context.getAssets(), "fonts/ubuntu.ttf");
			TextView mytext = (TextView) row.findViewById(R.id.NameText);
			ProfilePictureView pictureView = (ProfilePictureView)row.findViewById(R.id.profile);
			
			
			mytext.setTypeface(typeface);
			mytext.setText(Frnds.get(position));
			pictureView.setProfileId(ids.get(position));
			CheckBox box = (CheckBox)row.findViewById(R.id.tick);
			box.setTag(ids.get(position));
			
			
			box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton button, boolean checked) {
					
					if (checked) {
						
						selectedFrnds.add(button.getTag().toString());
						
					}else {
						selectedFrnds.remove(button.getTag().toString());
					}
					
					
				}
			});
			
			row.startAnimation(animation);
			lastPosition = position;
			return row;
			
			
		}
		
	}


	

}
