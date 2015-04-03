package com.astro.androidtuts;






import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import library.FacebookFriendsDB;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.RequestAsyncTask;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.FacebookDialog;
import com.facebook.widget.ProfilePictureView;
import com.facebook.widget.WebDialog;






import android.os.Build;
import android.os.Bundle;

import android.annotation.TargetApi;
import android.app.AlertDialog;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class ShareToFrnd extends ActionBarActivity implements OnItemClickListener {

	
	
	 private ProgressDialog nDialog;
	 ArrayList<String> Friend_name;
	 ArrayList<String> Friend_id;
	 private ListView FriendsLists;
	 FacebookFriendsDB friendsDb;
	 private int tutId;

	 private UiLifecycleHelper lifecycleHelper;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_share_to_frnd);
		Drawable drawable = getResources().getDrawable(R.drawable.blur);
		getActionBar().setBackgroundDrawable(drawable);
		
		lifecycleHelper = new UiLifecycleHelper(ShareToFrnd.this, null);
		lifecycleHelper.onCreate(savedInstanceState);
		
		Bundle bundle = getIntent().getExtras();
		tutId = bundle.getInt("tutid");
		
	
		
		
		
		Session session = Session.getActiveSession();
		
		Friend_name = new ArrayList<String>();
		Friend_id = new ArrayList<String>();
		friendsDb = new FacebookFriendsDB(getApplicationContext());
	
		FriendsLists = (ListView) findViewById(R.id.FriendsList);
		FriendsLists.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		registerForContextMenu(FriendsLists);

		nDialog = new ProgressDialog(ShareToFrnd.this, ProgressDialog.STYLE_SPINNER);
		nDialog.setMessage("Fetching Friends List...");
		nDialog.setCancelable(false);
		nDialog.show();
		
		
		
		Bundle params = new Bundle();

		params.putString("fields", "id,name, installed");
	
		Request request = new Request(session, "me/friends", params, HttpMethod.GET, new Request.Callback() {
			
			@Override
			public void onCompleted(Response response) {
				
				if (response.getError() == null) {
					
					//Log.i("response", response.getRawResponse());
					
					if (friendsDb.getFriendsCount() == 0) {
						
							
							
							/*JSONObject object = new JSONObject(response.getRawResponse());
							String json = object.optString("data");
							JSONArray array = new JSONArray(json);*/
							nDialog.setMessage("Storing Data For Futhure Use...");
							friendsDb.addFriends(response.getRawResponse());
						
					}
					

						if (friendsDb.getFriendsCount() != 0) {
							
							try {
							String json = friendsDb.getFriendsDetails();
							JSONObject object = new JSONObject(json);
							String data = object.optString("data");
							JSONArray array = new JSONArray(data);
							nDialog.setMessage("Creating UI..");
							for (int i = 0; i < array.length(); i++) {
								
								JSONObject jsonObject = array.getJSONObject(i);
								String name = jsonObject.getString("name");
								String id = jsonObject.getString("id");
								Friend_name.add(name);
								Friend_id.add(id);
							}
							}
							catch(Exception exception){
								exception.printStackTrace();
							}
							Adapter adapter = new Adapter(getApplicationContext());
							
							FriendsLists.setAdapter(adapter);
							
						}

					nDialog.dismiss();
				}else{
					Toast.makeText(getApplicationContext(), response.getError().toString(), Toast.LENGTH_SHORT).show();
				}
				
			}
		});
		
		RequestAsyncTask asyncTask = new RequestAsyncTask(request);
		asyncTask.execute();
		
		FriendsLists.setOnItemClickListener(this);
		
	}
	
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);

	    lifecycleHelper.onActivityResult(requestCode, resultCode, data, new FacebookDialog.Callback() {
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
	    lifecycleHelper.onResume();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
	    super.onSaveInstanceState(outState);
	    lifecycleHelper.onSaveInstanceState(outState);
	}

	@Override
	public void onPause() {
	    super.onPause();
	    lifecycleHelper.onPause();
	}

	@Override
	public void onDestroy() {
	    super.onDestroy();
	    lifecycleHelper.onDestroy();
	}
	private String initAndroidCode() {
		InputStream path;
		try {
			String[] names = getResources().getStringArray(R.array.load_tuts);
			path = getResources().getAssets().open("html/"+names[tutId]);
			Document document = Jsoup.parse(path, "UTF-8", " ");
			Elements code = document.select("#android pre");
			
			return code.html();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	private String initXMLCode() {
		InputStream path;
		try {
			String[] names = getResources().getStringArray(R.array.load_tuts);
			path = getResources().getAssets().open("html/"+names[tutId]);
			Document document = Jsoup.parse(path, "UTF-8", " ");
			Elements code = document.select("#xml pre");
			return code.html();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return null;
	}

	private class Adapter extends ArrayAdapter<String> {
		 private int lastPosition = -1;
		
		 private Context context;
		public Adapter(Context context) {
			super(context, R.layout.fb_frnds_layout,Friend_name);
			this.context= context;
			
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View row = convertView;
			if (row==null) {
				LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				row = inflater.inflate(R.layout.fb_frnds_layout, null, false);
			}
			Animation animation = AnimationUtils.loadAnimation(context, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
			Typeface typeface= Typeface.createFromAsset(context.getAssets(), "fonts/ubuntu.ttf");
			TextView mytext = (TextView) row.findViewById(R.id.FrndName);
			ProfilePictureView pictureView = (ProfilePictureView)row.findViewById(R.id.PicPro);
			mytext.setTypeface(typeface);
			mytext.setText(Friend_name.get(position));
			pictureView.setProfileId(Friend_id.get(position));
	
			
			
			row.startAnimation(animation);
			lastPosition = position;
			return row;
			
			
		}
		
	}
		
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
	
		getMenuInflater().inflate(R.menu.share_to_frnd, menu);
		return true;
		
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		if (item.getItemId() == R.id.refresh) {
			
			nDialog = new ProgressDialog(ShareToFrnd.this, ProgressDialog.STYLE_SPINNER);
			nDialog.setMessage("Fetching Friends List...");
			nDialog.setCancelable(false);
			nDialog.show();
			
			Session session=Session.getActiveSession();
			
			Bundle params = new Bundle();

			params.putString("fields", "id,name, installed");
		
			Request request = new Request(session, "me/friends", params, HttpMethod.GET, new Request.Callback() {
				
				@Override
				public void onCompleted(Response response) {
					
					if (response.getError() == null) {
						
						//Log.i("response", response.getRawResponse());
						
						
					
							try {
								friendsDb.resetFacebook();
								friendsDb.addFriends(response.getRawResponse());
								JSONObject object = new JSONObject(response.getRawResponse());
								String json = object.optString("data");
								JSONArray array = new JSONArray(json);
								nDialog.setMessage("Storing Data For Futhure Use...");
								for (int i = 0; i < array.length(); i++) {
									
									JSONObject jsonObject = array.getJSONObject(i);
									String name = jsonObject.getString("name");
									String id = jsonObject.getString("id");
									Friend_name.add(name);
									Friend_id.add(id);
								}
								
							} catch (JSONException e) {
								
								e.printStackTrace();
							}
		
								Adapter adapter = new Adapter(getApplicationContext());
								
								FriendsLists.setAdapter(adapter);
								
								nDialog.dismiss();
							}

						
					else{
						Toast.makeText(getApplicationContext(), response.getError().toString(), Toast.LENGTH_SHORT).show();
					}
					
			}
			});
			
			RequestAsyncTask asyncTask = new RequestAsyncTask(request);
			asyncTask.execute();
			
			
		}
		
		return super.onOptionsItemSelected(item);
	}


	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		
		final String id = Friend_id.get(position);
		final String name = Friend_name.get(position);
		
		AlertDialog.Builder builder= new AlertDialog.Builder(ShareToFrnd.this);
		builder.setMessage("Do You Want Share To "+name);
		builder.setCancelable(true);
		builder.setPositiveButton("Share", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				
				
				String ToPost;
				Bundle bundle = getIntent().getExtras();
				
				if (bundle.getString("toshare") == "Android") {
					ToPost = initAndroidCode();
				}
				else {
					ToPost =initXMLCode();
				}
				
				
				
			Session session = Session.getActiveSession();

			Bundle params = new Bundle();
			params.putString("name", "Code+ ");
		    params.putString("message", ToPost);
		    params.putString("to", id);
		    params.putString("action_type", "send");
		    //Utills.CopyToClipboard(getApplicationContext(), ToPost);

		   
		    WebDialog dialog = (new WebDialog.RequestsDialogBuilder(ShareToFrnd.this, session, params))
		    		.build();
		    dialog.show();

			}
		});
		builder.setNegativeButton("Cancel", null);
		builder.show();
		
		
	}

}
