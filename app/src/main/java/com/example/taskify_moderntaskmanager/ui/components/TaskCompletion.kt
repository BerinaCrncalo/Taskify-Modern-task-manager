package com.example.taskify_moderntaskmanager.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskify_moderntaskmanager.R

/**
 * Composable function to display a screen indicating task completion.
 * This screen shows a checkmark image and congratulatory messages.
 */
@Composable
fun TaskCompletionScreen() {
    // Load the checkmark image from resources
    val checkImage = painterResource(id = R.drawable.checkimage)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFECE7EE)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Display the checkmark image
        Image(
            painter = checkImage,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape)
        )
        // Text displaying tasks completed message
        Text(
            text = stringResource(R.string.tasks_completed),
            fontSize = 20.sp,
            fontWeight = FontWeight.W900,
            modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)
        )
        // Text displaying congratulatory message
        Text(
            text = stringResource(R.string.amazing_job),
            fontSize = 15.sp
        )
    }
}

/**
 * Preview function for TaskCompletionScreen.
 */
@Preview
@Composable
fun TaskCompletionScreenPreview() {
    // Preview the TaskCompletionScreen
    TaskCompletionScreen()
}
