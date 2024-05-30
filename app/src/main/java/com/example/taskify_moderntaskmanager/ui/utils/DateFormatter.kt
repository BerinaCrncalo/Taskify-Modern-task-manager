package com.example.taskify_moderntaskmanager.ui.utils

import java.text.SimpleDateFormat
import java.util.Date

fun DateFormatter(date: Date): String = SimpleDateFormat("dd-MM").format(date)
