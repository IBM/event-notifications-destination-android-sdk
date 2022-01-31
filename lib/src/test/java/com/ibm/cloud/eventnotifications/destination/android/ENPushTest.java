/**
 * Copyright 2021 IBM Corp. All Rights Reserved.
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

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.ibm.cloud.eventnotifications.destination.android.internal.BaseDeviceIdentity;
import com.ibm.cloud.eventnotifications.destination.android.internal.ServiceImpl;
import com.ibm.cloud.eventnotifications.destination.android.internal.SettingsSecureInterface;
import com.ibm.cloud.sdk.core.security.NoAuthAuthenticator;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;


public class ENPushTest {

    private String path = "http://localhost:8080";
    private MockWebServer server;

    @Before
    public void setUp() throws Exception {

        server = new MockWebServer();
        server.start(8080);
    }
    @After
    public void tearDown() throws IOException {
        server.shutdown();
    }

    @Test
    public void testENPushInit() throws NoSuchFieldException, JSONException {

        Context mockContext = Mockito.mock(Context.class);

        String testDeviceId = UUID.randomUUID().toString();
        String version = "1.0.0";
        String brand = "IBM";
        String model = "IBM-1";
        String osName = "android";


        class SettingsMock implements SettingsSecureInterface {
            @Override
            public String getDeviceID(Context context) {
                return UUID.nameUUIDFromBytes(testDeviceId.getBytes()).toString();
            }

            @Override
            public String getRelease() {
                return version;
            }

            @Override
            public String getBrand() {
                return brand;
            }

            @Override
            public String getModel() {
                return model;
            }

            @Override
            public String getOs() {
                return osName;
            }
        }

        class AndroidContextDefaultMock implements AndroidContextInterface {

            @Override
            public void validateAndroidContext(Context context) {
                return;
            }
        }

        SettingsSecureInterface mockSetting = new SettingsMock();

        when(mockContext.getApplicationContext()).thenReturn(mockContext);
        BaseDeviceIdentity.settingsSecureInterface = mockSetting;
        BaseDeviceIdentity baseDeviceIdentity = new BaseDeviceIdentity(mockContext);
        ENPush pushSut = ENPush.getInstance();
        pushSut.androidContextInterface = new AndroidContextDefaultMock();
        assertNotNull(pushSut);
        pushSut.initialize(mockContext, "test_guid", "test_destination_Id", "test_apikey");
        pushSut.setCloudRegion("test_cloud_region");
        assertEquals(pushSut.getCloudRegionSuffix(), "test_cloud_region");
    }
}
