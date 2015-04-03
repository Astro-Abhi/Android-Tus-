package com.astro.androidtuts;


import java.util.HashMap;
import library.ConnectionDetector;
import library.DatabaseHandler;
import library.PreferenceData;
import library.UserFunctions;
import library.Utills;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.widget.LoginButton;
import com.facebook.widget.ProfilePictureView;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import android.net.Uri;
import android.os.Bundle;

import android.os.Vibrator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class Tutorials extends ActionBarActivity implements OnItemClickListener, OnItemLongClickListener {

	
	private DrawerLayout NavDrawer;
	private ListView NavList;
	String[] tuts_titles;
	ListView listView;
	TextView textView;
	private NavAdapter navAdapter;
	private ActionBarDrawerToggle actionBarDrawerToggle;
	private ProfilePictureView pictureView;
	private TextView FB_user;
	private LoginButton FB_logout;
	private int selected;
	private String selectedTut;
	private PreferenceData preferenceData = new PreferenceData();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
		setContentView(R.layout.activity_tutorial);
		final ConnectionDetector detector  = new ConnectionDetector(getApplicationContext());		
		Drawable drawable = new ColorDrawable(Color.TRANSPARENT);
		getSupportActionBar().setBackgroundDrawable(drawable);
	
				if (!detector.isConnectingToInternet()) {
					de.keyboardsurfer.android.widget.crouton.Configuration configuration;
					de.keyboardsurfer.android.widget.crouton.Configuration.Builder builder = new de.keyboardsurfer.android.widget.crouton.Configuration.Builder();
					configuration = builder.build();
				
					Crouton.showText(Tutorials.this, "No Internet Connection", Style.ALERT, 0, configuration);
				}
				
		Resources resources = getResources();
		tuts_titles =  resources.getStringArray(R.array.tuts_titles);
		NavDrawer = (DrawerLayout)findViewById(R.id.NavDrawer);
		NavList = (ListView)findViewById(R.id.DrawerList);
		View headerview = getLayoutInflater().inflate(R.layout.fb_info, null);
		View footerview = getLayoutInflater().inflate(R.layout.nav_bottom_slides, null);
		FB_logout = (LoginButton)headerview.findViewById(R.id.FB_logout);
		FB_logout.setSessionStatusCallback(new Session.StatusCallback() {
			
			@Override
			public void call(Session session, SessionState state, Exception exception) {
				
				if (session.isClosed()) {
					session.closeAndClearTokenInformation();
					UserFunctions functions = new UserFunctions();
					functions.logoutUser(getApplicationContext());
					Intent intent = new Intent(getApplicationContext(), Login.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					finish();
					IntentAnimation(intent);
				
				}
			
				
			}
		});
	
		pictureView = (ProfilePictureView)headerview.findViewById(R.id.PicPro);
		DatabaseHandler handler = new DatabaseHandler(getApplicationContext());
		HashMap<String, String> user_info = handler.getUserDetails();
		Typeface typefac = Typeface.createFromAsset(getAssets(), "fonts/ubuntu.ttf");
		pictureView.setPresetSize(ProfilePictureView.CUSTOM);
		pictureView.setProfileId(user_info.get("uid"));
		
		FB_user = (TextView)headerview.findViewById(R.id.FB_user);
		FB_user.setText(user_info.get("fname")+" "+user_info.get("lname"));
		FB_user.setTypeface(typefac);
		NavList.addHeaderView(headerview);
		NavList.addFooterView(footerview);
		navAdapter = new NavAdapter(this);
		NavList.setAdapter(navAdapter);
		NavList.setOnItemClickListener(new itemClicked());
		NavList.setScrollBarStyle(ListView.SCROLLBARS_INSIDE_OVERLAY);
	
		
		actionBarDrawerToggle = new ActionBarDrawerToggle(this, NavDrawer, R.drawable.ic_drawer, R.string.draw_open, R.string.draw_close){
			
			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				getSupportActionBar().setDisplayShowTitleEnabled(false);
				getSupportActionBar().hide();
				
			}
			
			@Override
			public void onDrawerClosed(View drawerView) {
				
				super.onDrawerClosed(drawerView);
				getSupportActionBar().setDisplayShowTitleEnabled(false);
				getSupportActionBar().show();
			}
			
			@Override
			public void onConfigurationChanged(Configuration newConfig) {
			
				super.onConfigurationChanged(newConfig);
				actionBarDrawerToggle.onConfigurationChanged(newConfig);
			}
		};
		NavDrawer.setDrawerListener(actionBarDrawerToggle);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
	

		
		
		
		textView = (TextView)findViewById(R.id.tut_t);
		Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/claritty.ttf");
		textView.setTypeface(typeface);
	
		
		listView = (ListView)findViewById(R.id.listView);
		TutorialAdapter adapter = new TutorialAdapter(getApplicationContext(), tuts_titles);
		listView.setAdapter(adapter);
		listView.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView arg0, int arg1) {
				
				
				
			}
			
			@Override
			public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
				
				if (listView.getScrollY() > 10) {
					
					getSupportActionBar().hide();
				}else if (listView.getScrollY() == 0) {
					getSupportActionBar().show();
				}
				
				
			}
		});
		listView.setOnItemClickListener(this);
		registerForContextMenu(listView);
		listView.setOnItemLongClickListener(this);
		
		
		View bottom_view = getLayoutInflater().inflate(R.layout.nav_bottom_slides, null);
		
		ImageButton FB = (ImageButton)bottom_view.findViewById(R.id.FBBtn);
		ImageButton GP = (ImageButton)bottom_view.findViewById(R.id.GoogleBtn);
		
		
		FB.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				Intent Fb;
				
				if (Utills.isAppInstalled(Utills.FACEBOOK, getApplicationContext())) {
					Fb = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/1446388482276814"));
				}else {
					Fb = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/codeprograming"));
				}
				
				
				
					
					Crouton.showText(Tutorials.this, "Please Wait..", Style.INFO);
					IntentAnimation(Fb);
				
				
			}
		});
		GP.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setClassName("com.google.android.apps.plus",
				"com.google.android.apps.plus.phone.UrlGatewayActivity");
				intent.putExtra("customAppUri", "100129798527109336470");
				startActivity(intent);
					
					Crouton.showText(Tutorials.this, "Please Wait..", Style.INFO);
					IntentAnimation(intent);
				
				
			}
		});
		
		
		
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		if (keyCode == KeyEvent.KEYCODE_MENU) {
			
			if (NavDrawer.isDrawerOpen(Gravity.LEFT)) {
				
				NavDrawer.closeDrawers();
				
			}else {
				NavDrawer.openDrawer(Gravity.LEFT);
			}
			
		}
		
		return super.onKeyDown(keyCode, event);
	}

	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		
		super.onCreateContextMenu(menu, v, menuInfo);
		
	
		menu.add("Social Share").setIcon(R.drawable.contextsocial);
		menu.add("Share To Other").setIcon(R.drawable.contextshare);;
		menu.setHeaderIcon(R.drawable.ic_launcher);
		menu.setHeaderTitle(selectedTut);
	
		
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem menus) {
		
		super.onContextItemSelected(menus);
		
		String name = menus.getTitle().toString();
		
		if (name == "Social Share") {
		
			Intent intent = new Intent(getApplicationContext(), ShareToSocial.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.putExtra("TutName", selected);
			IntentAnimation(intent);
		}
		return true;
		
	
	}
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
	
		
		super.onPostCreate(savedInstanceState);
		actionBarDrawerToggle.syncState();
		
	}
	
	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View view, int position,
			long id) {
		
			selected = position;
			selectedTut = tuts_titles[position];
			Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
			vibrator.vibrate(50);
			
		return false;
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
	public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
		
		int tuts = position;
		String tuts_name = tuts_titles[position];
		SharedPreferences preferences = getSharedPreferences("Intent_Data", MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		Intent gotuts = new Intent(getApplicationContext(), Learn.class);
		gotuts.putExtra("tut", tuts);
		gotuts.putExtra("name", tuts_name);
		editor.putString("name", tuts_name);
		editor.putInt("tut", tuts);
		editor.apply();
		gotuts.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		IntentAnimation(gotuts);
	
	
		
	}
	
	
	

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tutorials, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}else {
		if (item.getItemId() == R.id.action_settings) {
		
		}
		
		
		return super.onOptionsItemSelected(item);
		}
	}
	
	

private class itemClicked implements OnItemClickListener {

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		
		
		Intent Fb;
		
		if (Utills.isAppInstalled(Utills.FACEBOOK, getApplicationContext())) {
			Fb = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/1446388482276814"));
		}else {
			Fb = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/codeprograming"));
		}
		
		
		if (position == 2) {
			
			Crouton.showText(Tutorials.this, "Please Wait..", Style.INFO);
			IntentAnimation(Fb);
		}	
		
	if (position == 4) {
		Intent intent = new Intent(getApplicationContext(), Settings.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		IntentAnimation(intent);
	}
	if (position == 5) {
		Intent intent = new Intent(getApplicationContext(), About.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		IntentAnimation(intent);
	}
		
		NavList.setItemChecked(position, true);
		NavDrawer.closeDrawers();
		
	}
	
}
		
private class NavAdapter extends BaseAdapter {

	 Context context;
	 String[] NavTitle;
	
	 int[] images = {R.drawable.nav_pre,R.drawable.nav_like,R.drawable.nav_star,R.drawable.nav_setting,R.drawable.nav_us,R.drawable.nav_feedback};
	 private int lastPosition = -1;
	 
	
	public NavAdapter(Context context) {
		this.context = context;
		NavTitle = context.getResources().getStringArray(R.array.NavTitles);
		
	}
	
	@Override
	public int getCount() {
		return NavTitle.length;
	}

	@Override
	public Object getItem(int postiton) {
		
		return NavTitle[postiton];
	}

	@Override
	public long getItemId(int postiton) {

		return postiton;
	}

	
	@Override
	public View getView(int postiton, View convertView, ViewGroup parent) {
		View row= null;
	if (convertView == null) {
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		row = inflater.inflate(R.layout.nav_row, parent,false);
	}else {
		row = convertView;
	}
	 Animation animation = AnimationUtils.loadAnimation(context, (postiton > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
	TextView textView1 = (TextView)row.findViewById(R.id.textview1);
	textView1.setTextColor(Color.WHITE);
	Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/ubuntu.ttf");
	
	textView1.setTypeface(typeface);
	ImageView imageView1 =(ImageView)row.findViewById(R.id.imageview1);
	textView1.setText(NavTitle[postiton]);
	imageView1.setImageResource(images[postiton]);
	


	row.startAnimation(animation);
	lastPosition = postiton;

		return row;
	}
	
}


	
}

