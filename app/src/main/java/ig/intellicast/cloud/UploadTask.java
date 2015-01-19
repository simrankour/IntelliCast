package ig.intellicast.cloud;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

/**
 * Created by ig on 17/12/14.
 */
public class UploadTask extends AsyncTask<Void,Void,Void>{
        Context _ctx ;
        String FilePath  ;


       ProgressDialog pg;
        boolean exceptionFlag =false;
        UploadTask(Context c,String input){
            _ctx=c;
            FilePath=input;
        }


        @Override
        protected void onPreExecute(){
             pg= new ProgressDialog(_ctx);
            pg.setTitle("Uploading to google Cloud");
            pg.show();

        }


    @Override
    protected Void doInBackground(Void... params) {
        //Add Call to upload Image
        try {
            //Buckets been created once
           // CloudStorage.createBucket("intellicastbucket");
            CloudStorage.uploadFile("intellicastbucket", FilePath);
        }catch(Exception ex){
            ex.printStackTrace();
            exceptionFlag=true;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void v){
        if(exceptionFlag){
           pg.dismiss();
            Toast.makeText(_ctx,"Upload Operation failed",Toast.LENGTH_SHORT).show();
        }
        else {
            pg.dismiss();
            Toast.makeText(_ctx, "Upload Operation Success in Bucket testBucket_Intelligrape", Toast.LENGTH_SHORT).show();
        }

    }
}