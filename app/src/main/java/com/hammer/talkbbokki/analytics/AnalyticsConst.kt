package com.hammer.talkbbokki.analytics

object AnalyticsConst {
    object Event {
        // 앱 진입
        const val APP_LAUNCH = "app_launch"
        const val APP_LAUNCH_FROM_PUSH = "app_launch_from_push"
        const val APP_LAUNCH_FROM_LINK = "app_launch_from_link"

        // 메인 화면
        const val CLICK_CATEGORY = "click_category"
        const val CLICK_BOOKMARK_MENU = "click_bookmark_menu"

        // 카드 상세 화면
        const val SCREEN_CARD_DETAIL = "screen_card_detail"
        const val CLICK_CARD_DOWNLOAD = "click_card_download"
        const val CLICK_CARD_SHARE = "click_card_share"
        const val CLICK_CARD_BOOKMARK = "click_card_bookmark"
    }

    object Key {
        const val NOTIFICATION_INFO = "noti_info"
        const val CATEGORY_TITLE = "category_title"
        const val TOPIC_ID = "topic_id"
        const val TOGGLE = "toggle"
    }
}
