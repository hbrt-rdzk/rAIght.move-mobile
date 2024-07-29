package com.raightmove.raightmove.ui.components

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raightmove.raightmove.ui.themes.DarkBronze

@Composable
fun ProgressIndicator() {
    return CircularProgressIndicator(
        color = DarkBronze,
        strokeWidth = 5.dp,
    )
}

@Preview
@Composable
fun PreviewProgressIndicator() {
    ProgressIndicator()
}