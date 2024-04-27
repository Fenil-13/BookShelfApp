package com.fenil.bookshelfapp.ui.utils

import java.util.Calendar
import java.util.TimeZone

fun getYearFromTimestamp(timestamp: Long): Int {
    val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
    calendar.timeInMillis = timestamp * 1000 // Converting seconds to milliseconds
    return calendar.get(Calendar.YEAR)
}