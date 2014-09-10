/*
 * Copyright 2012-2014 One Platform Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.onepf.openpush;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by krozov on 07.09.14.
 */
public class BroadcastListener implements OpenPushListener {

    public static final String ACTION_REGISTERED = "org.onepf.openpush.registered";
    public static final String ACTION_UNREGISTERED = "org.onepf.openpush.unregistered";
    public static final String ACTION_MESSAGE = "org.onepf.openpush.message";
    public static final String ACTION_DELETED_MESSAGES = "org.onepf.openpush.message_deleted";
    public static final String ACTION_ERROR = "org.onepf.openpush.error";
    public static final String ACTION_NO_AVAILABLE_PROVIDER = "org.onepf.openpush.no_available_provider";
    public static final String ACTION_HOST_APP_REMOVED = "org.onepf.openpush.host_app_removed";

    public static final String EXTRA_PROVIDER_NAME = "org.onepf.openpush.provider_name";
    public static final String EXTRA_REGISTRATION_ID = "org.onepf.openpush.registration_id";
    public static final String EXTRA_ERROR_ID = "org.onepf.openpush.error_id";
    public static final String EXTRA_MESSAGES_COUNT = "org.onepf.openpush.messages_count";
    public static final String EXTRA_HOST_APP_PACKAGE = "org.onepf.openpush.host_app_package";

    @NotNull
    private final Context mAppContext;

    public BroadcastListener(@NotNull Context context) {
        mAppContext = context.getApplicationContext();
    }

    @Override
    public void onMessage(@NotNull String providerName, @Nullable Bundle extras) {
        sendBroadcast(ACTION_MESSAGE, providerName, extras);
    }

    @Override
    public void onDeletedMessages(@NotNull String providerName,
                                  @Nullable Bundle extras) {
        sendBroadcast(ACTION_DELETED_MESSAGES, providerName, extras);
    }

    @Override
    public void onRegistered(@NotNull String providerName, @Nullable String registrationId) {
        Bundle extras = new Bundle(1);
        extras.putString(EXTRA_REGISTRATION_ID, registrationId);
        sendBroadcast(ACTION_REGISTERED, providerName, extras);
    }

    @Override
    public void onError(@NotNull String providerName, int errorId) {
        Bundle extras = new Bundle(1);
        extras.putInt(EXTRA_ERROR_ID, errorId);
        sendBroadcast(ACTION_ERROR, providerName, extras);
    }

    @Override
    public void onNoAvailableProvider() {
        sendBroadcast(ACTION_NO_AVAILABLE_PROVIDER, null, null);
    }

    @Override
    public void onUnregistered(@NotNull String providerName, @Nullable String registrationId) {
        Bundle extras = new Bundle(1);
        extras.putString(EXTRA_REGISTRATION_ID, registrationId);
        sendBroadcast(ACTION_UNREGISTERED, providerName, extras);
    }

    private void sendBroadcast(@NotNull String action,
                               @Nullable String providerName,
                               @Nullable Bundle extras) {
        Intent newIntent = new Intent(action);
        if (extras != null) {
            newIntent.putExtras(extras);
        }
        if (providerName != null) {
            newIntent.putExtra(EXTRA_PROVIDER_NAME, providerName);
        }
        mAppContext.sendBroadcast(newIntent);
    }

    @Override
    public void onHostAppRemoved(@NotNull String providerName, @NotNull String hostAppPackage) {
        Bundle extras = new Bundle(1);
        extras.putString(EXTRA_HOST_APP_PACKAGE, hostAppPackage);
        sendBroadcast(ACTION_HOST_APP_REMOVED, providerName, extras);
    }
}
