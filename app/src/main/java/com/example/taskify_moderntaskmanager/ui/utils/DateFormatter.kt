package com.example.taskify_moderntaskmanager.ui.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun DateFormatter(date: Date): String {
    return SimpleDateFormat("dd MMM", Locale.getDefault()).format(date)
}
