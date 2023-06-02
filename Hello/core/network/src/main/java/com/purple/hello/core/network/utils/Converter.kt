package com.purple.hello.core.network.utils

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

@RequiresApi(Build.VERSION_CODES.O)
fun String.toLocalDateTime(): LocalDateTime? {
    return try {
        val offsetDateTime = OffsetDateTime.parse(this)
        val zoneOffset = ZoneOffset.ofTotalSeconds(offsetDateTime.offset.totalSeconds)
        offsetDateTime.toLocalDateTime().atOffset(zoneOffset).toLocalDateTime()
    } catch (e: DateTimeParseException) {
        Log.d("parsing Error", e.message ?: "")
        null
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun String.toLocalDateTime(datePattern: String): LocalDateTime? {
    return try {
        LocalDateTime.parse(this, DateTimeFormatter.ofPattern(datePattern))
    } catch (e: DateTimeParseException) {
        null
    }
}
