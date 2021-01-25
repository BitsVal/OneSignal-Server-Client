# 服务端对OneSignal API集成

>  OneSignal官网: https://onesignal.com/
>
>  OneSignal API示例https://documentation.onesignal.com/docs/onesignal-api
>
>  OneSignal API基本说明: https://documentation.onesignal.com/reference



- [x] Create Notification iOS
- [ ] Cancel Notification
- [ ] View Apps
- [ ] ........

## 引入和使用

```xml
    <dependency>
        <groupId>com.upuphub.library</groupId>
        <artifactId>onesignal-server</artifactId>
        <version>1.0.0</version>
    </dependency>
```
- 同步调用
```java
public class demo {
    public void demoSyncNotificationToExternalUser() {
        OneSignalClient oneSignalClient = OneSignalClientBuilder
                .newBuilder("APP-ID","REST-APP-TOKEN")
                .build();
        OneSignalNotification oneSignalNotification = new OneSignalNotification();
        oneSignalNotification.setIncludePlayerIds(Arrays.asList("6392d91a-b206-4b7b-a620-cd68e32c3a76", "76ece62b-bcfe-468c-8a78-839aeaa8c5fa", "8e0f21fa-9a5a-4ae7-a9a6-ca1f24294b86"));
        oneSignalNotification.setData(Collections.singletonMap("foo", "bar"));
        oneSignalNotification.setContents(Collections.singletonMap(LANGUAGES.ENGLISH.VALUE(), "English Message"));
        oneSignalNotification.setToIos(true);
        OneSignalNotificationResponse response = oneSignalClient.push(oneSignalNotification);
        System.out.println(response);
    }
}
```
- 异步调用
```java
public class demo {
    public void demoAsyncNotificationToExternalUser() {
        OneSignalClient oneSignalClient = OneSignalClientBuilder
            .newBuilder("APP-ID","REST-APP-TOKEN")
            .setAsync(true)
            .build();
        OneSignalNotification oneSignalNotification = new OneSignalNotification()
            .setIncludeExternalUserIds(Collections.singletonList("testExternalUserId"))
            .setChannelForExternalUserIds(EXTERNAL_CHANNEL.PUSH.VALUE())
            .setData(Collections.singletonMap("key","value"))
            .setIosAttachments(Collections.singletonMap("id1","https://img.upuphub.com/a9996a4a4c70b1f8eb3096b0e215f0ac.jpg"))
            .setHeadings(Collections.singletonMap(LANGUAGES.ENGLISH.VALUE(),"image 1"))
            .setContents(Collections.singletonMap(LANGUAGES.ENGLISH.VALUE(),"XXXXX"))
            .setSubtitle(Collections.singletonMap(LANGUAGES.ENGLISH.VALUE(),"ios 10+ subtitle"))
            .setToIos(true);
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
    }
}
```

## OneSignalClient Builder Config

```java
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
}
```

| Builder Key            | 说明                   |
| ---------------------- | ---------------------- |
| authKey                | Rest Api Token         |
| appId                  | OneSignal App Id       |
| async                  | 异步模式 默认同步      |
| apiUrl                 | OneSignal Api Root Url |
| timeUnit               | 时间单位               |
| readTimeout            | 读超时时间             |
| connectTimeout         | 连接超时时间           |
| connectionPool         | 连接池                 |
| maxIdleConnections     | 最大连接数             |
| keepAliveDuration      | 保活连接数             |
| maxRetryCount          | 最大重试次数           |
| interceptors           | 请求拦截器             |
| netWorkingInterceptors | 网络拦截器             |
| eventListener          | 事件监听器             |

## OneSignalClient Interface Api

````java
/**
 * Interface for general purpose OneSignal Message Client
 *
 * @author Inspiration S.P.A Leo
 * @date create time 2021-01-15 15:39
 **/
public interface OneSignalClient {
    /**
     * 是否是异步的返回判断
     * 
     * @return 是否是异步的返回判断
     */
    boolean isAsync();

    /**
     * 同步发送OneSignal Push 消息
     *
     * @param oneSignalNotification OneSignal Push内容
     * @return 发送的Push消息的发送返回结果
     */
    OneSignalNotificationResponse push(OneSignalNotification oneSignalNotification);

    /**
     * 异步发送OneSignal Push 消息
     * 
     * @param oneSignalNotification OneSignal Push内容
     * @param callback 发送的Push消息的发送返回回调
     */
    void push(OneSignalNotification oneSignalNotification, OneSignalNotificationCallback callback);
}

````

## OneSignal Server Rest Api Reference

### Create Notification
`OneSignalNotification` 常用参数和说明

#### Send to Specific Devices
You may also target specific devices. Targeting devices is typically used in two ways:

For notifications that target individual users, such as if they've received a message from someone.

For apps that wish to manage their own segments, such as tracking a user's followers and sending notifications to them when that user posts.

When targeting specific devices, you may use any of the following parameters together:



| Parameter                 | Type         | Description                                                  |
| ------------------------- | ------------ | ------------------------------------------------------------ |
| include_player_ids        | array_string | Specific player*ids to send your notification to. _Does not require API Auth Key.* Do not combine with other targeting parameters. Not compatible with any other targeting parameters.Example: `["1dd608f2-c6a1-11e3-851d-000c2940e62c"]` |
| include_external_user_ids | array_string | Target specific devices by custom user IDs assigned via API.Not compatible with any other targeting parameters,Example: `[“custom-id-assigned-by-api”]` **REQUIRED**: *REST API Key Authentication*,*Limit of 2,000 entries per REST API call.* |

####  Common Parameters

| Parameter       | Type          | Platform   | Description                                                  |
| --------------- | ------------- | ---------- | ------------------------------------------------------------ |
| app_id          | string        | All        | **Required:** Your OneSignal Application ID, which can be found in [Keys & IDs](https://documentation.onesignal.com/docs/accounts-and-keys).It is a UUID and looks similar to `8250eaf6-1a58-489e-b136-7c74a864b434`. |
| external_id     | string (UUID) | All        | Correlation and idempotency key.A request received with this parameter will first look for another notification with the same `external_id`. If one exists, a notification will not be sent, and result of the previous operation will instead be returned. Therefore, if you plan on using this feature, it's important to use a good source of randomness to generate the UUID passed here.**This key is only idempotent for 30 days.** After 30 days, the notification could be removed from our system and a notification with the same `external_id` will be sent again. |
| contents        | object        | All - Push | Required unless `content_available=true` or `template_id` is set. The notification's content (excluding the title), a map of language codes to text for each language.Each hash must have a language code string for a key, mapped to the localized text you would like users to receive for that language.<br/>**This field supports [inline substitutions](https://documentation.onesignal.com/docs/personalization).**<br/>**English must be included in the hash**.Example: `{"en": "English Message", "es": "Spanish Message"}` |
| headings        | object        | All - Push | The notification's title, a map of language codes to text for each language. Each hash must have a language code string for a key, mapped to the localized text you would like users to receive for that language.**This field supports [inline substitutions](https://documentation.onesignal.com/docs/personalization).**Example: `{"en": "English Title", "es": "Spanish Title"}` |
| subtitle        | object        | iOS 10+    | The notification's subtitle, a map of language codes to text for each language. Each hash must have a language code string for a key, mapped to the localized text you would like users to receive for that language.**This field supports [inline substitutions](https://documentation.onesignal.com/docs/personalization).**Example: `{"en": "English Subtitle", "es": "Spanish Subtitle"}` |
| template_id     | string        | All        | Use a template you setup on our dashboard. The template_id is the UUID found in the URL when viewing a template on our dashboard.Example: `be4a8044-bbd6-11e4-a581-000c2940e62c` |
| data            | object        | All        | A custom map of data that is passed back to your app. Same as using [Additional Data](https://documentation.onesignal.com/docs/sending-notifications#advanced-settings) within the dashboard. Can use up to `2048` bytes of data.Example: `{"abc": 123, "foo": "bar", "event_performed": true, "amount": 12.1}` |
| url             | string        | All        | The URL to open in the browser when a user clicks on the notification.Example: `https://onesignal.com`Note: iOS needs https or updated NSAppTransportSecurity in plist.**This field supports [inline substitutions](https://documentation.onesignal.com/docs/personalization).**Omit if including `web_url` or `app_url` |
| ios_attachments | object        | iOS 10+    | Adds media attachments to notifications. Set as JSON object, key as a media id of your choice and the value as a valid local filename or URL. User must press and hold on the notification to view.Do not set `mutable_content` to download attachments. The OneSignal SDK does this automatically.Example: `{"id1": "https://domain.com/image.jpg"}` |
| isIos           | boolean       | iOS        | Indicates whether to send to all devices registered under your app's Apple iOS platform. |
| isAndroid       | boolean       | Android    | Indicates whether to send to all devices registered under your app's Google Android platform. |


## Rate Limits & Disabled Apps

In extremely rare cases, OneSignal may automatically disable an app due to a rate limit. The rate limit is meant to trigger as a safety measure in case customers accidentally make an error when integrating with the OneSignal API that causes excessive notifications. Rate limiting is designed solely as a solution to prevent end users from getting spammed with notifications that the customer did not know they were sending, and is not intended to affect customers in other cases.

### Scenarios where rate limiting triggers

The following are scenarios where rate limiting may or may not be triggered, based on an app with exactly 1000 messagable users:

| Scenario                                                     | Rate limit reached?                                          |
| :----------------------------------------------------------- | :----------------------------------------------------------- |
| Sending 1 notification to 1,000 recipients?                  | No, rate limit not reached.  If your app has 1000 messageable users, you can send up to 9,999 notifications within a 15 minute period. However, sending to one more recipient would trigger the rate limit. |
| Sending 10 notifications to 1,000 recipient each ?           | Yes, rate limit is reached.  The rate limit is 10 x messageable users within 15 minutes.  10 x 1,000 = 10,000 you should not be sending 10 notifications per user within 15 minute periods. |
| Sending 10,000 notifications to 1 recipient each in a 15-minute period? | Yes, rate limit is reached.  Ten times the number of messageable recipients have been delivered to. |
| Sending 9 notifications to 1,000 recipients each in a 14-minute period, and then sending 1 notification to 1,000 recipients after 1 minute? | No.  The last notification is counted using a new 15-minute window. |
| Sending 9 notifications to 1,000 recipients each in a 14-minute period, and then sending another 9 notification to 1,000 recipients after 1 minute? | No.  The 15-minute window is static and does not roll over from minute to minute. |

## Idempotent Notification Requests

The notifications create api supports idempotent operations to avoid sending the same notification twice. This can be useful when a request is interrupted mid request, or there is an error processing the response.

For example consider the case of:

1. A `notifications#create` call is made
2. OneSignal processes the request and begins sending the notification
3. A network error occurs and no response is ever received by your client.

Without some kind of correlation key, there is no way to verify the last notification did indeed send. If the notification did send and the request is sent again, your users will see duplicate notifications. If it did not send and the request is not retried, your users might miss important notifications. Fortunately, the `external_id`field exists on notifications for precisely this reason.

`external_id` is simply an optional parameter to the `notifications#create` request that can be used to correlate the notification to your system, and more importantly, ensure that duplicate notifications are not sent.

In the example case above, the flow might look something like this:

1. A `notifications#create`call is made with `external_id`
2. OneSignal processes the request and begins sending the notification
3. A network error occurs and no response is ever received by your client.
4. A `notifications#create` call is made again with the same `external_id` as the previous one.
5. OneSignal recognizes the notification has already begun sending, and returns the result of the previous operation.

The above can happen any number of times. As long as the `external_id` is the same in each request, only 1 notification will be delivered to your users regardless of the number of times the request is sent to our api.

Because the api will not send a notification if the `external_id` already exists in our database, it's important to use a good source of randomness when generating the uuid passed.

It's important to note this key is only idempotent for 30 days. After 30 days, the notification could be removed from our system and a notification with the same external_id` will be sent again.

## OneSignal FAQ

### What are the API Rate Limits?

When sending push through our API, it is always recommended to include as many devices as possible within each request.

If you are using the `include_player_ids` or `include_external_user_ids` you can make these requests to individual users at rates of 50 requests per second.

If you use segments or filters, we recommend no more than 20 requests per second.

| Sending Push Notifications                                   | Updating Tags/Devices                                        | Notification History                                       |
| :----------------------------------------------------------- | :----------------------------------------------------------- | :--------------------------------------------------------- |
| **Hard Limit:** You cannot send more than 10x the amount of subscribed users you have within 15 minutes. More details in [Rate Limits & Disabled Apps](https://documentation.onesignal.com/docs/disabled-apps). | Our [Edit device](https://documentation.onesignal.com/reference-link/edit-device) and [Edit tags with external user id](https://documentation.onesignal.com/reference-link/edit-tags-with-external-user-id) endpoints recommended rate is no more than 1000 requests per second. | Please keep number of parallel requests under 100 GB/file. |

###  What is the response timeout for API endpoints?

Responses are usually generated within a couple seconds. However, in extreme cases, they can take longer.

OneSignal will wait 30 seconds for a response before automatically canceling the request. To verify no duplicate requests go through, you can add an [Idempotent Key](https://documentation.onesignal.com/docs/idempotent-notification-requests) with the `external_id` parameter.

### Tag Filter Target and Exclude By Topics

#### 1. Figure out Tag categories

More details in our [Add Data Tags](https://documentation.onesignal.com/docs/add-user-data-tags), for example:

```
"breaking": 1
"sports": 1
"finance": 1
"politics": 1
```

The tag "key" will be the topic and the value can be whatever you like. In the example: "1" can indicate how many times the [user visited the topic](https://documentation.onesignal.com/docs/auto-segment-users-by-page-visit) or you can use [Time Operators](https://documentation.onesignal.com/docs/time-operators) to know how long since they last viewed the topic. Another common setup is to [Tag based on Subscription page](https://documentation.onesignal.com/docs/auto-segment-users-by-subscription-page).

#### 2. Target by filters

For news that crosses both "politics" and "breaking" you can use [API filters](https://documentation.onesignal.com/reference/create-notification#send-to-users-based-on-filters) to target these users without overlap.

For example:

```
filters: [
  {"field": "tag", "key": "politics", "relation": "exists"},
  {"operator": "OR"},
  {"field": "tag", "key": "breaking", "relation": "exists"}
]
```

In this example, all users with either the "politics" or "breaking" tags will get the notification. Our system will automatically make sure no users with both tags will get it twice as long as they are targeted in the same API call.

#### 3. Exclude Segments

If you later want to send this same article to "finance" users, you can make sure none of them get duplicates if you use the example:

```
filters: [
  {"field": "tag", "key": "politics", "relation": "not_exists"},
  {"field": "tag", "key": "breaking", "relation": "not_exists"},
  {"field": "tag", "key": "finance", "relation": "exists"}
]
```

This will make sure that only users with the "finance" tag will get the push.