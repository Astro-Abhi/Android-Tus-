package com.astro.androidtuts;



import java.io.InputStream;
import library.ActivitySplitAnimationUtil;
import library.ConnectionDetector;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.Options;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.Toast;






public class Learn extends ActionBarActivity   {

	
	private PullToRefreshLayout pullToRefreshLayout;
	private WebView androidWEB = null;
	private WebView xml = null;
	private WebView permission = null;
	private ActionBar actionBar;
	private ProgressBar bar;
	private String url;
	private int tut;
	private FrameLayout Android,XML;
	String[] tuts_to_load = new String[21];
	private AlertDialog dialog;
	int CurrentTab = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_learn);

		ConnectionDetector.StartNetworkServiceFor(Learn.this, getApplicationContext());
		
		bar = (ProgressBar)findViewById(R.id.progressBar);
		
		pullToRefreshLayout = (PullToRefreshLayout)findViewById(R.id.pullRefresh);
		
		
		ActionBarPullToRefresh.from(this)
			.theseChildrenArePullable(R.id.android_view)
			.listener(new OnRefreshListener() {
				
				@Override
				public void onRefreshStarted(View view) {
					
					Refresh();
				}
			})
			.options(Options.create()
					.refreshOnUp(true)
					.build()
					)
			.setup(pullToRefreshLayout);
		
		
		
		
		Bundle bundle = getIntent().getExtras();
		
		tut = bundle.getInt("tut");
		
		//Toast.makeText(getApplicationContext(), String.valueOf(tut), Toast.LENGTH_SHORT).show();
		
		tuts_to_load = getResources().getStringArray(R.array.load_tuts);

		url = tuts_to_load[tut];

		Toast.makeText(getApplicationContext(), String.valueOf(tut), Toast.LENGTH_SHORT);
			
		actionBar = getSupportActionBar();
	
		actionBar.setTitle("Loading...");
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setLogo(R.drawable.ic_launcher);
		Drawable drawable = getResources().getDrawable(R.drawable.blur);
		actionBar.setBackgroundDrawable(drawable);
		View getRow = getLayoutInflater().inflate(R.layout.sharelayout, null);
		Android = (FrameLayout)getRow.findViewById(R.id.AndroidCode);
		XML = (FrameLayout)getRow.findViewById(R.id.XMLCode);
		
		Android.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				Toast.makeText(getApplicationContext(), "Android Code", Toast.LENGTH_SHORT).show();
			}
		});
		
		XML.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg1) {
				Toast.makeText(getApplicationContext(), "XML Code", Toast.LENGTH_SHORT).show();
				
			}
		});
		
		
	final TabHost tabHost=(TabHost)findViewById(R.id.tabhost);
	Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
	tabHost.startAnimation(animation);
	
		
	tabHost.setup();
	TabHost.TabSpec tabSpec = tabHost.newTabSpec("android_code");
	tabSpec.setContent(R.id.android_code);
	tabSpec.setIndicator("Android");
	tabHost.addTab(tabSpec);
	
	TabHost.TabSpec tabSpec1 = tabHost.newTabSpec("xml_code");
	tabSpec1.setContent(R.id.xml_code);
	tabSpec1.setIndicator("XML");
	tabHost.addTab(tabSpec1);
	
	TabHost.TabSpec tabSpec2 = tabHost.newTabSpec("permission_code");
	tabSpec2.setContent(R.id.permission);
	tabSpec2.setIndicator("Permissions");
	tabHost.addTab(tabSpec2);
	
	
		
	
	androidWEB = (WebView)findViewById(R.id.android_view);
	xml = (WebView)findViewById(R.id.xml_view);
	permission = (WebView)findViewById(R.id.permission_code);
	
	
	
	
	androidWEB.loadUrl("file:///android_asset/html/"+url+"#android");
	androidWEB.getSettings().setJavaScriptEnabled(true);
	androidWEB.getSettings().setBuiltInZoomControls(true);
	androidWEB.setPadding(0, 0, 0, 0);
	androidWEB.setWebViewClient(new ViewClient());
	androidWEB.getSettings().setUseWideViewPort(true);
	androidWEB.setInitialScale(50);

	androidWEB.setScrollBarStyle(WebView.SCROLLBARS_INSIDE_OVERLAY);
	
	
	xml.loadUrl("file:///android_asset/html/"+url+"#xml");
	xml.getSettings().setJavaScriptEnabled(true);
	xml.getSettings().setBuiltInZoomControls(true);
	xml.setPadding(0, 0, 0, 0);
	xml.getSettings().setUseWideViewPort(true);
	xml.setInitialScale(50);
	androidWEB.setScrollBarStyle(WebView.SCROLLBARS_INSIDE_OVERLAY);
	
	permission.loadUrl("file:///android_asset/html/"+url+"#permission");
	permission.getSettings().setJavaScriptEnabled(true);
	permission.getSettings().setBuiltInZoomControls(true);
	permission.setPadding(0, 0, 0, 0);
	permission.getSettings().setUseWideViewPort(true);
	permission.setInitialScale(50);
	permission.setScrollBarStyle(WebView.SCROLLBARS_INSIDE_OVERLAY);

	}
	
	
	
	private void Refresh() {
	
		androidWEB.loadUrl("file:///android_asset/html/"+url+"#android");
		pullToRefreshLayout.setRefreshComplete();
	}
	
	
	

	
	private class ViewClient extends WebViewClient {
		
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			
			return super.shouldOverrideUrlLoading(view, url);
		}
		
		@Override
		public void onPageFinished(WebView view, String url) {
			Learn.this.bar.setVisibility(View.INVISIBLE);
			actionBar.setTitle("Learn");
			super.onPageFinished(view, url);
		}
		
	}
	

	public void ExecuteCode() {
		Intent Code;
		switch (tut) {
		case 0:
			Code = new Intent(getApplicationContext(), Hello.class);
			IntentOnClick(Code);
			break;
		case 1:
			Code = new Intent(getApplicationContext(), Buttons.class);
			IntentOnClick(Code);
		case 2:
			Code = new Intent(getApplicationContext(), Image.class);
			IntentOnClick(Code);
		case 3:
			Code = new Intent(getApplicationContext(), Imagebutton.class);
			IntentOnClick(Code);
		case 4:
			Code = new Intent(getApplicationContext(), Edittext.class);
			IntentOnClick(Code);
		case 5:
			Code = new Intent(getApplicationContext(), Toast_Ex.class);
			IntentOnClick(Code);
		case 6:
			Code = new Intent(getApplicationContext(), Checkbox.class);
			IntentOnClick(Code);
		case 7:
			Code = new Intent(getApplicationContext(), RadioButton_ex.class);
			IntentOnClick(Code);
		case 8:
			Code = new Intent(getApplicationContext(), Toggle.class);
			IntentOnClick(Code);
		case 9:
			Code = new Intent(getApplicationContext(), Video.class);
			IntentOnClick(Code);
		case 10:
			Code = new Intent(getApplicationContext(), WebActivity.class);
			IntentOnClick(Code);
		case 11:
			Code = new Intent(getApplicationContext(), Spinner_act.class);
			IntentOnClick(Code);
		case 12:
			Code = new Intent(getApplicationContext(), Rating.class);
			IntentOnClick(Code);
		default:
			break;
		}
	}
	
	private void IntentOnClick(Intent intent) {
		
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
		overridePendingTransition(R.anim.abc_slide_in_top, R.anim.fade_out);
		
	}

	@Override
	public void onBackPressed() {
	
		super.onBackPressed();
	
			finish();
			overridePendingTransition(R.anim.fade_in, R.anim.push_right_out);

	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		new MenuInflater(getApplicationContext()).inflate(R.menu.learn, menu);
	
		    return true;
	}
	
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		
		switch (item.getItemId()) {
		case R.id.getOptions:
			
			ExecuteCode();
			
			break;
		case R.id.shareCode:
			onCreateDialog(10);
			
		break;
		case R.id.quickShare:
		
			Intent intent = new Intent(getApplicationContext(), ShareToSocial.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.putExtra("TutName", tut);
			startActivity(intent);
			 overridePendingTransition(R.anim.push_left_in, R.anim.fade_out);


		break;
		}
		return super.onOptionsItemSelected(item);
		}


	@Override
    protected void onStop() {
        // If we're currently running the entrance animation - cancel it
        ActivitySplitAnimationUtil.cancel();

        super.onStop(); 
    }
	





	protected Dialog onCreateDialog(int id) {
		
		Builder builder = new AlertDialog.Builder(this);
		View getView = getLayoutInflater().inflate(R.layout.sharelayout, null);
		FrameLayout android = (FrameLayout)getView.findViewById(R.id.AndroidCode);
		FrameLayout xml = (FrameLayout)getView.findViewById(R.id.XMLCode);
		android.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				try {
				InputStream path = getResources().getAssets().open("html/"+url);
				Document document = Jsoup.parse(path, "UTF-8", " ");
				Elements code = document.select("#android pre");
				String getCode = code.html();
				Intent sendIntent = new Intent();
				sendIntent.setAction(Intent.ACTION_SEND);
				sendIntent.putExtra(Intent.EXTRA_TEXT, getCode);
				sendIntent.setType("text/plain");
				startActivity(sendIntent);
				dialog.dismiss();
				}
				catch(Exception exception) {
					exception.printStackTrace();
				}
				
			}
		});
		xml.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				try {
					InputStream path = getResources().getAssets().open("html/"+url);
					Document document = Jsoup.parse(path, "UTF-8", " ");
					Elements code = document.select("#xml pre");
					String getCode = code.html();
					Intent sendIntent = new Intent();
					sendIntent.setAction(Intent.ACTION_SEND);
					sendIntent.putExtra(Intent.EXTRA_TEXT, getCode);
					sendIntent.setType("text/plain");
					startActivity(sendIntent);
					dialog.dismiss();
					}
					catch(Exception exception) {
						exception.printStackTrace();
					}
			}
		});
		builder.setView(getView);
		dialog = builder.create();
		dialog.show();
		return dialog;

		}
		
	
	

}
