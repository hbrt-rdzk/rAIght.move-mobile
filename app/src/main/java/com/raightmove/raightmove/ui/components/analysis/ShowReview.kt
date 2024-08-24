package com.raightmove.raightmove.ui.components.analysis

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.raightmove.raightmove.models.Feedback
import com.raightmove.raightmove.models.Joint
import com.raightmove.raightmove.models.Training
import com.raightmove.raightmove.ui.themes.Bronze
import com.raightmove.raightmove.ui.themes.Cream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun ShowReview(
    exercise: String,
    joints: List<Joint>?,
    feedbacks: List<Feedback>?,
    getUserID: () -> String,
    addTraining: (Context, String, Training) -> Unit
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(feedbacks!!.get(0).toString())
        Button(
            onClick = {
                val current = LocalDateTime.now()
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                val currentDateTime = current.format(formatter)

                val training = Training(
                    exercise = exercise,
                    feedbacks = feedbacks.orEmpty(),
                    joints = joints.orEmpty(),
                    date = currentDateTime
                )
                val userId = getUserID()
                addTraining(context, userId, training)
            }, colors = ButtonColors(
                contentColor = Cream,
                containerColor = Bronze,
                disabledContentColor = Color.Black,
                disabledContainerColor = Color.Gray
            )
        ) {
            Text(text = "Save training")
        }
    }
}
