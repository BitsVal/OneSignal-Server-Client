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

package com.upuphub.library.onesignal.internal;

import okhttp3.MediaType;

/**
 * Constants
 *
 * @author Upuphub iWzl
 * @date create time 2021-01-15 17:14
 **/
public class Constants {
    public static final MediaType HTTP_MEDIA_TYPE_JSON_UTF8 = MediaType.parse("application/json; charset=UTF-8");
    public static final String HTTP_AUTHORIZATION_KEY = "Authorization";
    public static final String HTTP_AUTHORIZATION_VALUE_TYPE ="Basic %s";
    public static final Integer HTTP_REQUEST_SUCCESS = 200;

    public static final String DEFAULT_ONESIGNAL_API_URL = "https://onesignal.com/api/v1/notifications";

}
