package com.example.achoo;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Switch;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.core.content.ContextCompat;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.example.achoo.flask.UploadWorker;
import com.example.achoo.ui.login.LoginActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.messages.MessageListener;
import com.google.android.gms.nearby.messages.MessagesClient;
import com.google.android.gms.nearby.messages.MessagesOptions;
import com.google.android.gms.nearby.messages.NearbyPermissions;
import com.google.android.gms.nearby.messages.SubscribeOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity {
    private static MessageListener mMessageListener;
    private MessagesClient mMessagesClient;
    private static final String TAG = MainActivity.class.getName();
    GoogleSignInClient mGoogleSignInClient;
    // Switch simpleSwitch = (Switch) findViewById(R.id.BLE_Switch);
    private SubscribeOptions options;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Notifications.createNotificationChannel(this);


        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mAuth = FirebaseAuth.getInstance();
        
        mMessageListener =  messageGateway.getMessageListener(this);

        options = messageGateway.setSubscribeOptions();

        PeriodicWorkRequest workerRequestKey =
                new PeriodicWorkRequest.Builder(UploadWorker.class,
                        24, TimeUnit.HOURS, 30, TimeUnit.MINUTES)
                        .setInitialDelay(24, TimeUnit.HOURS)
                        .build();
        WorkManager
                .getInstance(this)
                .enqueue(workerRequestKey);

        PeriodicWorkRequest workerRequestInf =
                new PeriodicWorkRequest.Builder(InfectedWorker.class,
                        24, TimeUnit.HOURS, 30, TimeUnit.MINUTES)
                        .setInitialDelay(25, TimeUnit.HOURS)
                        .build();
        WorkManager
                .getInstance(this)
                .enqueue(workerRequestInf);

        UploadWorker.createFirstKey();
    }


    @Override
    public void onStart() {
        super.onStart();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH)
                == PackageManager.PERMISSION_GRANTED) {
            mMessagesClient = Nearby.getMessagesClient(this, new MessagesOptions.Builder()
                    .setPermissions(NearbyPermissions.BLE)
                    .build());
        }
        else{
            requestPermissionLauncher.launch(
                    Manifest.permission.ACCESS_FINE_LOCATION);
            requestPermissionLauncher.launch(
                    Manifest.permission.BLUETOOTH);
        }

        final BluetoothManager bluetoothManager =
                (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        BluetoothAdapter bluetoothAdapter = bluetoothManager.getAdapter();

        mMessageListener =  messageGateway.getMessageListener(this);

        options = messageGateway.setSubscribeOptions();
    }

    @Override
    public void onStop() {
        //Nearby.getMessagesClient(this).unpublish(mMessage);
        //Nearby.getMessagesClient(this).unsubscribe(mMessageListener);
        super.onStop();
    }

    private ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted){
                    mMessagesClient = Nearby.getMessagesClient(this, new MessagesOptions.Builder()
                            .setPermissions(NearbyPermissions.BLE)
                            .build());
                    activateNearby((Switch) findViewById(R.id.BLE_Switch));
                } else{
                    Snackbar mySnackbar = Snackbar.make(findViewById(R.id.coordinatorLayout), "App Requires Location Services", Snackbar.LENGTH_INDEFINITE);
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
        // Handle action bar item clicks here.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.sign_out) {
             mGoogleSignInClient.signOut()
                    .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Log.d(TAG, "Signed out Successfully");
                            Intent ide = new Intent(MainActivity.this, LoginActivity.class);
                            startActivity(ide);
                        }
                    });
        }

        if (id == R.id.upload) {
            sendToUpload();
        }
        return super.onOptionsItemSelected(item);
    }

    public void buttonFlipped(View view) {
        Switch simpleSwitch = (Switch) findViewById(R.id.BLE_Switch);
        if (!simpleSwitch.isChecked()) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.coordinatorLayout), "Location Services turned off", Snackbar.LENGTH_INDEFINITE);
            mySnackbar.setAction("DISMISS", new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    mySnackbar.dismiss();
                }
            });
            mySnackbar.show();
            deactivateNearby(simpleSwitch);

        } else {
            activateNearby(simpleSwitch);
            return;
        }
    }
    public void uploadButton(View view) {
        sendToUpload();
    }

    private void activateNearby(Switch simpleSwitch){
        mMessageListener = messageGateway.getMessageListener(this);
        messageGateway.backgroundSubscribe(this, mMessageListener);
        messageGateway.publish(this);
    }

    private void deactivateNearby(Switch simpleSwitch){
        options.getCallback().onExpired();
        mMessageListener = null;
        Nearby.getMessagesClient(this).unsubscribe(messageGateway.getPendingIntent(this));
        messageGateway.unpublish(this);
    }

    public void sendToUpload(){
        Intent ide = new Intent(MainActivity.this, UploadActivity.class);
        // ide.putExtra
        // ide.addFlags ** May be needed **
        startActivity(ide);
    }
}