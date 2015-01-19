package ig.intellicast.utils;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

import ig.intellicast.R;

/**
 * Created by Simranjit Kour on 6/1/15.
 */
public class VideoDownloaderTask extends AsyncTask<String, Void, Bitmap> {
    private final WeakReference imageViewReference;

    public VideoDownloaderTask(ImageView imageView) {
        imageViewReference = new WeakReference(imageView);
    }

    @Override
    // Actual download method, run in the task thread
    protected Bitmap doInBackground(String... params) {
        // params comes from the execute() call: params[0] is the url.
        if(params[0].contains(".mp4")){
           return null;
        }
        return null;
    }


    @Override
    // Once the image is downloaded, associates it to the imageView
    protected void onPostExecute(Bitmap bitmap) {
        if (isCancelled()) {
            bitmap = null;
        }

        if (imageViewReference != null) {
            ImageView imageView = (ImageView) imageViewReference.get();
            if (imageView != null) {
                if (bitmap != null) {
                    imageView.setImageBitmap(bitmap);
                } else {
                    imageView.setImageDrawable(imageView.getContext().getResources()
                            .getDrawable(R.drawable.ic_launcher));
                }
            }

        }
    }

}
