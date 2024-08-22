package com.raightmove.raightmove.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raightmove.raightmove.R
import com.raightmove.raightmove.ui.themes.Bronze
import com.raightmove.raightmove.ui.themes.Cream

val exerciseIcons = mapOf(
    "squat" to R.drawable.squat_icon,
    "lunges" to R.drawable.lunges_icon,
    "plank" to R.drawable.plank_icon
)


@Composable
fun PreviousTrainingButton(
    exercise: String, date: String, repsNum: Int, onClick: () -> Unit
) {

    return Button(
        modifier = Modifier.padding(15.dp),
        onClick = { onClick() },
        colors = ButtonColors(
            contentColor = Cream,
            containerColor = Bronze,
            disabledContentColor = Color.Black,
            disabledContainerColor = Color.Gray
        ),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(30.dp), painter = painterResource(
                    id = exerciseIcons.getOrDefault(
                        exercise, R.drawable.home_icon
                    )
                ), contentDescription = exercise + "icon"
            )
            Text(text = exercise.uppercase())
            Column {
                Text(text = date)
                Text(text = "Reps: $repsNum")
            }
        }
    }
}

@Preview
@Composable
fun PreviewPreviousTrainingButton() {
    return PreviousTrainingButton(exercise = "squat", date = "2024-09-12", repsNum = 12, {})
}