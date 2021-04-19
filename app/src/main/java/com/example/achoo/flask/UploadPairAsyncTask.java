package com.example.achoo.flask;

import android.os.AsyncTask;
import android.util.Log;

import com.example.achoo.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

class UploadPairAsyncTask extends AsyncTask<Void, Void, Void> {
    private static final String TAG = MainActivity.class.getName();
    private static String key;
    public UploadPairAsyncTask(String otherKey) {
        super();
        key = otherKey;
    }

    protected Void doInBackground(Void... params) {
        HttpURLConnection conn = null;
        DataOutputStream os = null;
        try {
            Log.i(TAG, "Entered try");
            URL url = new URL("https://6269c9e6b2bc.ngrok.io/new_pair"); //important to add the trailing slash after add

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
            Log.i(TAG, "Uploaded pair");
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
        String ourKey = UploadWorker.getCurrKey();
        JSONObject json = new JSONObject();
        json.put("key1", ourKey);
        json.put("key2", key);
        return json.toString().getBytes();
    }
}
