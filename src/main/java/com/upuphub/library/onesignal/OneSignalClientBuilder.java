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

import com.upuphub.library.onesignal.Interceptor.RetryInterceptor;
import com.upuphub.library.onesignal.clients.AsyncOkHttpOneSignalClient;
import com.upuphub.library.onesignal.clients.SyncOkHttpOneSignalClient;
import com.upuphub.library.onesignal.internal.Constants;
import okhttp3.ConnectionPool;
import okhttp3.EventListener;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * OneSignalClient Builder
 *
 * @author Upuphub iWzl
 * @date create time 2021-01-15 21:25
 **/
public class OneSignalClientBuilder {
    private final String authKey;
    private final String appId;
    private boolean async;
    private String apiUrl = Constants.DEFAULT_ONESIGNAL_API_URL;

    private TimeUnit timeUnit = TimeUnit.SECONDS;
    private Integer readTimeout = 5;
    private Integer connectTimeout = 5;

    private OkHttpClient okHttpClient;
    private ConnectionPool connectionPool;

    private Integer maxIdleConnections = 10;
    private Integer keepAliveDuration = 600;
    private Integer maxRetryCount = 0;

    private List<Interceptor> interceptors;
    private List<Interceptor> netWorkingInterceptors;

    private EventListener eventListener;


    public static OneSignalClientBuilder newBuilder(String appId,String authKey){
        return new OneSignalClientBuilder(appId,authKey);
    }

    public OneSignalClientBuilder(String appId,String authKey) {
        this.authKey = authKey;
        this.appId = appId;
        this.async = false;
    }

    public OneSignalClientBuilder setApiUrl(String apiUrl){
        this.apiUrl = apiUrl;
        return this;
    }


    public OneSignalClientBuilder setAsync(boolean async) {
        this.async = async;
        return this;
    }

    public OneSignalClientBuilder setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
        return this;
    }


    public OneSignalClientBuilder setReadTimeout(Integer readTimeout) {
        this.readTimeout = readTimeout;
        return this;
    }

    public OneSignalClientBuilder setConnectTimeout(Integer connectTimeout) {
        this.connectTimeout = connectTimeout;
        return this;
    }

    public OneSignalClientBuilder setMaxIdleConnections(Integer maxIdleConnections) {
        this.maxIdleConnections = maxIdleConnections;
        return this;
    }

    public OneSignalClientBuilder setKeepAliveDuration(Integer keepAliveDuration) {
        this.keepAliveDuration = keepAliveDuration;
        return this;
    }

    public OneSignalClientBuilder setOkHttpClient(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
        return this;
    }

    public OneSignalClientBuilder setConnectionPool(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
        return this;
    }

    public OneSignalClientBuilder setMaxRetryCount(Integer maxRetryCount) {
        this.maxRetryCount = maxRetryCount;
        return this;
    }

    public OneSignalClientBuilder setInterceptors(List<Interceptor> interceptors) {
        this.interceptors = interceptors;
        return this;
    }

    public OneSignalClientBuilder setNetWorkingInterceptors(List<Interceptor> netWorkingInterceptors) {
        this.netWorkingInterceptors = netWorkingInterceptors;
        return this;
    }

    public OneSignalClientBuilder setEventListener(EventListener eventListener) {
        this.eventListener = eventListener;
        return this;
    }

    public OneSignalClient build(){
        if(null == this.okHttpClient) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .readTimeout(this.readTimeout, this.timeUnit)
                    .connectTimeout(this.connectTimeout, this.timeUnit)
                    .retryOnConnectionFailure(true);
            if (null == this.connectionPool) {
                builder.connectionPool(new ConnectionPool(this.maxIdleConnections, this.keepAliveDuration, this.timeUnit));
            } else {
                builder.connectionPool(this.connectionPool);
            }
            if (this.maxRetryCount > 0) {
                builder.addInterceptor(new RetryInterceptor(this.maxRetryCount));
            }
            if(null != this.interceptors && !this.interceptors.isEmpty()){
                this.interceptors.forEach(builder::addInterceptor);
            }
            if(null != this.netWorkingInterceptors && ! this.netWorkingInterceptors.isEmpty()){
                this.netWorkingInterceptors.forEach(builder::addNetworkInterceptor);
            }
            if(null != this.eventListener){
                builder.eventListener(this.eventListener);
            }
            this.okHttpClient = builder.build();
        }
        if(this.async){
            return new AsyncOkHttpOneSignalClient(this.apiUrl,this.authKey,this.appId,this.okHttpClient);
        }else {
            return new SyncOkHttpOneSignalClient(this.apiUrl,this.authKey,this.appId,this.okHttpClient);
        }
    }
}
