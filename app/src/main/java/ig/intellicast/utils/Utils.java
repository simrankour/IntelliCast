package ig.intellicast.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.http.AndroidHttpClient;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Simranjit Kour on 6/1/15.
 */
public class Utils {
    public static String castID = "com.koushikdutta.cast.category.REMOTE_ALLCAST";


    static String IMAGE_URL_1 = "http://www.wallpapersfacts.com/wp-content/uploads/2014/07/lovely-beach-house-wallpapers-300x200.jpg";
    static String IMAGE_URL_2 = "http://hdwallpappers.com/images/wallpapers/Blue-Skies-Red-Sailing-Ship-wallpaper.png";
    static String IMAGE_URL_3 = "http://mattfarmer.net/projects/thickbox/images/plant4.jpg";
    static String IMAGE_URL_4 = "http://www.frugalbits.com/wp-content/uploads/2011/11/1-christmas-ps3-wallpaper-300x300.jpg";
    static String IMAGE_URL_5 = "http://www.flash-video-mx.com/blog/wp-content/uploads/2012/06/Euro-2012-Wallpaper-9-300x300.png";
    static String IMAGE_URL_6 = "http://www.themereflex.com/themes/wallpapers/Nokia%20320x240%20Wallpapers/Delightful-Nature-320x240-wallpaper.png";
    static String VIDEO_URL_1 = "http://download.clockworkmod.com/mediarouter/ff.mp4";
    static String VIDEO_URL_2 = "http://pixxelus.com/wp-content/uploads/new/!!NEW%20RELEASES/needforspeed2014.mp4";
    static String VIDEO_URL_3 = "http://pixxelus.com/wp-content/uploads/new/!!NEW%20RELEASES/A.Million.Ways.to.Die.in.the.West.2014.720p.BluRay.x264.YIFY.mp4";
    static String VIDEO_URL_4 = "http://pixxelus.com/wp-content/uploads/new/!!NEW%20RELEASES/Godzilla.2014.720p.BluRay.x264.YIFY.mp4";

    public static ArrayList<String> IMAGE_URL_LIST = new ArrayList<String>() {{
        add(IMAGE_URL_1);
        add(IMAGE_URL_2);
        add(IMAGE_URL_3);
        add(IMAGE_URL_4);
        add(IMAGE_URL_5);
        add(IMAGE_URL_6);

    }};

    public static ArrayList<String> VIDEO_URL_LIST = new ArrayList<String>() {{
        add(VIDEO_URL_1);
        add(VIDEO_URL_2);
        add(VIDEO_URL_3);
        add(VIDEO_URL_4);
    }};

    public static Bitmap downloadBitmap(String url) {
        final AndroidHttpClient client = AndroidHttpClient.newInstance("Android");
        final HttpGet getRequest = new HttpGet(url);
        try {
            HttpResponse response = client.execute(getRequest);
            final int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                Log.w("ImageDownloader", "Error " + statusCode
                        + " while retrieving bitmap from " + url);
                return null;
            }

            final HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream inputStream = null;
                try {
                    inputStream = entity.getContent();
                    final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    return bitmap;
                } finally {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    entity.consumeContent();
                }
            }
        } catch (Exception e) {
            // Could provide a more explicit error message for IOException or
            // IllegalStateException
            getRequest.abort();
            Log.w("ImageDownloader", "Error while retrieving bitmap from " + url);
        } finally {
            if (client != null) {
                client.close();
            }
        }
        return null;
    }


    public static Bitmap DownloadImageFromPath(String path) {
        InputStream in = null;
        Bitmap bmp = null;
        int responseCode = -1;
        try {

            URL url = new URL(path);//"http://192.xx.xx.xx/mypath/img1.jpg
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setDoInput(true);
            con.connect();
            responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                //download
                in = con.getInputStream();
                bmp = BitmapFactory.decodeStream(in);
                in.close();
                return bmp;
            }

        } catch (Exception ex) {
            Log.e("Exception", ex.toString());
        }
        return null;
    }
}
/**/