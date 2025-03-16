package com.bolaware.feature_history.timestamp

interface TimestampFormatter {
    fun formatTimestamp(timestamp: Long): String
}
