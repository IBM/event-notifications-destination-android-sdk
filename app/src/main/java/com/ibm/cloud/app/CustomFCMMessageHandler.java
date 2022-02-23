package com.ibm.cloud.app;

import static com.ibm.cloud.eventnotifications.destination.android.internal.ENMessageKeys.GCM_EXTRA_MESSAGE;
import static com.ibm.cloud.eventnotifications.destination.android.internal.ENMessageKeys.GCM_MESSAGE;

import android.content.Context;
import android.content.Intent;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class CustomFCMMessageHandler extends FirebaseMessagingService {

    private static CustomFCMMessageHandler instance;

    public synchronized static CustomFCMMessageHandler getInstance() {
        if (instance == null) {
            instance = new CustomFCMMessageHandler();
        }
        return instance;
    }
    @Override
    public void onMessageReceived(RemoteMessage message) {
        Map<String, String> data = message.getData();

        Context context = getApplicationContext();

        Intent intent = new Intent("FCM_Message");
        intent.putExtra("FCM_Message_Extras", data.toString());
        getApplicationContext().sendBroadcast(intent);
    }
}