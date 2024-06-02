package com.example.taskify_moderntaskmanager.data.converter

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

open class DateConverter {
    @TypeConverter
    fun toDate(dateLong: Long?): Date? {
        return dateLong?.let { Date(it) }
    }

    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }

    fun formatDate(date: Date?): String {
        return date?.let {
            val sdf = SimpleDateFormat("dd MMM", Locale.getDefault())
            sdf.format(it)
        } ?: ""
    }
}
