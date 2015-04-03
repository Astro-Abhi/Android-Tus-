package library;



import com.astro.androidtuts.Settings;

public class PreferenceData {

	
	public static int NETWORK_ANY = 3;
	public static int NETWORK_WIFI = 2;
	public static int NETWORK_MOBILE_WIFI = 1;
	
	
	/**
	 * 
	 * @author Astro
	 * @category Settings
	 * @return if the user wishes to activate animation
	 * */
	public boolean isAnimation() {
		return Settings.isAnim;
	}
	
	/**
	 * 
	 * @author Astro
	 * @category Settings
	 * @return if the user wishes to activate Google+
	 * */
	public boolean isGoogle() {
		return Settings.isGoogle;
	}
	
	/**
	 * 
	 * @author Astro
	 * @category Settings
	 * @return if the user wishes to activate Twiitter
	 * */	
	public boolean isTwitter() {	
		return Settings.isTwitter;	
	}
	
	/**
	 * 
	 * @author Astro
	 * @category Settings
	 * @return if the user wishes to activate Notification For Facebook
	 * */
	public boolean isNotifyFB() {
		return Settings.isNotifyFB;
	}
	/**
	 * 
	 * @author Astro
	 * @category Settings
	 * @return if the user wishes to activate Notification For Twiiter
	 * */
	public boolean isNotifyTwitter() {
		return Settings.isNotifyTwitter;
	}
	/**
	 * 
	 * @author Astro
	 * @category Settings
	 * @return if the user wishes to activate Notification RingTone
	 * */
	public boolean isNotifyTone() {
		return Settings.isNotifiTone;
	}

	/**
	 * 
	 * @author Astro
	 * @category Settings
	 * @return Which Data network to use when posting
	 * 3 as in Any network.
	 * 2 as in only Wifi
	 * 1 as in Mobile Network And Wifi
	 * */
	public int useNetwork() {
		
		String get = Settings.posting;
		
		if (get == "Any") {
			return 3;
		}else if (get == "Only in Wifi") {
			return 2;
		}else {
			return 1;
		}
		
	}

	/**
	 * 
	 * @author Astro
	 * @category Settings
	 * @return Extra Text to be appended when posting or null
	 * */
	public String getUserExtraText() {	
		return Settings.extraText;
	}
	
	/**
	 * 
	 * @author Astro
	 * @category Settings
	 * @return if to save facebook friends list offline
	 * */
	public boolean isOffile() {
		return Settings.isoffline;
	}
}
