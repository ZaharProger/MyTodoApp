package com.example.mytodoapp.services

import java.text.SimpleDateFormat
import java.util.*

class DateTimeConverter {
    private val format = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.ROOT)
    private val _calendar = Calendar.getInstance()
    val calendar: Calendar
        get() = _calendar

    fun getCurrentDateTime(): Array<Int> {
        return arrayOf(
            calendar[Calendar.DAY_OF_MONTH],
            calendar[Calendar.MONTH],
            calendar[Calendar.YEAR],
            calendar[Calendar.HOUR_OF_DAY],
            calendar[Calendar.MINUTE]
        )
    }

    fun convertToString(data: Array<Int>, separator: String): String {
        return data.joinToString(separator) {
            if (it > 9) "$it" else "0$it"
        }
    }

    fun convertToTimeStamp(data: String): Long {
        return format.parse(data)!!.time
    }

    fun formatToDate(data: Long): String {
        return format.format(Date(data))
    }
}