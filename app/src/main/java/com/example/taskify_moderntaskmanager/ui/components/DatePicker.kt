package com.example.taskify_moderntaskmanager.ui.components

import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import java.util.Calendar
import java.util.Date

@Composable
fun datePicker(
    context: Context = LocalContext.current,
    onDateSelected: (Date) -> Unit
): DatePickerDialog {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    return DatePickerDialog(
        context,
        { _, mYear, mMonth, mDayOfMonth ->
            val selectedCalendar = Calendar.getInstance()
            selectedCalendar.set(mYear, mMonth, mDayOfMonth)
            onDateSelected(selectedCalendar.time)
        },
        year,
        month,
        day
    )
}
