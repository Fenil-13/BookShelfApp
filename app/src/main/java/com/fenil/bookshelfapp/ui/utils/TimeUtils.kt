package com.fenil.bookshelfapp.ui.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

fun getYearFromTimestamp(timestamp: Long): Int {
    val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
    calendar.timeInMillis = timestamp * 1000 // Converting seconds to milliseconds
    return calendar.get(Calendar.YEAR)
}

fun getDateString(time: Long) : String{
    val simpleDateFormat = SimpleDateFormat("dd MMMM yyyy, HH:mm:ss", Locale.ENGLISH)
    return simpleDateFormat.format(time * 1000L)
}
