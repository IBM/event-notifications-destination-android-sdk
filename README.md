
[![Build Status](https://travis-ci.com/IBM/event-notifications-destination-android-sdk.svg?token=eW5FVD71iyte6tTby8gr&branch=main)](https://travis-ci.com/IBM/event-notifications-destination-android-sdk)


# Android destination SDK for IBM Cloud Event Notifications service Version 0.0.1
Android destination client library to interact with various [IBM Cloud Event Notifications Service](https://cloud.ibm.com/apidocs?category=event-notifications).

Disclaimer: this SDK is being released initially as a **pre-release** version.
Changes might occur which impact applications that use this SDK.

## Table of Contents

- [Prerequisites](#prerequisites)
- [Installation](#installation)
  * [Gradle](#gradle)
- [Using the SDK](#using-the-sdk)
- [Questions](#questions)
- [Issues](#issues)
- [Open source @ IBM](#open-source--ibm)
- [Contributing](#contributing)
- [License](#license)

<!-- tocstop -->

## Overview

The IBM Cloud Event Notifications Service Android destination SDK allows developers to register for FCM destiantion of Event Notifications service in IBM cloud.

Service Name | Artifact Coordinates
--- | ---
[Event Notifications Service](https://cloud.ibm.com/apidocs/event-notifications) | com.ibm.cloud:eventnotifications-destination-android:0.0.1

## Prerequisites

[ibm-cloud-onboarding]: https://cloud.ibm.com/registration

* An [IBM Cloud][ibm-cloud-onboarding] account.
* An Event Notifications Instance
* An IAM API key to allow the SDK to access your account. Create one [here](https://cloud.ibm.com/iam/apikeys).

## Installation
The current version of this SDK is: 0.0.1

Each service's artifact coordinates are listed in the table above.

The project artifacts are published on the public [Maven Central](https://repo1.maven.org/maven2/)
artifact repository. This is the default public repository used by maven when searching for dependencies.
To use this repository within a gradle build, please see
[this link](https://docs.gradle.org/current/userguide/declaring_repositories.html).

To use the Event Notifications Android destination SDK, define a dependency that contains the artifact coordinates (group id, artifact id and version) for the service, like this:


### Gradle
```gradle
compile 'com.ibm.cloud:eventnotifications-destination-android:0.0.1'
```

## Using the SDK

SDK Methods to consume

- [Installation](#installation)
- [Initialize SDK](#initialize-sdk)
	- [Include SDK with Gradle](#include-sdk-with-gradle)
        - [Initialize SDK](#initialize-sdk)
- [Register for notifications](#register-for-notifications)
	- [Receiving notifications](#receiving-notifications)
	- [Unregistering from notifications](#unregistering-from-notifications)
- [Event Notifications destination tags subscriptions](#event-notifications-destination-tags-subscriptions)
	- [Subscribe to tags](#subscribe-to-tags)
	- [Retrieve subscribed tags](#retrieve-subscribed-tags)
	- [Unsubscribe from tags](#unsubscribe-from-tags)
- [Notification options](#notification-options)
	- [Interactive notifications](#interactive-notifications)
	- [Adding custom DeviceId for registration](#adding-custom-deviceid-for-registration)
	- [Advanced options](#advanced-options)
	- [Holding notifications](#holding-notifications)


## Installation

### Include SDK with Gradle

Configure the Module level `build.gradle` and Project level `build.gradle` files.

1. Add the following dependencies to your Project level `build.gradle` file.
		
   ```groovy
   buildscript {
   	repositories {
   	        google()
                   mavenCentral()
   	}
   	dependencies {
   	  classpath "com.android.tools.build:gradle:7.0.3"
         classpath 'com.google.gms:google-services:4.3.10'
   	}
   }
   allprojects {
        repositories {
   	  google()
         mavenCentral()
   	}
   }
   ```

2. Add SDK dependency to your Module level `build.gradle` file.
	
	```groovy
	dependencies {
    	........
        implementation platform('com.google.firebase:firebase-bom:29.0.0')
        implementation 'com.google.firebase:firebase-messaging'
        implementation 'com.ibm.cloud:sdk-core:9.15.0'

        implementation 'com.ibm.cloud:eventnotifications-destination-android:0.0.1'
        .......
	}
	```
	>**Note**: Use the latest build tools (API 26).


3. Add the `Google Play services` dependency to your Module level `build.gradle` file at the end, after the `dependencies{.....}`:

	```
	apply plugin: 'com.google.gms.google-services'
	```

4. Configure the `AndroidManifest.xml` file. Refer the [example here](https://github.com/IBM/event-notifications-destination-android-sdk/app/src/main/AndroidManifest.xml). Add the following permissions inside application's `AndroidManifest.xml` file. 

	 ```
	 <uses-permission android:name="android.permission.INTERNET"/>
	 <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
	 <uses-permission android:name="android.permission.WAKE_LOCK" />
	 ```
5. Add the notification intent settings for the activity. This setting starts the application when the user clicks the received notification from the notification area.

	```xml
        <intent-filter>
                <action android:name="Your_Android_Package_Name.IBMPushNotification"/>
                <category  android:name="android.intent.category.DEFAULT"/>
	</intent-filter>
	```
	>**Note**: Replace `Your_Android_Package_Name` in the previous action with the application package name used in your application.

6. Update the `Firebase Cloud Messaging (FCM)` or `Google Cloud Messaging (GCM)` intent service and intent filters for the `RECEIVE` and `REGISTRATION` event notifications:

	```xml
        <service android:name="com.ibm.cloud.eventnotifications.destination.android.ENPushIntentService" android:exported="true" >
                <intent-filter>
                        <action android:name="com.google.firebase.MESSAGING_EVENT" />
            </intent-filter>
        </service>

        <service android:name="com.ibm.cloud.eventnotifications.destination.android.ENPush" android:exported="true" >
            <intent-filter>
                <action android:name="com.google.firebase.INSTANCE_ID_EVENT" />
            </intent-filter>
        </service>
	```

7. Event Notifications service supports retrieval of individual notifications from the notification tray. For notifications accessed from the notification tray, you are provided with a handle only to the notification that is being clicked. All notifications are displayed when the application is opened normally. Update your `AndroidManifest.xml` file with the following snippet to use this functionality:
	
	```xml
        <activity android:name="com.ibm.cloud.eventnotifications.destination.android.ENPushNotificationHandler" android:theme="@android:style/Theme.NoDisplay"/>
        <service
	```
8. Add the `google-services.json` in Android application module root directory. For more information on how to add this file, see [Setup the SDK on FCM](https://cloud.ibm.com/docs/services/mobilepush?topic=mobile-pushnotification-push_step_3#push_step_3_Android).


### Initialize SDK
Initialize the sdk to connect with your Event Notifications service instance.

```java
import com.ibm.cloud.eventnotifications.destination.android.ENPush;

String instanceGUID = "<instance_guid>>";
String destinationID = "<instance_destination_id>";
String apiKey = "<instance_apikey>";

ENPush enPush = ENPush.getInstance();
enPush.setCloudRegion(ENPush.REGION_US_SOUTH); // Set your region

enPush.initialize(getApplicationContext(),instanceGUID,destinationID, apiKey);
```
- region : Region of the Event Notifications Instance.
- `ENPush.REGION_GERMANY`

## Register for notifications

Use the `ENPush.registerDevice()` API to register the device with FCM destination in Event Notifications service. 

The following options are supported:

- Register without userId:
	
	```java
	//Register Android devices
	enPush.registerDevice(new ENPushResponseListener<String>() {
		
		@Override
		public void onSuccess(String deviceId) {
		   //handle successful device registration here
		}

		@Override
		public void onFailure(ENPushException ex) {
		   //handle failure in device registration here
		}
	});
	```

- Register with UserId. For `userId` based notification, the register method will accept one more parameter - `userId`.

	```java
	// Register the device to Event Notifications
	enPush.registerDeviceWithUserId("userId",new ENPushResponseListener<String>() {
		
		@Override	
		public void onSuccess(String deviceId) {
			//handle successful device registration here
		}

		@Override	
		public void onFailure(ENPushException ex) {
			//handle failure in device registration here
		}
	});
	```
The userId is used to pass the unique userId value for registering for Event notifications.

### Receiving notifications

To register the `ENPushNotificationListener` object with Event Notifications service, use the `ENPush.listen()` method. This method is typically called from the `onResume()` and `onPause()` methods of the activity that is handling push notifications.

```java
//Handles the notification when it arrives
ENPushNotificationListener notificationListener = new ENPushNotificationListener() {
	
	@Override
	public void onReceive (final ENSimplePushNotification message){
      // Handle Push Notification
    }	
};

@Override
protected void onResume(){
    super.onResume();
    if(enPush != null) {
      enPush.listen(notificationListener);
    }
}

@Override
protected void onPause() {
	super.onPause();
	if (enPush != null) {
		enPush.hold();
	}
}
```

### Unregistering from notifications

Use the following code snippets to un-register from Event Notifications.
```java
enPush.unregister(new ENPushResponseListener<String>() {
	
	@Override
	public void onSuccess(String s) {
		// Handle success
	}

	@Override
	public void onFailure(ENPushException e) {
		// Handle Failure
	}
});
```
>**Note**: To unregister from the `UserId` based registration, you have to call the registration method. See the `Register without userId option` in [Register for notifications](#register-for-notifications).

## Event Notifications destination tags subscriptions

### Subscribe to tags

The `subscribe` API will subscribe the device for the list of given tags. After the device is subscribed to a particular tag, the device can receive notifications that are sent for that tag. 

Add the following code snippet to your Android mobile application to subscribe to a list of tags.

```java
// Subscribe to the given tag
enPush.subscribe(tagName, new ENPushResponseListener<String>() {
	
	@Override
	public void onSuccess(String arg) {
		System.out.println("Succesfully Subscribed to: "+ arg);
	}

	@Override
	public void onFailure(ENPushException ex) {
		System.out.println("Error subscribing to Tag1.." + ex.getMessage());
	}
});
```

### Retrieve subscribed tags

The `getSubscriptions` API will return the list of tags to which the device is subscribed. Use the following code snippets in the mobile application to get the subscription list.

```java
// Get a list of tags that to which the device is subscribed.
enPush.getSubscriptions(new ENPushResponseListener<List<String>>() {
	
	@Override
	public void onSuccess(List<String> tags) {
		System.out.println("Subscribed tags are: "+tags);
	}

	@Override
	public void onFailure(ENPushException ex) {
		System.out.println("Error getting subscriptions.. " + ex.getMessage());
	}
})
```

### Unsubscribe from tags

The `unsubscribeFromTags` API will remove the device subscription from the list tags. Use the following code snippets to allow your devices to get unsubscribe from a tag.

```java
// unsubscibe from the given tag ,that to which the device is subscribed.
push.unsubscribe(tagName, new ENPushResponseListener<String>() {
	
	@Override
	public void onSuccess(String s) {
		System.out.println("Successfully unsubscribed from tag . "+ tag);
	}

	@Override
	public void onFailure(ENPushException e) {
		System.out.println("Error while unsubscribing from tags. "+ e.getMessage());
	}	
});
```

## Notification options

The following notification options are supported.


### Interactive notifications

1. To enable interactive push notifications, the notification action parameters must be passed in as part of the notification object. The following is a sample code to enable interactive notifications:

```java
	ENPushNotificationOptions options = new ENPushNotificationOptions();
        ENPushNotificationButton acceptButton = new ENPushNotificationButton.Builder("Accept Button")
                .setIcon("check_circle_icon")
                .setLabel("Accept")
                .build();
        ENPushNotificationButton declineButton = new ENPushNotificationButton.Builder("Decline Button")
                .setIcon("extension_circle_icon")
                .setLabel("Decline")
                .build();
        ENPushNotificationButton viewButton = new ENPushNotificationButton.Builder("View Button")
                .setIcon("extension_circle_icon")
                .setLabel("view")
                .build();
        List<ENPushNotificationButton> buttonGroup_1 =  new ArrayList<ENPushNotificationButton>();
        buttonGroup_1.add(acceptButton);
        buttonGroup_1.add(declineButton);
        buttonGroup_1.add(viewButton);
        List<ENPushNotificationButton> buttonGroup_2 =  new ArrayList<ENPushNotificationButton>();
        buttonGroup_2.add(acceptButton);
        buttonGroup_2.add(declineButton);
        List<ENPushNotificationButton> buttonGroup_3 =  new ArrayList<ENPushNotificationButton>();
        buttonGroup_3.add(acceptButton);

        ENPushNotificationCategory category = new ENPushNotificationCategory.Builder("First_Button_Group1").setButtons(buttonGroup_1).build();
        ENPushNotificationCategory category1 = new ENPushNotificationCategory.Builder("First_Button_Group2").setButtons(buttonGroup_2).build();
        ENPushNotificationCategory category2 = new ENPushNotificationCategory.Builder("First_Button_Group3").setButtons(buttonGroup_3).build();
        List<ENPushNotificationCategory> categoryList =  new ArrayList<ENPushNotificationCategory>();
        categoryList.add(category);
        categoryList.add(category1);
        categoryList.add(category2);
        options.setInteractiveNotificationCategories(categoryList);
        enPush.initialize(getApplicationContext(),instanceGUID,destinationID, apiKey, options);

```

2. To handle the interactive notifications by identifying which action is clicked, follow the method:

```java
	notificationListener = new ENPushNotificationListener() {

            @Override
            public void onReceive(final ENSimplePushNotification message) {

                showNotification(activity, message);

                if (message.actionName.equals("Accept Button")){
                    System.out.print("Clicked Accept Action");
                }else if (message.actionName.equals("Decline Button")){
                    System.out.print("Clicked Decline Action");
                }else if (message.actionName.equals("View Button")){
                    System.out.print("Clicked View Action");
                }
            
            }
        };
```
	
This callback method is invoked when user clicks the action button.

### Adding custom DeviceId for registration

To send `DeviceId` use the `setDeviceId` method of `ENPushNotificationOptions` class.

```java
	ENPushNotificationOptions options = new ENPushNotificationOptions();
	options.setDeviceid("YOUR_DEVICE_ID");
```

>**Note**: Remember to keep custom DeviceId `unique` for each device.


### Advanced options

You can choose to specify a ring-tone for your notifications. To specify a ring-tone, complete the following steps:

1. Create a folder named `raw` in the `res` directory of your android application and add the ring-tone files to that folder.
2. Specify the ring-tone file name when you send notification to IBM Cloud Event Notifications FCM destination.


### Holding notifications

When your application goes into background, you might want Push Notifications to hold back notifications sent to your application. To hold notifications, call the `hold()` method in the `onPause()` method of the activity that is handling Push Notifications.

```java
	@Override
	protected void onPause() {
    	        super.onPause();
	        if (enPush != null) {
    	                enPush.hold();
    	        }
	}
```

## Multidex support prior to Android 5.0
Versions of the platform prior to Android 5.0 (API level 21) use the Dalvik runtime for executing app code.
1. Add the following in your gradle file,

```groovy

android {
...
   defaultConfig{
    ....
      multiDexEnabled true
    ....
   }
...
}
...
dependencies {
  .....
  compile 'com.android.support:multidex:1.0.1'
  ....
}
```
2. In the `manifest.xml` file add teh following,
A
```xml
<application 
    android:name="android.support.multidex.MultiDexApplication"
```


## Questions

If you are having difficulties using this SDK or have a question about the IBM Cloud services,
please ask a question at
[Stack Overflow](http://stackoverflow.com/questions/ask?tags=ibm-cloud).

## Issues
If you encounter an issue with the project, you are welcome to submit a
[bug report](https://github.com/IBM/event-notifications-destination-android-sdk/issues).
Before that, please search for similar issues. It's possible that someone has already reported the problem.

## Open source @ IBM
Find more open source projects on the [IBM Github Page](http://ibm.github.io/)

## Contributing
See [CONTRIBUTING](CONTRIBUTING.md).

## License

The IBM Cloud Event Notifications Service Android destination SDK is released under the Apache 2.0 license.
The license's full text can be found in [LICENSE](LICENSE).
