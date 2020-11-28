package com.example.achoo;

import android.Manifest;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageListener;
import com.google.android.gms.nearby.messages.MessagesClient;
import com.google.android.gms.nearby.messages.MessagesOptions;
import com.google.android.gms.nearby.messages.NearbyPermissions;
import com.google.android.gms.nearby.messages.SubscribeOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;


public class MainActivity extends AppCompatActivity {
    private static MessageListener mMessageListener;
    private static Message mMessage;
    private static MessagesClient mMessagesClient;
    private static final String TAG = MainActivity.class.getName();

    private Switch simpleSwitch = (Switch) findViewById(R.id.BLE_Switch);
    private SubscribeOptions options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Notifications.createNotificationChannel(this);

        FloatingActionButton fab = findViewById(R.id.fab);
        //fab.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View view) {
        //        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
        //                .setAction("Action", null).show();
        //    }
       // });
        mMessageListener =  messageGateway.getMessageListener(this);

        mMessage = messageGateway.getNewMessage();

        options = messageGateway.setSubscribeOptions();
    }


    @Override
    public void onStart() {
        super.onStart();
        Nearby.getMessagesClient(this).publish(mMessage);
        Nearby.getMessagesClient(this).subscribe(mMessageListener);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMessagesClient = Nearby.getMessagesClient(this, new MessagesOptions.Builder()
                    .setPermissions(NearbyPermissions.BLE)
                    .build());
        }
        else{
            requestPermissionLauncher.launch(
                    Manifest.permission.ACCESS_FINE_LOCATION);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    private ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted){
                    mMessagesClient = Nearby.getMessagesClient(this, new MessagesOptions.Builder()
                            .setPermissions(NearbyPermissions.BLE)
                            .build());
                    activateNearby();
                } else{
                    Snackbar mySnackbar = Snackbar.make(findViewById(R.id.Snack), "You can't use our app dummy.", Snackbar.LENGTH_INDEFINITE);
                    mySnackbar.setAction("DISMISS", new View.OnClickListener(){
                        @Override
                        public void onClick(View v){
                            mySnackbar.dismiss();
                        }
                    });
                    mySnackbar.show();
                }
            });
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void sendMessage(View view) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "test")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("YO dawg, you have COVID")
                .setContentText("Sike, you got played kid")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(1, builder.build());
    }

    public void buttonFlipped(View view) {
        if (simpleSwitch.isChecked()) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.Snack), "You can't use our app dummy.", Snackbar.LENGTH_INDEFINITE);
            mySnackbar.setAction("DISMISS", new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    mySnackbar.dismiss();
                }
            });
            mySnackbar.show();
            deactivateNearby();

        } else {
            activateNearby();
        }
    }

    private void activateNearby(){
        simpleSwitch.setChecked(true);
        mMessageListener = messageGateway.getMessageListener(this);
        messageGateway.backgroundSubscribe(this);
        messageGateway.publish("Device is Broadcasting",this);
    }

    private void deactivateNearby(){
        simpleSwitch.setChecked(false);
        options.getCallback().onExpired();
        mMessageListener = null;
        Nearby.getMessagesClient(this).unsubscribe(messageGateway.getPendingIntent(this));
        messageGateway.unpublish(this);
    }
}