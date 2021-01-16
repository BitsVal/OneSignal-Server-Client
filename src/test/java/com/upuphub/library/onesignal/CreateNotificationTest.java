package com.upuphub.library.onesignal;


import com.upuphub.library.onesignal.lang.*;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

/**
 * Create notification Test
 *
 * @author Upuphub iWzl
 * @date create time 2021-01-15 15:44
 **/

public class CreateNotificationTest {

    @Test
    public void  sendSyncNotificationToExternalUser() {
        OneSignalClient oneSignalClient = OneSignalClientBuilder
                .newBuilder("5eb5a37e-b458-11e3-ac11-000c2940e62c","NGEwMGZmMjItY2NkNy0xMWUzLTk5ZDUtMDAwYzI5NDBlNjJj")
                .build();
        OneSignalNotification oneSignalNotification = new OneSignalNotification();
        oneSignalNotification.setIncludePlayerIds(Arrays.asList("6392d91a-b206-4b7b-a620-cd68e32c3a76","76ece62b-bcfe-468c-8a78-839aeaa8c5fa","8e0f21fa-9a5a-4ae7-a9a6-ca1f24294b86"));
        oneSignalNotification.setData(Collections.singletonMap("foo","bar"));
        oneSignalNotification.setContents(Collections.singletonMap(LANGUAGES.ENGLISH.VALUE(),"English Message"));
        oneSignalNotification.setToIos(true);
        OneSignalNotificationResponse response = oneSignalClient.push(oneSignalNotification);
        System.out.println(response);
    }

    @Test
    public void  sendAsyncNotificationToExternalUser() {
        OneSignalClient oneSignalClient = OneSignalClientBuilder
                .newBuilder("5eb5a37e-b458-11e3-ac11-000c2940e62c","NGEwMGZmMjItY2NkNy0xMWUzLTk5ZDUtMDAwYzI5NDBlNjJj")
                .setAsync(true)
                .build();
        OneSignalNotification oneSignalNotification = new OneSignalNotification();
        oneSignalNotification.setIncludeExternalUserIds(Arrays.asList("6392d91a-b206-4b7b-a620-cd68e32c3a76","76ece62b-bcfe-468c-8a78-839aeaa8c5fa","8e0f21fa-9a5a-4ae7-a9a6-ca1f24294b86"));
        oneSignalNotification.setChannelForExternalUserIds(EXTERNAL_CHANNEL.PUSH);
        oneSignalNotification.setData(Collections.singletonMap("foo","bar"));
        oneSignalNotification.setContents(Collections.singletonMap(LANGUAGES.ENGLISH.VALUE(),"English Message"));
        oneSignalNotification.setToIos(true);
        oneSignalClient.push(oneSignalNotification, new OneSignalNotificationCallback() {
            @Override
            public void onSuccess(OneSignalNotification notification, OneSignalNotificationResponse response) {
                System.out.println(response);
            }

            @Override
            public void onFailure(OneSignalNotification notification, OneSignalNotificationResponse response) {
                System.out.println(response);
            }
        });
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
