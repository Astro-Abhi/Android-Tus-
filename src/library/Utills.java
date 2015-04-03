package library;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.widget.Toast;


public class Utills {

	public final static String FACEBOOK = "com.facebook.katana";
	private final static String TEMP = "Temp_Data";
	private Context context;	
	private  SharedPreferences Temp = context.getSharedPreferences(TEMP, Context.MODE_PRIVATE);
	private SharedPreferences.Editor editor = Temp.edit();

	public Utills(Context value) {
		this.context = value;
	}
	
	
	public static void CopyToClipboard(Context context,String copy) {
		ClipboardManager manager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
		   
		   ClipData data = ClipData.newPlainText("ToPost", copy);
		   
		   manager.setPrimaryClip(data);
		   
		   Toast.makeText(context, "The Code is Copied to clipboard", Toast.LENGTH_LONG).show();
		   
	}
	
	
	public void StoreForTempData(String key,String value) {
		
		if (key == null || value == null) {
			
			throw new IllegalStateException("Temp Data Cannot Be Null");
			
		}else {
			
			 editor.putString(key, value);
			
		}
		
	}
	
	public String GetForTempData(String key) {
		
		if (key == null) {
			
			throw new IllegalStateException("Temp Data Cannot Be Null");
			
		}else {
			
			return Temp.getString(key, null);
			
		}
		
	}
	
	
	
	public static boolean isAppInstalled(String packageName, Context context) {
		
		PackageManager manager = context.getPackageManager();
		try {
			manager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
			return true;	
		} catch (NameNotFoundException e) {
			return false;
		}
	}
	
	
	
	
}
	

	
	

