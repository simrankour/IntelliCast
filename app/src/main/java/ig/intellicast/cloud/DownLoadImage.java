package ig.intellicast.cloud;

import android.app.Activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

import ig.intellicast.R;


public class DownLoadImage extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bucket_create);
         final ImageView iv =(ImageView) findViewById(R.id.imageView);
        Button b =(Button)findViewById(R.id.download_image);
        EditText bucketName = (EditText)findViewById(R.id.editText);
        EditText objectName = (EditText)findViewById(R.id.editText2);
        final String bucstr=bucketName.getText().toString();
        final String objectNamestr=objectName.getText().toString();
        b.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                        new DownLoadTask(DownLoadImage.this).execute();
                    File imgFile = new  File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/download_fromCloud_Monday"," IMG-20141218-WA0001.jpg " );
                    if(imgFile.exists()) {
                        Bitmap mymap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getAbsolutePath() + "/download_fromCloud_Monday/IMG-20141218-WA0001.jpg ");
                        iv.setImageBitmap(mymap);

                    }else{
                        Log.i("The file not found at that path", "No file at the path" + imgFile);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(DownLoadImage.this,"Exception in downloading Image creation"+e.getMessage(),Toast.LENGTH_LONG).show();
                }



            }
        }


        );


    }



}
