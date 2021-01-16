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

package com.upuphub.library.onesignal;

import com.upuphub.library.onesignal.lang.OneSignalNotification;
import com.upuphub.library.onesignal.lang.OneSignalNotificationCallback;
import com.upuphub.library.onesignal.lang.OneSignalNotificationResponse;

/**
 * Interface for general purpose OneSignal Message Client
 *
 * @author Upuphub iWzl
 * @date create time 2021-01-15 15:39
 **/
public interface OneSignalClient {
    /**
     * @return 判断是否是异步执行
     */
    boolean isAsync();

    /**
     * 同步发送OneSignal Push 消息
     *
     * @param oneSignalNotification OneSignal Push内容
     * @return 发送的Push消息的发送返回结果
     */
    OneSignalNotificationResponse push(OneSignalNotification oneSignalNotification);

    void push(OneSignalNotification oneSignalNotification, OneSignalNotificationCallback callback);
}