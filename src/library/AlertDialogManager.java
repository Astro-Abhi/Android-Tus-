package library;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;

 
public class AlertDialogManager {
    /**
     * Function to display simple Alert Dialog
     * @param context - application context
     * @param title - alert dialog title
     * @param message - alert message
     * @param status - success/failure (used to set icon)
     *               - pass null if you don't want icon
     * */
    public void showAlertDialog(Context context, String title, String message,
            Boolean status) {
       
    	Builder builder = new Builder(context);
    	builder.setTitle(title);
    	builder.setMessage(message);
    	builder.setPositiveButton("OK", null);
    	
    	AlertDialog alertDialog = builder.create();
    	alertDialog.show();
    	
    }
}