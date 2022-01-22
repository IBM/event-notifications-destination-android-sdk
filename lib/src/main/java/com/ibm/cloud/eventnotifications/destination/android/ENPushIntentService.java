/**
 * (C) Copyright IBM Corp. 2021.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.ibm.cloud.eventnotifications.destination.android;

import static com.ibm.cloud.eventnotifications.destination.android.internal.ENMessageKeys.GCM_EXTRA_MESSAGE;
import static com.ibm.cloud.eventnotifications.destination.android.internal.ENMessageKeys.GCM_MESSAGE;

import android.content.Context;
import android.content.Intent;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.ibm.cloud.eventnotifications.destination.android.internal.ENInternalPushMessage;
import com.ibm.cloud.eventnotifications.destination.android.internal.ENMessageConstantInterface;
import com.ibm.cloud.eventnotifications.destination.android.internal.ENMessageDefaultConstant;
import com.ibm.cloud.eventnotifications.destination.android.internal.ENPushConstants;
import com.ibm.cloud.eventnotifications.destination.android.internal.ENPushUtils;
import com.ibm.cloud.eventnotifications.destination.android.internal.Logger;


import java.util.Map;
import java.util.Random;

import org.json.JSONObject;
import org.json.JSONException;


/**
 * ENPushIntentService responsible for handling communication from GCM (Google
 * Cloud Messaging). This class should be configured as the GCM intent service
 * in AndroidManifest.xml of the android application as follows:
 * <p/>
 * <pre>
 * <p></p>
 * {@code
 * <application>
 * ...
 * 	<service android:name="com.ibm.cloud.eventnotifications.destination.android.ENPushIntentService" />
 * ...
 * </application>
 * }
 * </pre>
 */

public class ENPushIntentService extends FirebaseMessagingService {

    public static boolean isAppForeground = false;
    private static Random randomObj = new Random();
    private static Logger logger = Logger.getLogger(Logger.INTERNAL_PREFIX + ENPushIntentService.class.getSimpleName());

    public static ENMessageConstantInterface enMessageConstants = new ENMessageDefaultConstant();
    public static ENNotificationBuilderInterface enNotificationBuilder = new ENNotificationBuilderDefault();

    public static boolean isAppForeground() {
        return isAppForeground;
    }

    public static void setAppForeground(boolean isAppForeground) {
        ENPushIntentService.isAppForeground = isAppForeground;
    }

    private String getMessageId(JSONObject dataPayload) {
        try {
            String payload = dataPayload.getString("payload");
            JSONObject payloadObject = new JSONObject(payload);
            return payloadObject.getString(ENPushConstants.NID);
        } catch (JSONException e) {
            logger.error("ENPushIntentService:getMessageId() - Exception while parsing JSON, get payload  " + e.toString());
        }
        return null;
    }

    @Override
    public void onMessageReceived(RemoteMessage message) {
        String from = message.getFrom();

        Map<String, String> data = message.getData();
        int notificationId = randomObj.nextInt();
        onNotificationReceived(data, notificationId);
    }

    public void onNotificationReceived(Map<String, String> data, int notificationId) {

        JSONObject dataPayload = new JSONObject(data);
        String messageId = getMessageId(dataPayload);
        String action = data.get(ENPushConstants.ACTION);

        logger.info("ENPushIntentService:onMessageReceived() - New notification received. Payload is: " + dataPayload.toString());

        if (action != null && action.equals(ENPushConstants.DISMISS_NOTIFICATION)) {
            logger.debug("ENPushIntentService:handleMessageIntent() - Dismissal message from GCM Server");
            enNotificationBuilder.dismissNotification(this, data.get(ENPushConstants.NID).toString());
        } else {
            Context context = getApplicationContext();

            ENInternalPushMessage recMessage = new ENInternalPushMessage(dataPayload);

            if (isAppForeground()) {
                Intent intent = new Intent(ENPushUtils.getIntentPrefix(context)
                        + enMessageConstants.getStringForKey(GCM_MESSAGE));
                intent.putExtra(enMessageConstants.getStringForKey(GCM_EXTRA_MESSAGE), recMessage);
                getApplicationContext().sendBroadcast(intent);
            } else {
                enNotificationBuilder.onUnhandled(context, recMessage, notificationId);
            }

        }
    }
}
