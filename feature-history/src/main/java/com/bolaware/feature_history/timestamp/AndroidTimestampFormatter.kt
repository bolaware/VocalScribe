package com.bolaware.feature_history.timestamp

import android.text.format.DateUtils
import javax.inject.Inject

class AndroidTimestampFormatter @Inject constructor() : TimestampFormatter {
    override fun formatTimestamp(timestamp: Long): String {
        return DateUtils.getRelativeTimeSpanString(
            timestamp,
            System.currentTimeMillis(),
            DateUtils.MINUTE_IN_MILLIS
        ).toString()
    }
}
