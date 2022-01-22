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

package com.ibm.cloud.eventnotifications.destination.android.internal;

public interface ENPushConstants {
	public static final String FROM_NOTIFICATION_BAR = "notificationBar";
	public static final String DEFAULT_SERVICE_NAME = "event_notifications";
	public static final String TOKEN = "token";
	public static final String PLATFORM = "platform";
	public static final String DEVICE_ID = "device_id";
	public static final String DEVICE_ID_RES = "id";
	public static final String USER_ID = "user_id";
	public static final String ENPUSH_AUTHORIZATION  = "Authorization";
	public static final String TAG_NAME = "tag_name";
	public static final String TAGS = "tags";
	public static final String SUBSCRIPTIONS = "subscriptions";
	public static final String ID = "id";
	public static final String SENDER_ID = "senderId";
	public static final double MIN_SUPPORTED_ANDRIOD_VERSION = 4.0;
	public static final String PRIORITY_HIGH = "high";
	public static final String PRIORITY_LOW = "low";
	public static final String PRIORITY_MAX = "max";
	public static final String PRIORITY_MIN = "min";
	public static final String VISIBILITY_PUBLIC = "public";
	public static final String VISIBILITY_PRIVATE = "private";
	public static final String VISIBILITY_SECRET = "secret";
	public static final String DISMISS_NOTIFICATION = "com.ibm.cloud.eventnotifications.destination.android.sdk.DISMISS_NOTIFICATION";
	public static final String NOTIFICATIONID = "notificationId";
	public static final String URL = "url";
	public static final String TITLE = "title";
	public static final String TYPE = "type";
	public static final String TEXT = "text";
	public static final String LINES = "lines";
	public static final String PICTURE_NOTIFICATION = "picture_notification";
	public static final String BIGTEXT_NOTIFICATION = "bigtext_notification";
	public static final String INBOX_NOTIFICATION = "inbox_notification";
	public static final String NID = "nid";
	public static final String ACTION = "action";
	public static final String RAW = "raw";
	public static final String DRAWABLE = "drawable";
	public static final String STATUS = "status";
	public static final String OPEN = "OPEN";
	public static final String SEEN = "SEEN";
	public static final String PREFS_CLOUD_REGION = "IBMRegion";
	public static final String LEDARGB = "ledARGB";
	public static final String LEDONMS = "ledOnMS";
	public static final String LEDOFFMS = "ledOffMS";
	public static final String DEFAULT_CHANNEL_ID = "bms_notification_channel";
	public static final String MESSAGE_TYPE = "silent";
	public static final String TEMPLATE_OPTIONS = "variables";

	public static final Integer REQUEST_SUCCESS_200 = 200;
	public static final Integer REQUEST_SUCCESS_299 = 299;
	public static final Integer REQUEST_ERROR_AUTH = 401;
	public static final Integer REQUEST_ERROR = 400;
	public static final Integer REQUEST_ERROR_NOT_SUPPORTED = 405;

	public static final String DEVICE_GET_API_ERROR = "Error while fetching the device details";
	public static final String DEVICE_POST_API_ERROR = "Error while registering the device details";
	public static final String DEVICE_PUT_API_ERROR = "Error while updating the device details";
	public static final String DEVICE_DEL_API_ERROR = "Error while deleting the device details";

	public static final String SUBSCRIPTION_GET_API_ERROR = "Error while fetching the subscription details";
	public static final String SUBSCRIPTION_POST_API_ERROR = "Error while registering the subscription details";
	public static final String SUBSCRIPTION_PUT_API_ERROR = "Error while updating the subscription details";
	public static final String SUBSCRIPTION_DEL_API_ERROR = "Error while deleting the subscription details";

}
