package com.ibm.cloud.app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ibm.cloud.eventnotifications.destination.android.ENPush;
import com.ibm.cloud.eventnotifications.destination.android.ENPushException;
import com.ibm.cloud.eventnotifications.destination.android.ENPushNotificationButton;
import com.ibm.cloud.eventnotifications.destination.android.ENPushNotificationCategory;
import com.ibm.cloud.eventnotifications.destination.android.ENPushNotificationListener;
import com.ibm.cloud.eventnotifications.destination.android.ENPushNotificationOptions;
import com.ibm.cloud.eventnotifications.destination.android.ENPushResponseListener;
import com.ibm.cloud.eventnotifications.destination.android.ENSimplePushNotification;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView txtVResult = null;
    private Button regButton = null;
    private Button unRegButton = null;
    private Button tagSubButton = null;
    private Button UnTagSubButton = null;
    private Button clearSubButton = null;

    private ProgressBar loading_spinner = null;

    private ENPush enPush = null;
    private ENPushNotificationListener notificationListener = null;

    private List<String> subscribedTags;
    private String tagName = "Tech_IBM";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtVResult = (TextView) findViewById(R.id.display);
        regButton = (Button) findViewById(R.id.register);
        unRegButton = (Button) findViewById(R.id.unregister);
        tagSubButton = (Button) findViewById(R.id.subtags);
        UnTagSubButton = (Button) findViewById(R.id.unsubtags);
        loading_spinner = (ProgressBar) findViewById(R.id.loading_spinner);
        clearSubButton = (Button) findViewById(R.id.clearButton);
        loading_spinner.setVisibility(View.INVISIBLE);
        txtVResult.setMovementMethod(new ScrollingMovementMethod());


        regButton.setOnClickListener(this);
        unRegButton.setOnClickListener(this);
        tagSubButton.setOnClickListener(this);
        UnTagSubButton.setOnClickListener(this);
        clearSubButton.setOnClickListener(this);

        /**
         * Event Notifications credentials.
         */
        String instanceGUID = "<instance_guid>>";
        String destinationID = "<instance_destination_id>";
        String apiKey = "<instance_apikey>";

        enPush = ENPush.getInstance();
        enPush.setCloudRegion(ENPush.REGION_US_SOUTH);

        enPush.initialize(getApplicationContext(),instanceGUID,destinationID, apiKey);


        final Activity activity = this;


        notificationListener = new ENPushNotificationListener() {

            @Override
            public void onReceive(final ENSimplePushNotification message) {

                showNotification(activity, message);

                if (message.getActionName() == null){
                    return;
                }

                if (message.getActionName().equals("Accept Button")){
                    System.out.print("Clicked Accept Action");
                }else if (message.getActionName().equals("Decline Button")){
                    System.out.print("Clicked Decline Action");
                }else if (message.getActionName().equals("View Button")){
                    System.out.print("Clicked View Action");
                }

            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (enPush != null) {
            enPush.listen(notificationListener);
        }
    }
    private void regForPush() {
        updateTextView("Registering the device.");
        disableUI(true);

        //enPush.registerDevice();
        enPush.registerDeviceWithUserId("userId", new ENPushResponseListener<String>() {
            @Override
            public void onSuccess(String deviceId) {
                System.out.println("deviceId : " + deviceId);
                updateTextView("Device is registered with Event Notifications Service.");
                disableUI(false);
            }

            @Override
            public void onFailure(ENPushException ex) {
                updateTextView("Error registering with Event Notifications Service...\n" + ex.toString()
                        + "push notifications will not be received.");
                disableUI(false);
            }
        });
    }
    private void unRegisterPush() {
        updateTextView("Unregistering the device.");
        disableUI(true);
        enPush.unregister(new ENPushResponseListener<String>() {
            @Override
            public void onSuccess(String response) {
                updateTextView("Device is unregistered from Event Notifications Service.");
                disableUI(false);
            }

            @Override
            public void onFailure(ENPushException exception) {
                updateTextView("Error unregistering with Event Notifications Service...\n" + exception.toString());
                disableUI(false);
            }
        });
    }

    private void disableUI(Boolean disable) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {

                if (disable) {
                    loading_spinner.setVisibility(View.VISIBLE);
                    regButton.setEnabled(false);
                    unRegButton.setEnabled(false);
                    tagSubButton.setEnabled(false);
                    UnTagSubButton.setEnabled(false);
                } else {
                    loading_spinner.setVisibility(View.INVISIBLE);
                    regButton.setEnabled(true);
                    unRegButton.setEnabled(true);
                    tagSubButton.setEnabled(true);
                    UnTagSubButton.setEnabled(true);
                }

            }
        });

    }

    void subscribeToTag() {
        updateTextView("Subscribing to tag");
        disableUI(true);
        enPush.subscribe(tagName, new ENPushResponseListener<String>() {
            @Override
            public void onSuccess(String response) {
                updateTextView("Successfully Subscribed to: "+ tagName);
                disableUI(false);

            }

            @Override
            public void onFailure(ENPushException exception) {
                updateTextView("Error subscribing to tag .."
                        + exception.getMessage());
                disableUI(false);
            }
        });
    }
    void unsubscribeFromTag() {
        updateTextView("Unsubscribing from tag");
        disableUI(true);

        enPush.unsubscribe(tagName, new ENPushResponseListener<String>() {

            @Override
            public void onSuccess(String s) {
                updateTextView("Successfully unsubscribed from tag . " + tagName);
                disableUI(false);
            }

            @Override
            public void onFailure(ENPushException e) {
                updateTextView("Error while unsubscribing from tags. " + e.getMessage());
                disableUI(false);
            }

        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register:
                this.regForPush();
                break;
            case R.id.unregister:
                this.unRegisterPush();
                break;
            case R.id.subtags:
                this.subscribeToTag();
                break;
            case R.id.unsubtags:
                this.unsubscribeFromTag();
                break;
            case R.id.clearButton:
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        txtVResult.setText("");
                    }
                });
                break;
            default:
                break;

        }
    }

    void showNotification(Activity activity, ENSimplePushNotification message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage("Notification Received : " + message.getAlert());
        builder.setCancelable(true);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();


    }

    public void updateTextView(final String str) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                txtVResult.append(str);
                txtVResult.append("\n");
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (enPush != null) {
            enPush.hold();
        }

    }


}