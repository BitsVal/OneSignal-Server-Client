/*
 * MIT License
 *
 * Copyright (c) [2021] [Upuphub iWzl]
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 * */

package com.upuphub.library.onesignal.clients;

import com.upuphub.library.onesignal.lang.OneSignalNotification;
import com.upuphub.library.onesignal.lang.OneSignalNotificationResponse;
import com.upuphub.library.onesignal.util.JsonUtil;
import okhttp3.*;

import java.io.IOException;

import static com.upuphub.library.onesignal.internal.Constants.*;

/**
 * SyncOkHttpPushClient
 *
 * @author Upuphub iWzl
 * @date create time 2021-01-15 16:30
 **/
public class SyncOkHttpOneSignalClient extends BaseOkHttpOneSignalClient {
    private final String apiUrl;
    private final String restApiKey;
    private final String appId;

    private final OkHttpClient syncOkHttpClient;

    public SyncOkHttpOneSignalClient(String apiUrl, String restApiKey, String appId,OkHttpClient okHttpClient) {
        this.apiUrl = apiUrl;
        this.restApiKey = restApiKey;
        this.appId = appId;
        this.syncOkHttpClient = okHttpClient;
    }

    @Override
    public OneSignalNotificationResponse push(OneSignalNotification oneSignalNotification) {
        oneSignalNotification.setAppId(appId);
        String notificationJsonData = JsonUtil.writeValueAsString(oneSignalNotification);
        Request request = new Request.Builder().url(apiUrl)
                .addHeader(HTTP_AUTHORIZATION_KEY, String.format(HTTP_AUTHORIZATION_VALUE_TYPE,restApiKey))
                .post(RequestBody.create(notificationJsonData,HTTP_MEDIA_TYPE_JSON_UTF8))
                .build();
        Call call = syncOkHttpClient.newCall(request);
        try {
            Response response = call.execute();
            return parseResponse(response);
        } catch (IOException e) {
            return new OneSignalNotificationResponse(null, -1, null, e);
        }
    }

    @Override
    public boolean isAsync() {
        return false;
    }
}
