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

package org.onepf.opfpush.gcm;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.pm.PackageInfo;
import android.content.res.Resources;
import android.os.Handler;

import org.junit.After;
import org.junit.Before;
import org.robolectric.Robolectric;

/**
 * @author Kirill Rozov
 * @since 10/8/14.
 */

public abstract class GCMProviderTestBase {

    private static final Account TEST_GOOGLE_ACCOUNT
            = new Account("OnePF Test", GCMProvider.GOOGLE_ACCOUNT_TYPE);

    protected static void addGoogleAccount() {
        AccountManager accountManager = AccountManager.get(Robolectric.application);
        Robolectric.shadowOf(accountManager).addAccount(TEST_GOOGLE_ACCOUNT);
    }

    protected static void removeGoogleAccount() {
        AccountManager accountManager = AccountManager.get(Robolectric.application);
        Robolectric.shadowOf(accountManager)
                .removeAccount(TEST_GOOGLE_ACCOUNT, null, new Handler());
    }

    protected static void removeGMCServiceApp() {
        Robolectric.packageManager.removePackage(TestConstants.GOOGLE_PLAY_SERVICES_PACKAGE);
    }

    protected static void addGMSServiceApp(String versionName, int versionCode) {
        PackageInfo packageInfo = new PackageInfo();
        packageInfo.packageName = TestConstants.GOOGLE_PLAY_SERVICES_PACKAGE;
        packageInfo.versionName = versionName;
        packageInfo.versionCode = versionCode;
        Robolectric.packageManager.addPackage(packageInfo);
    }

    protected static void addLatestGMSServiceApp() {
        Resources resources = Robolectric.application.getResources();
        addGMSServiceApp("4.0.30", 4030000);
    }

    @Before
    public void addGooglePlayApp() {
        Robolectric.packageManager.addPackage(GCMProvider.GOOGLE_PLAY_APP_PACKAGE);
    }

    @After
    public void removeGooglePlayApp() {
        Robolectric.packageManager.removePackage(GCMProvider.GOOGLE_PLAY_APP_PACKAGE);
    }
}
