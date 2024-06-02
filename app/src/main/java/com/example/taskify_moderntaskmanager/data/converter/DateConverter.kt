package com.example.taskify_moderntaskmanager.data.converter

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Converts Date objects to Long and vice versa for Room database compatibility.
 */
open class DateConverter {
    /**
     * Formats a Date object into a string in the format "dd MMM" (e.g., "25 Dec").
     *
     * @param date The Date object to format.
     * @return The formatted date string or an empty string if the input is null.
     */
    fun formatDate(date: Date?): String {
        return date?.let {
            val sdf = SimpleDateFormat("dd MMM", Locale.getDefault())
            sdf.format(it)
        } ?: ""
    }

    /**
     * Converts a Long value representing milliseconds since the epoch to a Date object.
     *
     * @param dateLong The Long value representing the date.
     * @return The corresponding Date object or null if the input is null.
     */
    @TypeConverter
    fun toDate(dateLong: Long?): Date? {
        return dateLong?.let { Date(it) }
    }

    /**
     * Converts a Date object to a Long value representing milliseconds since the epoch.
     *
     * @param date The Date object to convert.
     * @return The Long value representing the date, or null if the input is null.
     */
    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }
}
