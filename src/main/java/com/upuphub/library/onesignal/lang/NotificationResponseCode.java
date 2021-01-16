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

import java.util.HashMap;
import java.util.Map;

/**
 * A collection of all the HTTP status codes returned by Onesignal Api.
 *
 * @author Upuphub iWzl
 * @date create time 2021-01-15 17:39
 */
public enum NotificationResponseCode {
    /**
     * notification response return
     */
    Success(200),
    BadRequest(400),
    BadMethod(405),
    DeviceTokenInactiveForTopic(410),
    PayloadTooLarge(413),
    TooManyRequestsForToken(429),
    InternalServerError(500),
    ServerUnavailable(503),
    InvalidProviderToken(403);
    public final int errorCode;

    NotificationResponseCode(int errorCode) {
        this.errorCode = errorCode;
    }

    private static final Map<Integer, NotificationResponseCode> ERROR_MAP = new HashMap<>();

    static {
        for (NotificationResponseCode notificationRequestError : NotificationResponseCode.values()) {
            ERROR_MAP.put(notificationRequestError.errorCode, notificationRequestError);
        }
    }

    public static NotificationResponseCode get(int errorCode) {
        return ERROR_MAP.get(errorCode);
    }
}
