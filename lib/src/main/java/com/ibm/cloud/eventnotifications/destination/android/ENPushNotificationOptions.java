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

import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;


/**
 * ENPushNotificationOptions for creating the Notifications options available in the SDK.
 */
public class ENPushNotificationOptions {

    private Visibility visibility;
    private String redact;
    private Priority priority;
    private String sound;
    private String icon;
    private List<ENPushNotificationCategory> categories = new ArrayList<ENPushNotificationCategory>();
    private String deviceId;
    private JSONObject templateValues = new JSONObject();

    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    public String getRedact() {
        return redact;
    }

    public void setRedact(String redact) {
        this.redact = redact;
    }

    public Priority getPriority() { return priority; }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public ENPushNotificationOptions() {}

    public static enum Priority {
        MAX(2), HIGH(1), DEFAULT(0), LOW(-1), MIN(-2);

        private final int value;

        Priority(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public static enum Visibility {
        PUBLIC(1), PRIVATE(0), SECRET(-1);

        private final int value;

        Visibility(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
    public void  setInteractiveNotificationCategory(ENPushNotificationCategory category) {
        categories.add(category);
    }

    public void setInteractiveNotificationCategories(List<ENPushNotificationCategory> categories) {
        this.categories = categories;
    }

    public void setDeviceid(String withDeviceId) {
        this.deviceId = withDeviceId;
    }

    public List<ENPushNotificationCategory> getInteractiveNotificationCategories() {
        return categories;
    }

    public String getDeviceid() {
        return deviceId;
    }

    public JSONObject getTemplateValues() {
        return templateValues;
    }

    public void setPushVariables(JSONObject templateValues) {
        this.templateValues = templateValues;
    }

    }
