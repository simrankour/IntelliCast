package ig.intellicast.cloud;


import android.accounts.AccountManager;
import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button ;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.AccountPicker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import ig.intellicast.R;

public class MainCloudActivity extends Activity {

    Button uploadButton,certButton;
    Dialog upload_dialog ;
    Bitmap yourSelectedImage ;
    static final int REQUEST_CODE_PICK_ACCOUNT = 5111988;
    String email ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cloud_main);
        String[] accountTypes = new String[]{"com.google"};
        Intent intent = AccountPicker.newChooseAccountIntent(null, null,
                accountTypes, false, null, null, null, null);
        startActivityForResult(intent, REQUEST_CODE_PICK_ACCOUNT);


        uploadButton = (Button) findViewById(R.id.upload_Button);
            uploadButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                    final int ACTIVITY_SELECT_IMAGE = 1234;
                    startActivityForResult(i, ACTIVITY_SELECT_IMAGE);


                    /*upload_dialog= new Dialog(MainCloudActivity.this);
                    upload_dialog.setTitle(R.string.DialogTitle);
                    upload_dialog.setContentView(R.layout.dialog_layout);
                    Button cameraButton = (Button) upload_dialog.findViewById(R.id.camera_button);
                    Button galleryButton = (Button) upload_dialog.findViewById(R.id.gallery_Button);
                    Log.i("value of upload button", "" + uploadButton);
                    Log.i("value of gallery button", "" + galleryButton);
                    upload_dialog.show();
                    galleryButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(Intent.ACTION_PICK,
                                    MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                            final int ACTIVITY_SELECT_IMAGE = 1234;
                            startActivityForResult(i, ACTIVITY_SELECT_IMAGE);
                        }
                    });
                    cameraButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                        }
                    });*/

                }
            });
            Button sample = (Button) findViewById(R.id.testbutton);
            sample.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Getting status
                    int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());

                    // Showing status
                    if (status == ConnectionResult.SUCCESS)
                    {
                        Toast.makeText(getApplicationContext(), "Google Play Services are available", Toast.LENGTH_LONG).show();
                    }

                    else {
                        Toast.makeText(getApplicationContext(), "Google Play Services are not available", Toast.LENGTH_LONG).show();
                    }


                    }
            });
        certButton =(Button) findViewById(R.id.get_cert_button);
        certButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fileintent = new Intent(Intent.ACTION_GET_CONTENT);
                fileintent.setType("gagt/sdf");
                try {
                    startActivityForResult(fileintent, 100);
                } catch (ActivityNotFoundException e) {
                    Log.e("tag", "No activity can handle picking a file. Showing alternatives.");
                }
            }
        });
        Button downLoadButton =(Button)findViewById(R.id.download_Button);
        downLoadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchDownLoadSceen = new Intent(getApplicationContext(),DownLoadImage.class);
                startActivity(launchDownLoadSceen);
            }
        });


    }

    //When the upload button is selected
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case 1234:
                 if(resultCode == RESULT_OK){
                     if(upload_dialog!=null)
                         upload_dialog.dismiss();
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                     Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    final String filePath = cursor.getString(columnIndex);
                    cursor.close();
                    yourSelectedImage = BitmapFactory.decodeFile(filePath);
                    Log.i("the path of file","the path"+filePath);

                    ImageView iv = new ImageView(this);
                    iv.setImageBitmap(yourSelectedImage);
                    LinearLayout rl =new LinearLayout(this);
                    rl.setWeightSum(100);
                    Button uploadFromHome = new Button(this);
                    rl.addView(uploadFromHome);
                    uploadFromHome.setText("Upload to Cloud");
                    rl.addView(iv);
                    Toast.makeText(this,"the file of  path for upload"+filePath,Toast.LENGTH_LONG).show();
                    uploadFromHome.setOnClickListener( new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                             new UploadTask(MainCloudActivity.this,filePath).execute();
                        }
                    });
                    setContentView(rl);
            /* Now you have choosen image in Bitmap format in object "yourSelectedImage". You can use it in way you want! */
                }
                break ;
            case 100 :
                if(resultCode == RESULT_OK){
                    //Getting path of cert here
                    String FilePath = data.getData().getPath();
                    MySingleton.getMyInstance().setDownloadFilePath(FilePath);
                    Toast.makeText(this,"the file path for certificate"+FilePath,Toast.LENGTH_LONG).show();

                }
                break ;
            case REQUEST_CODE_PICK_ACCOUNT :
                if(resultCode==RESULT_OK){
                     email = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                    Toast.makeText(this,"The email id of user is"+email,Toast.LENGTH_LONG).show();
                    new GetUsernameTask(MainCloudActivity.this,email).execute();
                    Log.i("the token value","The value of token "+MySingleton.getMyInstance().getauthtoken());
                }
                break;
            case 101:
                Toast.makeText(this,"The email id of user is"+email,Toast.LENGTH_LONG).show();
                new GetUsernameTask(MainCloudActivity.this,email).execute();
                break ;
            default:
                Toast.makeText(this,"Default fall through",Toast.LENGTH_LONG).show();
        }

    }
    public void handleException(UserRecoverableAuthException userRecoverableException){
        Intent  i =userRecoverableException.getIntent();
        startActivityForResult(i,101);
    }

}
