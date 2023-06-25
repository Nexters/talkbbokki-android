package com.hammer.talkbbokki.presentation.report

enum class ReportReasonKeyword(val keyword: String) {
    ABUSE("욕설/비방"),
    OBSCENE("음란물"),
    ADVERTISING("광고"),
    ETC("기타");

    companion object {
        fun getReportReasons() = ReportReasonKeyword.values()
    }
}

data class ReportReasonItem(
    val reason: ReportReasonKeyword,
    val checked: Boolean
)
