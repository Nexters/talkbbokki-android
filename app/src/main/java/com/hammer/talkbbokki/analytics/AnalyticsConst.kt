package com.hammer.talkbbokki.analytics

object AnalyticsConst {
    object Event {
        const val APP_LAUNCH = "app_launch"
        const val CLICK_CATEGORY = "click_category"
        const val CLICK_BOOKMARK_MENU = "click_bookmark_menu"
        const val CLICK_CARD_DOWNLOAD = "click_card_download"
        const val CLICK_CARD_SHARE = "click_card_share"
        const val CLICK_CARD_BOOKMARK = "click_card_bookmark"
    }

    object Key {
        const val FROM = "from"
        const val NOTIFICATION_INFO = "noti_info"
        const val CATEGORY = "category"
        const val IS_ACTIVE = "is_active"
        const val TOPIC_ID = "topic_id"
    }

    object Param {
        const val LAUNCH_FROM_PUSH = "push"
        const val LAUNCH_FROM_DYNAMIC_LINK = "dynamic_link"
    }
}
