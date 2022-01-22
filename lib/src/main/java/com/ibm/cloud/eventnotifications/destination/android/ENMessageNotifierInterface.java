/**
 * (C) Copyright IBM Corp. 2021.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ibm.cloud.eventnotifications.destination.android;

import android.app.Notification;
import android.app.NotificationManager;

/**
 * Interface to handle the Notification actions.
 */
interface ENMessageNotifierInterface {
    /**
     * Method to display the push notification.
     * @param notificationManager NotificationManager object
     * @param notification Notification object
     * @param notificationId Notification ID.
     */
    void notify(NotificationManager notificationManager, Notification notification, int notificationId);

    /**
     * Method to cancel the push notification.
     * @param notificationManager NotificationManager object
     * @param notificationId Notification ID.
     */
    void cancel(NotificationManager notificationManager, int notificationId);
}
