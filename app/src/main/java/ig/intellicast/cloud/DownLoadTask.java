package ig.intellicast.cloud;

import android.app.Activity;
import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by ig on 19/12/14.
 */
public class DownLoadTask extends AsyncTask<Void,Void,Void> {

    Activity activity;
    boolean errorFlag ;

    public DownLoadTask(Activity a){
        this.activity =a;

    }
    Dialog d ;
    @Override
    protected  void onPreExecute(){
        d= new Dialog(activity);
        d.setTitle("Download from Google Cloud");
        d.show();
    }
    @Override
    protected Void doInBackground(Void... params) {
        try {
            CloudStorage.downloadFile("saliltestbucket"," IMG-20141218-WA0001.jpg ", Environment.getExternalStorageDirectory().getAbsolutePath()+"/download_fromCloud_Monday");
        }catch(Exception ex){
            ex.printStackTrace();
            Log.i("Error in DownLoad",ex.getMessage());
            errorFlag=true ;
        }
        return null;
    }

    @Override
    protected  void onPostExecute(Void v){
        if(d.isShowing()){
            d.dismiss();
        }
        if(errorFlag){
            Toast.makeText(activity,"Error in DownLoad",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(activity,"DownLoad Succesful",Toast.LENGTH_LONG).show();
        }


    }

}
