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

import java.util.List;
import java.util.Map;

/**
 * onesignal notification builder
 *
 * @author  Upuphub iWzl
 * @date create time 2021-01-16 13:37
 **/
public class OneSignalNotificationBuilder {

    private class IosBuilder{
        /**
         * Required: Your OneSignal Application ID, which can be found in Keys & IDs.
         *
         * It is a UUID and looks similar to 8250eaf6-1a58-489e-b136-7c74a864b434.
         */
        private String appId;

        /**
         * Target specific devices by custom user IDs assigned via API.
         * Not compatible with any other targeting parameters
         * Example: [“custom-id-assigned-by-api”]
         *
         * REQUIRED: REST API Key Authentication
         *
         * Limit of 2,000 entries per REST API call.
         * Note: If targeting push and email subscribers with same ids,
         * use with channel_for_external_user_ids to indicate you are sending a push or email.
         */
        private List<String> includeExternalUserIds;

        /**
         * A custom map of data that is passed back to your app.
         * Same as using Additional Data within the dashboard. Can use up to 2048 bytes of data.
         *
         * Example: {"abc": 123, "foo": "bar", "event_performed": true, "amount": 12.1}
         */
        private Map<String,String> data;

        /**
         * The URL to open in the browser when a user clicks on the notification.
         * Example: https://onesignal.com
         *
         * Note: iOS needs https or updated NSAppTransportSecurity in plist
         *
         * This field supports inline substitutions.
         * Omit if including web_url or app_url
         */
        private String url;

        /**
         * Delivery priority through the push server (example GCM/FCM).
         * Pass 10 for high priority or any other integer for normal priority.
         * Defaults to normal priority for Android and high for iOS.
         * For Android 6.0+ devices setting priority to high will wake the device out of doze mode.
         *
         */
        private int priority;

        /**
         * {@link EXTERNAL_CHANNEL}
         */
        private String channelForExternalUserIds;

        /**
         * Specific player ids to send your notification to. _Does not require API Auth Key.
         * Do not combine with other targeting parameters. Not compatible with any other targeting parameters.
         *
         * Example: ["1dd608f2-c6a1-11e3-851d-000c2940e62c"]
         * Limit of 2,000 entries per REST API call
         */
        private List<String> includePlayerIds;

        /**
         * Required unless content_available=true or template_id is set.
         *
         * The notification's content (excluding the title),
         * a map of language codes to text for each language.
         *
         * Each hash must have a language code string for a key,
         * mapped to the localized text you would like users to receive for that language.
         * This field supports inline substitutions.
         * English must be included in the hash.
         *
         * Example: {"en": "English Message", "es": "Spanish Message"}
         */
        private Map<String, String> contents;

        private List<String> includedSegments;

        /**
         * Use a template you setup on our dashboard.
         * The template_id is the UUID found in the URL when viewing a template on our dashboard.
         *
         * Example: be4a8044-bbd6-11e4-a581-000c2940e62c
         */
        private String templateId;

        /**
         * The notification's title, a map of language codes to
         * text for each language. Each hash must have a language
         * code string for a key, mapped to the localized text you
         * would like users to receive for that language.
         *
         * This field supports inline substitutions.
         *
         * Example: {"en": "English Title", "es": "Spanish Title"}
         */
        private Map<String,String> headings;

        /**
         * The notification's subtitle, a map of language codes to text for each language. Each hash must have a language code string for a key, mapped to the localized text you would like users to receive for that language.
         * This field supports inline substitutions.
         *
         * Example: {"en": "English Subtitle", "es": "Spanish Subtitle"}
         *
         * limited iOS 10+
         */
        private Map<String, String> subtitle;

        /**
         * Correlation and idempotency key.
         *
         * A request received with this parameter will first look for
         * another notification with the same external_id. If one exists,
         * a notification will not be sent, and result of the previous
         * operation will instead be returned. Therefore, if you plan
         * on using this feature, it's important to use a good source
         * of randomness to generate the UUID passed here.
         *
         * This key is only idempotent for 30 days. After 30 days,
         * the notification could be removed from our system and
         * a notification with the same external_id will be sent again.
         *
         * See Idempotent Notification Requests for more details
         */
        private String externalId;
    }

    private class AndroidBuilder{
        /**
         * Required: Your OneSignal Application ID, which can be found in Keys & IDs.
         *
         * It is a UUID and looks similar to 8250eaf6-1a58-489e-b136-7c74a864b434.
         */
        private String appId;

        /**
         * Target specific devices by custom user IDs assigned via API.
         * Not compatible with any other targeting parameters
         * Example: [“custom-id-assigned-by-api”]
         *
         * REQUIRED: REST API Key Authentication
         *
         * Limit of 2,000 entries per REST API call.
         * Note: If targeting push and email subscribers with same ids,
         * use with channel_for_external_user_ids to indicate you are sending a push or email.
         */
        private List<String> includeExternalUserIds;

        /**
         * A custom map of data that is passed back to your app.
         * Same as using Additional Data within the dashboard. Can use up to 2048 bytes of data.
         *
         * Example: {"abc": 123, "foo": "bar", "event_performed": true, "amount": 12.1}
         */
        private Map<String,String> data;

        /**
         * The URL to open in the browser when a user clicks on the notification.
         * Example: https://onesignal.com
         *
         * Note: iOS needs https or updated NSAppTransportSecurity in plist
         *
         * This field supports inline substitutions.
         * Omit if including web_url or app_url
         */
        private String url;

        /**
         * Adds media attachments to notifications. Set as JSON object,
         * key as a media id of your choice and the value as a valid local
         * filename or URL. User must press and hold on the notification to view.
         *
         * Do not set mutable_content to download attachments. The OneSignal SDK does this automatically
         *
         * Example: {"id1": "https://domain.com/image.jpg"}
         *
         * limited iOS 10+
         */
        private Map<String, String> iosAttachments;

        /**
         * Picture to display in the expanded view. Can be a drawable resource name or a URL.
         *
         * limited Android
         */
        private String bigPicture;

        /**
         * Allowing setting a background image for the notification.
         * This is a JSON object containing the following keys.
         * See our Background Image documentation for image sizes.
         *
         * limited Android
         */
        private OneSignalNotification.AndroidBackgroundLayout androidBackgroundLayout;

        /**
         * Delivery priority through the push server (example GCM/FCM).
         * Pass 10 for high priority or any other integer for normal priority.
         * Defaults to normal priority for Android and high for iOS.
         * For Android 6.0+ devices setting priority to high will wake the device out of doze mode.
         *
         */
        private int priority;

        /**
         * {@link EXTERNAL_CHANNEL}
         */
        private String channelForExternalUserIds;

        /**
         * Specific player ids to send your notification to. _Does not require API Auth Key.
         * Do not combine with other targeting parameters. Not compatible with any other targeting parameters.
         *
         * Example: ["1dd608f2-c6a1-11e3-851d-000c2940e62c"]
         * Limit of 2,000 entries per REST API call
         */
        private List<String> includePlayerIds;

        /**
         * Required unless content_available=true or template_id is set.
         *
         * The notification's content (excluding the title),
         * a map of language codes to text for each language.
         *
         * Each hash must have a language code string for a key,
         * mapped to the localized text you would like users to receive for that language.
         * This field supports inline substitutions.
         * English must be included in the hash.
         *
         * Example: {"en": "English Message", "es": "Spanish Message"}
         */
        private Map<String, String> contents;

        private List<String> includedSegments;

        /**
         * 	Indicates whether to send to all devices registered under your app's Apple iOS platform.
         */
        private Boolean toIos;

        /**
         * Indicates whether to send to all devices registered under your app's Google Android platform.
         */
        private Boolean toAndroid;

        /**
         * Use a template you setup on our dashboard.
         * The template_id is the UUID found in the URL when viewing a template on our dashboard.
         *
         * Example: be4a8044-bbd6-11e4-a581-000c2940e62c
         */
        private String templateId;

        /**
         * The notification's title, a map of language codes to
         * text for each language. Each hash must have a language
         * code string for a key, mapped to the localized text you
         * would like users to receive for that language.
         *
         * This field supports inline substitutions.
         *
         * Example: {"en": "English Title", "es": "Spanish Title"}
         */
        private Map<String,String> headings;

        /**
         * The notification's subtitle, a map of language codes to text for each language. Each hash must have a language code string for a key, mapped to the localized text you would like users to receive for that language.
         * This field supports inline substitutions.
         *
         * Example: {"en": "English Subtitle", "es": "Spanish Subtitle"}
         *
         * limited iOS 10+
         */
        private Map<String, String> subtitle;

        /**
         * Correlation and idempotency key.
         *
         * A request received with this parameter will first look for
         * another notification with the same external_id. If one exists,
         * a notification will not be sent, and result of the previous
         * operation will instead be returned. Therefore, if you plan
         * on using this feature, it's important to use a good source
         * of randomness to generate the UUID passed here.
         *
         * This key is only idempotent for 30 days. After 30 days,
         * the notification could be removed from our system and
         * a notification with the same external_id will be sent again.
         *
         * See Idempotent Notification Requests for more details
         */
        private String externalId;
    }

    private class CommonBuilder{

    }


}
