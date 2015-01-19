package ig.intellicast.cloud;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.api.services.storage.StorageScopes;


import java.io.IOException;

/**
 * Created by ig on 19/12/14.
 */
public class GetUsernameTask extends AsyncTask<Void,Void,Void> {
    Activity mActivity;

    String mEmail;
        GetUsernameTask(Activity activity, String name) {
        this.mActivity = activity;
        this.mEmail = name;
    }

    /**
     * Executes the asynchronous job. This runs when you call execute()
     * on the AsyncTask instance.
     */
    @Override
    protected Void doInBackground(Void... params) {
        try {
            String token = fetchToken();
             Log.i("Token got >>>>>>>>>><<<<<<<<<<<","the token value is "+token);
             MySingleton.getMyInstance().setauthtoken(token);

            if (token != null) {
                // Insert the good stuff here.
                // Use the token to access the user's Google data.

            }
        } catch (IOException e) {
            // The fetchToken() method handles Google-specific exceptions,
            // so this indicates something went wrong at a higher level.
            // TIP: Check for network connectivity before starting the AsyncTask.

        }
        return null;
    }

    /**
     * Gets an authentication token from Google and handles any
     * GoogleAuthException that may occur.
     */
    protected String fetchToken() throws IOException {
        try {
            Log.i("The scopes","the scope"+"oauth2:"+StorageScopes.DEVSTORAGE_FULL_CONTROL);
            Log.i("The mEmail","the mEmail"+mEmail);
            return GoogleAuthUtil.getToken(mActivity, mEmail,"oauth2:"+ StorageScopes.DEVSTORAGE_FULL_CONTROL);
        } catch (UserRecoverableAuthException userRecoverableException) {
            // GooglePlayServices.apk is either old, disabled, or not present
            // so we need to show the user some UI in the activity to recover.
            ((MainCloudActivity) mActivity).handleException(userRecoverableException);
            Log.i("UserRecoverableAuthException", userRecoverableException.getMessage());
        } catch (GoogleAuthException fatalException) {
            // Some other type of unrecoverable exception has occurred.
            // Report and log the error as appropriate for your app.
            Log.i("GoogleAuthException", fatalException.getMessage());

        }
        return null;
    }

}
