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
import com.upuphub.library.onesignal.lang.OneSignalNotificationCallback;
import com.upuphub.library.onesignal.lang.OneSignalNotificationResponse;
import com.upuphub.library.onesignal.util.JsonUtil;
import com.upuphub.library.onesignal.internal.Constants;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;

/**
 * AsyncOkHttpOneSignalClient
 *
 * @author Upuphub iWzl
 * @date create time 2021-01-15 16:30
 **/
public class AsyncOkHttpOneSignalClient extends BaseOkHttpOneSignalClient {
    private final String apiUrl;
    private final String restApiKey;
    private final String appId;

    private final OkHttpClient asyncOkHttpClient;

    public AsyncOkHttpOneSignalClient(String apiUrl, String restApiKey, String appId, OkHttpClient asyncOkHttpClient) {
        this.apiUrl = apiUrl;
        this.restApiKey = restApiKey;
        this.appId = appId;
        this.asyncOkHttpClient = asyncOkHttpClient;
    }

    @Override
    public void push(OneSignalNotification oneSignalNotification, OneSignalNotificationCallback callback) {
        oneSignalNotification.setAppId(appId);
        String notificationJsonData = JsonUtil.writeValueAsString(oneSignalNotification);
        final Request request = new Request.Builder().url(apiUrl)
                .addHeader(Constants.HTTP_AUTHORIZATION_KEY, String.format(Constants.HTTP_AUTHORIZATION_VALUE_TYPE,restApiKey))
                .post(RequestBody.create(notificationJsonData, Constants.HTTP_MEDIA_TYPE_JSON_UTF8))
                .build();

        asyncOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, IOException exception) {
                callback.onFailure(oneSignalNotification, new OneSignalNotificationResponse(null, -1, null, exception));
            }

            @Override
            public void onResponse(Call call, Response response){
                final OneSignalNotificationResponse oneSignalNotificationResponse;
                try {
                    oneSignalNotificationResponse = parseResponse(response);
                } catch (Throwable t) {
                    callback.onFailure(oneSignalNotification, new OneSignalNotificationResponse(null, -1, null, t));
                    return;
                } finally {
                    if (null != response && null != response.body()) {
                        Objects.requireNonNull(response.body()).close();
                    }
                }
                if (response.code() == Constants.HTTP_REQUEST_SUCCESS) {
                    callback.onSuccess(oneSignalNotification,oneSignalNotificationResponse);
                } else {
                    callback.onFailure(oneSignalNotification, oneSignalNotificationResponse);
                }
            }
        });
    }

    @Override
    public boolean isAsync() {
        return true;
    }
}
