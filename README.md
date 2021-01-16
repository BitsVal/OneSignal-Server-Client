# 服务端对OneSignal API集成

>  OneSignal官网: https://onesignal.com/
>
>  OneSignal API示例https://documentation.onesignal.com/docs/onesignal-api
>
>  OneSignal API基本说明: https://documentation.onesignal.com/reference



## OneSignal Server Rest Api Reference



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
