package com.example.achoo;

import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class FlaskGateway {
    private static final String TAG = MainActivity.class.getName();

    public static void newUser(){
        HttpURLConnection conn = null;
        DataOutputStream os = null;
        try{
            Log.i(TAG, "Entered try");
            URL url = new URL("http://b0f285f00749.ngrok.io/"); //important to add the trailing slash after add

            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty( "charset", "utf-8");
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

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }

        finally {
            if(conn != null)
            {
                conn.disconnect();
            }
        }
    }

    private static byte[] generateJSON(){
        return UploadWorker.getCurrKey().getBytes();
    }
}
