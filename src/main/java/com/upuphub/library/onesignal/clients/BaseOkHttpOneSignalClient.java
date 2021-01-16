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

import com.upuphub.library.onesignal.OneSignalClient;
import com.upuphub.library.onesignal.exception.PushTypeNotSupportException;
import com.upuphub.library.onesignal.lang.*;
import com.upuphub.library.onesignal.util.JsonUtil;
import okhttp3.Response;

import java.io.IOException;

/**
 * 抽象的默认PushNotify发送
 *
 * @author Upuphub iWzl
 * @date create time 2021-01-15 21:11
 **/
public abstract class BaseOkHttpOneSignalClient implements OneSignalClient {

    @Override
    public OneSignalNotificationResponse push(OneSignalNotification oneSignalNotification) {
        throw new PushTypeNotSupportException("AsyncOkHttpOneSignalClient does not support synchronous calls");
    }

    @Override
    public void push(OneSignalNotification oneSignalNotification, OneSignalNotificationCallback callback) {
        throw new PushTypeNotSupportException("SyncOkHttpOneSignalClient does not support async calls");
    }

    protected OneSignalNotificationResponse parseResponse(Response response) throws IOException {
        String contentBody;
        int statusCode = response.code();
        NotificationResponseCode error;
        contentBody = null != response.body() ? response.body().string() : null;
        error = NotificationResponseCode.get(statusCode);
        OneSignalNotificationResponse oneSignalNotificationResponse =
                new OneSignalNotificationResponse(error, statusCode, contentBody, null);
        oneSignalNotificationResponse.setResult(JsonUtil.readValue(contentBody, NotificationResponse.class));
        return  oneSignalNotificationResponse;
    }


}
