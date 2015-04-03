package library;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FacebookFriendsDB extends SQLiteOpenHelper {

	
	 // Database Version
    private static final int DATABASE_VERSION = 1;
    
    
    private static final String Facebook = "FacebookDB";
    
    // Login table name
    private static final String FriendsTABLE = "Friends";
    
    
    // Login Table Columns names
    private static final String KEY_JSON = "json";

    
    
    public FacebookFriendsDB(Context context) {
        super(context, Facebook, null, DATABASE_VERSION);
    }


	@Override
	public void onCreate(SQLiteDatabase db) {
		 String CREATE_Facebook_TABLE = "CREATE TABLE " + FriendsTABLE + "("
                 + KEY_JSON + " TEXT)";
		 db.execSQL(CREATE_Facebook_TABLE);
	}


	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		 db.execSQL("DROP TABLE IF EXISTS " + FriendsTABLE);
	        onCreate(db);
		
	}
	
	 public void addFriends(String json) {
	        SQLiteDatabase db = this.getWritableDatabase();
	        
	       
	        ContentValues values = new ContentValues();
	        values.put("json", json); // Email
	      
	        db.insert(FriendsTABLE, null, values);
	        db.close(); // Closing database connection
	    }
	
	 public String getFriendsDetails(){
	        String JSON = new String();
	        String selectQuery = "SELECT  * FROM " + FriendsTABLE;

	        SQLiteDatabase db = this.getReadableDatabase();
	        Cursor cursor = db.rawQuery(selectQuery, null);
	        // Move to first row
	        cursor.moveToFirst();
	        if(cursor.getCount() > 0){
	        	
	        	JSON = cursor.getString(0);
	            
	        }
	        cursor.close();
	        db.close();
	        // return user
	        
	        return JSON;
	     
	    }
	 
	 public int getFriendsCount() {
	        String countQuery = "SELECT  * FROM " + FriendsTABLE;
	        SQLiteDatabase db = this.getReadableDatabase();
	        Cursor cursor = db.rawQuery(countQuery, null);
	        int rowCount = cursor.getCount();
	        db.close();
	        cursor.close();

	        // return row count
	        return rowCount;
	    }
	 
	 public void resetFacebook(){
	        SQLiteDatabase db = this.getWritableDatabase();
	        // Delete All Rows
	        db.delete(FriendsTABLE, null, null);
	        db.close();
	    }

	 
}
