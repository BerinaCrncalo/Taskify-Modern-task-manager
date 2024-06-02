package com.example.taskify_moderntaskmanager.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Composable function that displays a header with the day of the week and the current date.
 *
 * @param day The day of the week to be displayed in the header.
 */
@Composable
fun Header(day: String) {
    // Create a date format for formatting the current date
    val dateFormat = SimpleDateFormat("EEEE, dd MMM yyyy", Locale.getDefault())
    // Get the current date
    val currentDate = Date()
    // Format the current date
    val formattedDate = dateFormat.format(currentDate)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFECE7EE))
            .padding(start = 20.dp, top = 20.dp, bottom = 15.dp)
    ) {
        // Display the day of the week
        Text(
            text = day,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 35.sp,
            style = MaterialTheme.typography.bodyLarge
        )
        // Display the formatted current date
        Text(
            text = formattedDate,
            Modifier.padding(5.dp, 0.dp),
            fontWeight = FontWeight.Light,
            style = MaterialTheme.typography.headlineMedium,
            color = Color.Black
        )
    }
}

/**
 * Preview function for the Header composable.
 */
@Preview
@Composable
fun HeaderPreview() {
    // Preview the Header composable with a specific day
    Header(day = "Monday")
}
