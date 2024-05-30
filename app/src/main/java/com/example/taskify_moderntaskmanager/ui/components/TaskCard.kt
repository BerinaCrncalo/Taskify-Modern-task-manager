package com.example.taskify_moderntaskmanager.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.taskify_moderntaskmanager.data.models.Task
import com.example.taskify_moderntaskmanager.ui.utils.DateFormatter

@Composable
fun TaskCard(
    task: Task,
    onCheckedChange: (Task, Boolean) -> Unit,
    onDelete: (Task) -> Unit,
    onEdit: (Int) -> Unit,
    isChecked: Boolean,
    modifier: Modifier = Modifier,
    onRequestDetails: (Int) -> Unit
) {
    val isVisible by remember { mutableStateOf(true) }

    AnimatedVisibility(
        visible = isVisible,
        enter = slideInHorizontally(
            initialOffsetX = { fullWidth -> fullWidth },
            animationSpec = tween(durationMillis = 500)
        ),
        exit = slideOutHorizontally(
            targetOffsetX = { fullWidth -> -fullWidth },
            animationSpec = tween(durationMillis = 500)
        )
    ) {
        Card(shape = RoundedCornerShape(20.dp), modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp)
                .shadow(4.dp, shape = RoundedCornerShape(20.dp))
                .clickable { onRequestDetails(task.id) }) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Checkbox(
                    checked = isChecked,
                    onCheckedChange = { onCheckedChange(task, it) },
                    modifier = Modifier.padding(end = 16.dp)
                )
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Start,
                    color = Color.Black
                )
                Text(
                    text = DateFormatter(task.dueDate),
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.End,
                    color = Color.Black
                )
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = "Edit",
                    modifier = Modifier.clickable { onEdit(task.id) }
                )
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Delete",
                    modifier = Modifier.clickable { onDelete(task) }
                )
            }
        }
    }
}
