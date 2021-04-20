package com.example.achoo.flask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.achoo.MainActivity;
import com.example.achoo.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

public class InfCheckAsyncTask extends AsyncTask<Void, Void, Void> {
    private static final String TAG = MainActivity.class.getName();
    private WeakReference<Context> act;
    public InfCheckAsyncTask(Context activity) {
        super();
        act = new WeakReference<>(activity);
    }
    protected Void doInBackground(Void... params) {
        HttpURLConnection conn = null;
        DataOutputStream os = null;
        try {
            Log.i(TAG, "Entered try");
            URL url = new URL("https://b677413dc2ad.ngrok.io/user_status"); //important to add the trailing slash after add

            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("charset", "utf-8");
            conn.setRequestProperty("Content-Length", "application/json");
            Log.i(TAG, "Writing Data");
            os = new DataOutputStream(conn.getOutputStream());
            os.write(generateJSON());
            os.flush();

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            System.out.println("Output from Server .... \n");
            Log.i(TAG, "Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
                Log.i(TAG, "boolean attained");
                if (output.equals("Positive")) {
                    Log.i(TAG, "Person has been flagged");
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(act.get(), "notif")
                            .setSmallIcon(R.drawable.ic_launcher_background)
                            .setContentTitle("URGENT")
                            .setContentText("You have recently been in contact with an individual who has contracted" +
                                    "COVID-19. Please immediately get tested and follow CDC guidelines for quarantine" +
                                    "after possible exposure.")
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(act.get());
                    notificationManager.notify(1, builder.build());
                    Log.i(TAG, "Notification sent");
                }
            }

            conn.disconnect();

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return null;
    }
    private static byte[] generateJSON() throws JSONException {
        String key = CurrentKey.getCurrKey();
        JSONObject json = new JSONObject();
        json.put("key", key);
        return json.toString().getBytes();
    }
}
