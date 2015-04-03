package library;

import java.util.ArrayList;
import java.util.List;
import library.JSONParser;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.content.Context;

public class UserFunctions {

	
	private JSONParser jsonParser;
	//testing in local 
	private static String ServerUrl = "http://10.0.2.2/SankethStudio/androidapi/";
	
	private static String login_tag = "login";
	private static String register_tag = "register";
	
	public UserFunctions() {
		jsonParser = new JSONParser();
	}
	
	/**
	 * Function make login request
	 * @param username
	 * @param password
	 */
	public JSONObject loginUser(String user,String pass) {
		
		//parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", login_tag));
		params.add(new BasicNameValuePair("username", user));
		params.add(new BasicNameValuePair("password", pass));
		JSONObject json = jsonParser.getJSONFromUrl(ServerUrl, params);
		System.out.print(json.toString());
		return json;
	}
	
	 /**
     * Function to  Register
     **/
   public JSONObject registerUser(String fname, String lname,String uname, String email,String mob_man,String mobile,String imei){
       // Building Parameters
       List<NameValuePair> params = new ArrayList<NameValuePair>();
       params.add(new BasicNameValuePair("tag", register_tag));
       params.add(new BasicNameValuePair("fname", fname));
       params.add(new BasicNameValuePair("lname", lname));
       params.add(new BasicNameValuePair("username", uname));
       params.add(new BasicNameValuePair("email", email));
       params.add(new BasicNameValuePair("mob_man", mob_man));
       params.add(new BasicNameValuePair("mobile", mobile));
       params.add(new BasicNameValuePair("imei", imei));
       JSONObject json = jsonParser.getJSONFromUrl(Server.SERVER_URL,params);
       return json;
   }
	
	 /**
     * Function to logout user
     * Resets the temporary data stored in SQLite Database
     * */
    public boolean logoutUser(Context context){
        DatabaseHandler db = new DatabaseHandler(context);
        db.resetTables();
        return true;
    }
    
    
}
