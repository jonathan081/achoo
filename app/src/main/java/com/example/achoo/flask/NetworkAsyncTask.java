package com.example.achoo.flask;

import android.os.AsyncTask;
import android.util.Log;

import androidx.work.impl.background.systemalarm.ConstraintProxyUpdateReceiver;

import com.example.achoo.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

class NetworkAsyncTask extends AsyncTask<Void, Void, Void> {
    private static final String TAG = MainActivity.class.getName();

    protected Void doInBackground(Void... params) {
        HttpURLConnection conn = null;
        DataOutputStream os = null;
        try {
            Log.i(TAG, "Entered try");
            URL url = new URL("https://b677413dc2ad.ngrok.io/new_user"); //important to add the trailing slash after add

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
