package com.example.taskify_moderntaskmanager.ui.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * A utility function to format a given date into a string representation.
 *
 * @param date The date object to be formatted.
 * @return A formatted string representing the date in the "dd MMM" format using the default locale.
 */
fun DateFormatter(date: Date): String {
    return SimpleDateFormat("dd MMM", Locale.getDefault()).format(date)
}