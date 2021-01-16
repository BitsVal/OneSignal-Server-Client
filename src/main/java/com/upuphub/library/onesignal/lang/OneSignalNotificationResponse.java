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

package com.upuphub.library.onesignal.lang;

/**
 * NotificationResponse 返回消息
 *
 * @author Upuphub iWzl
 * @date create time 2021-01-15 15:43
 **/
public class OneSignalNotificationResponse extends NotificationResponse{
    private NotificationResponseCode error;
    private int httpStatusCode;
    private String requestId;
    private String responseBody;
    private Throwable cause;

    public OneSignalNotificationResponse() {
    }

    public OneSignalNotificationResponse(NotificationResponseCode error, int httpStatusCode, String responseBody, Throwable cause) {
        this.error = error;
        this.httpStatusCode = httpStatusCode;
        this.responseBody = responseBody;
        this.cause = cause;
    }

    public void setResult(NotificationResponse notificationResponse){
        if(null == notificationResponse){
            return;
        }
        this.setRequestId(notificationResponse.getRequestId());
        this.setExternalId(notificationResponse.getExternalId());
        this.setRecipients(notificationResponse.getRecipients());
        this.setErrors(notificationResponse.getErrors());
    }


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("OneSignalNotificationResponse{");
        sb.append("error=").append(error);
        sb.append(", httpStatusCode=").append(httpStatusCode);
        sb.append(", requestId='").append(requestId).append('\'');
        sb.append(", responseBody='").append(responseBody).append('\'');
        sb.append(", cause=").append(cause);
        sb.append(", NotificationResponse=").append(super.toString());
        sb.append('}');
        return sb.toString();
    }
}
