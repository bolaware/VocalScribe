package com.bolaware.feature_history.timestamp

import android.text.format.DateUtils

class AndroidTimestampFormatter : TimestampFormatter {
    override fun formatTimestamp(timestamp: Long): String {
        return DateUtils.getRelativeTimeSpanString(
            timestamp,
            System.currentTimeMillis(),
            DateUtils.MINUTE_IN_MILLIS
        ).toString()
    }
}
