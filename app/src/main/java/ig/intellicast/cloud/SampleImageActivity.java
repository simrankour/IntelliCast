package ig.intellicast.cloud;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.net.URL;

import ig.intellicast.R;


public class SampleImageActivity extends ActionBarActivity {
     private Bitmap bm=null;
    ImageView sampleimageview=null;
    private ProgressDialog pg ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_image);
        sampleimageview=(ImageView)findViewById(R.id.imageView2);
        Button bt =(Button)findViewById(R.id.button);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pg= new ProgressDialog(SampleImageActivity.this);
               new Thread(){
                   public void run(){
                       bm  = downloadBitmap("http://storage.googleapis.com/saliltestbucket/IMG-20141219-WA0001.jpg");
                       messageHandler.sendEmptyMessage(0);

                   }
               }.start();
            }
        });
}

    private Bitmap downloadBitmap(String str)
    {
        Bitmap mb =null;
        try{
            if(pg.isShowing()){
                pg.dismiss();
            }
            mb =BitmapFactory.decodeStream(new URL(str).openStream());

        }catch(Exception ex){
            ex.printStackTrace();
        }
        return mb;
    }

    private Handler messageHandler = new Handler(){
      @Override
    public void handleMessage(Message msg){
          super.handleMessage(msg);
          sampleimageview.setImageBitmap(bm);

      }
    };
}
