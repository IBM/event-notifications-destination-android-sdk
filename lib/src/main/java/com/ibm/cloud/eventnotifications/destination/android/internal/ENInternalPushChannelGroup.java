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


import org.json.JSONException;
import org.json.JSONObject;

public class ENInternalPushChannelGroup {

    private static final String CHANNEL_GROUP_ID = "groupId";
    private static final String CHANNEL_GROUP_NAME = "groupName";

    private String groupId = null;
    private String groupName = null;

    protected static Logger logger = Logger.getLogger(Logger.INTERNAL_PREFIX + ENInternalPushChannelGroup.class.getSimpleName());

    public ENInternalPushChannelGroup(JSONObject json) {

        try {
            groupId = json.getString(CHANNEL_GROUP_ID);
        } catch (JSONException e) {
            logger.error("ENInternalPushChannelGroup: ENInternalPushChannelGroup() - Exception while parsing JSON, get alert.  "+ e.toString());
        }
        try {
            groupName = json.getString(CHANNEL_GROUP_NAME);
        } catch (JSONException e) {
            logger.error("ENInternalPushChannelGroup: ENInternalPushChannelGroup() - Exception while parsing JSON, get androidTitle.  "+ e.toString());
        }
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        try {
            json.put(CHANNEL_GROUP_ID, groupId);
            json.put(CHANNEL_GROUP_NAME, groupName);
        } catch (JSONException e) {
            logger.error("ENInternalPushChannelGroup: ENInternalPushChannelGroup() - Exception while parsing JSON.  "+ e.toString());
        }
        return json;
    }

    @Override
    public String toString() {
        return "ENInternalPushChannelGroup [groupId=" + groupId + ", groupName=" + groupName + "]";
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getChannelGroupName() {
        return groupName;
    }

    public void setChannelGroupName(String groupName) {
        this.groupName = groupName;
    }
}
