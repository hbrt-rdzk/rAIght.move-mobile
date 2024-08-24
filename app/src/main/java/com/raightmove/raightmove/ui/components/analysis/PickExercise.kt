package com.raightmove.raightmove.ui.components.analysis

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
import com.raightmove.raightmove.ui.themes.Bronze
import com.raightmove.raightmove.ui.themes.Cream

@Composable
fun PickExercise(
    setState: (String) -> Unit, setExercise: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(
            onClick = {
                setExercise("squat")
                setState("video_analysis")
            }, colors = ButtonColors(
                contentColor = Cream,
                containerColor = Bronze,
                disabledContentColor = Color.Black,
                disabledContainerColor = Color.Gray
            )
        ) {
            Text(text = "Squat")
        }
        Button(
            onClick = {
                setExercise("lunges")
                setState("video_analysis")
            }, colors = ButtonColors(
                contentColor = Cream,
                containerColor = Bronze,
                disabledContentColor = Color.Black,
                disabledContainerColor = Color.Gray
            )
        ) {
            Text(text = "Lunges")
        }
        Button(
            onClick = {
                setExercise("plank")
                setState("video_analysis")
            }, colors = ButtonColors(
                contentColor = Cream,
                containerColor = Bronze,
                disabledContentColor = Color.Black,
                disabledContainerColor = Color.Gray
            )
        ) {
            Text(text = "Plank")
        }
    }
}
