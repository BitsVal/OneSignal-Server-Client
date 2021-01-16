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

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * OneSignal Call Back Notification Response
 *
 * @author Upuphub iWzl
 * @date create time 2021-01-15 18:13
 **/
public class NotificationResponse {
    @JsonProperty("id")
    private String requestId;
    @JsonProperty("recipients")
    private Integer recipients;
    @JsonProperty("external_id")
    private String externalId;
    @JsonProperty("errors")
    private Object errors;

    public String getRequestId() {
        return requestId;
    }

    public NotificationResponse setRequestId(String requestId) {
        this.requestId = requestId;
        return this;
    }

    public Integer getRecipients() {
        return recipients;
    }

    public NotificationResponse setRecipients(Integer recipients) {
        this.recipients = recipients;
        return this;
    }

    public String getExternalId() {
        return externalId;
    }

    public NotificationResponse setExternalId(String externalId) {
        this.externalId = externalId;
        return this;
    }

    public Object getErrors() {
        return errors;
    }

    public NotificationResponse setErrors(Object errors) {
        this.errors = errors;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("NotificationResponse{");
        sb.append("requestId='").append(requestId).append('\'');
        sb.append(", recipients=").append(recipients);
        sb.append(", externalId='").append(externalId).append('\'');
        sb.append(", errors=").append(errors);
        sb.append('}');
        return sb.toString();
    }
}
