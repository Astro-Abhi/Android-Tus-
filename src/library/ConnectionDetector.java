package library;


import de.keyboardsurfer.android.widget.crouton.Configuration;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
  
public class ConnectionDetector {
  
    private Context _context;
  
    public ConnectionDetector(Context context){
        this._context = context;
    }
  
    /**
     *@return Checking for all possible internet providers
     * **/
    public boolean isConnectingToInternet(){
        ConnectivityManager connectivity = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
          if (connectivity != null)
          {
              NetworkInfo[] info = connectivity.getAllNetworkInfo();
              if (info != null)
                  for (int i = 0; i < info.length; i++)
                      if (info[i].getState() == NetworkInfo.State.CONNECTED)
                      {
                          return true;
                      }
  
          }
          return false;
    }
    
public static void StartNetworkServiceFor(final Activity activity,final Context context) {
		
	 final Handler handler = new Handler();
		Runnable runnable = new Runnable() {
			
			ConnectionDetector detector = new ConnectionDetector(context);
			
			@Override
			public void run() {
				if (!detector.isConnectingToInternet()) {
					Configuration configuration;
					Configuration.Builder builder = new Configuration.Builder();
					builder.setDuration(Configuration.DURATION_INFINITE);
					configuration = builder.build();
					Crouton.showText(activity, "No Internet Connection", Style.ALERT, 1, configuration);
				}
				else if (detector.isConnectingToInternet()) {
					Crouton.clearCroutonsForActivity(activity);
				}
				
				handler.postDelayed(this, 1000);
				
			}
		};
		handler.postDelayed(runnable, 1000);
		
	}
    
   
}