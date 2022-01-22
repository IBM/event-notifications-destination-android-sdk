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

import android.content.Context;
import android.os.Build;
import android.provider.Settings;

import java.util.UUID;

public class DefaultSettingsSecure implements SettingsSecureInterface {
    @Override
    public String getDeviceID(Context context) {

        String uuid = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        return UUID.nameUUIDFromBytes(uuid.getBytes()).toString();
    }

    @Override
    public String getRelease() {
        return Build.VERSION.RELEASE;
    }

    @Override
    public String getBrand() {
        return Build.BRAND;
    }

    @Override
    public String getModel() {
        return Build.MODEL;
    }

    @Override
    public String getOs() {
        return "android";
    }
}
