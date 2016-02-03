package coredinate.kkr.cordova.plugin.apkupdater;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class UpdateApp extends AsyncTask<String, Void, Void> {
    private Context context;

    public void setContext(Context contextf) {
        context = contextf;
    }

    @Override
    protected Void doInBackground(String... arg0) {
        try {
            URL url = new URL(arg0[0]);
            HttpURLConnection c = (HttpURLConnection) url.openConnection();
            Log(ApkAutoUpdatePlugin.TAG, "downloading apk from " + arg0[0] + "....")
            c.setRequestMethod("GET");
            c.setDoOutput(true);
            c.connect();

            String PATH = Environment.getExternalCacheDir().getAbsolutePath();
            String apkFileName = "update.apk";



            File file = new File(PATH);
            file.mkdirs();
            File outputFile = new File(file, apkFileName);
            if (outputFile.exists()) {
                Log(ApkAutoUpdatePlugin.TAG, "apk did already exist, deleting");
                outputFile.delete();
            }
            FileOutputStream fos = new FileOutputStream(outputFile);

            InputStream is = c.getInputStream();
            Log(ApkAutoUpdatePlugin.TAG, "streaming apk into folder: "+ PATH +"....");
            byte[] buffer = new byte[1024];
            int len1 = 0;
            while ((len1 = is.read(buffer)) != -1) {
                fos.write(buffer, 0, len1);
            }
            fos.close();
            is.close();
            Log(ApkAutoUpdatePlugin.TAG, "saved apk into folder: "+ PATH +"!");

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(new File(PATH+"/"+apkFileName)), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // without this flag android returned a intent error!
            context.startActivity(intent);
            Log(ApkAutoUpdatePlugin.TAG, "Starting apk activity");

        } catch (Exception e) {
            Log.e(ApkAutoUpdatePlugin.TAG, "Update error! " + e.getMessage());
        }
        return null;
    }
}