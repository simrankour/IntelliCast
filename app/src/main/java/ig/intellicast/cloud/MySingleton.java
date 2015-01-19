package ig.intellicast.cloud;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;

/**
 * Created by ig on 18/12/14.
 */
public class MySingleton {
    private static  MySingleton myInstance=null  ;
    private static String authtoken = new String() ;
    private static String downloadFilePath ;
    private static GoogleCredential singleCred ;
    private MySingleton(){
    }
    public static MySingleton getMyInstance(){
        if(myInstance==null){
            myInstance=new MySingleton();
        }

        return myInstance ;
    }
    public static  void setauthtoken(String str){
        authtoken=str;
    }
    public String getauthtoken(){
        return authtoken;
    }
    public static void setDownloadFilePath(String str){
        downloadFilePath=str ;
    }
    public static String getDownloadFilePath(){
        return downloadFilePath ;
    }
    public static GoogleCredential getSingleCred(){
        return singleCred ;
    }
    public static void setSingleCred(GoogleCredential gc){
        singleCred=gc ;
    }
}
