package ig.intellicast;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startActivity(new Intent(getApplicationContext(), SplashActivity.class));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_cast_images:
                Intent intentImage = new Intent(MainActivity.this, GalleryActivity.class);
                intentImage.putExtra("keyName", "image");
                startActivity(intentImage);
                break;
            case R.id.button_cast_videos:
                Intent intentVideo = new Intent(MainActivity.this, GalleryActivity.class);
                intentVideo.putExtra("keyName", "video");
                startActivity(intentVideo);
                break;
            case R.id.button_about_us:
                break;
        }
    }
}
