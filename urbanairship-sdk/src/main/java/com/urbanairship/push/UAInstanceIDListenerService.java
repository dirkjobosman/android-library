/*
Copyright 2009-2015 Urban Airship Inc. All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this
list of conditions and the following disclaimer.

2. Redistributions in binary form must reproduce the above copyright notice,
this list of conditions and the following disclaimer in the documentation
and/or other materials provided with the distribution.

THIS SOFTWARE IS PROVIDED BY THE URBAN AIRSHIP INC ``AS IS'' AND ANY EXPRESS OR
IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO
EVENT SHALL URBAN AIRSHIP INC OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.urbanairship.push;

import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

import com.google.android.gms.iid.InstanceIDListenerService;
import com.urbanairship.Logger;

/**
 * Listens for GCM Security token refresh. If your application needs to be notified when
 * the security tokens are refreshed, extend {@link UAInstanceIDListenerService} and override
 * {@link #onTokenRefresh()}. Make sure to call {@code super.onTokenRefresh()}.
 * <p/>
 * In the AndroidManifest.xml, add the following under the application entry:
 * <pre>
 * {@code
 * <service android:name="com.urbanairship.push.UAInstanceIDListenerService" tools:node="remove"/>
 * <service android:name=".YourInstanceIDListenerService" android:exported="false">
 *     <intent-filter>
 *         <action android:name="com.google.android.gms.iid.InstanceID"/>
 *     </intent-filter>
 * </service>
 * }
 * </pre>
 */
public class UAInstanceIDListenerService extends InstanceIDListenerService {

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        Logger.debug("GCM security tokens refreshed.");

        Intent intent = new Intent(getApplicationContext(), PushService.class)
                .setAction(PushService.ACTION_UPDATE_PUSH_REGISTRATION)
                .putExtra(PushService.EXTRA_GCM_TOKEN_REFRESH, true);

        WakefulBroadcastReceiver.startWakefulService(getApplicationContext(), intent);
    }
}
