package com.example.chickenzillav2.ui.screens.game.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.sp

@Composable
fun ScoreBoard(
    score: Int,
    bgBitmap: ImageBitmap,
    font: android.graphics.Typeface?,
    topMarginPx: Float,
    sideMarginPx: Float,
    modifier: Modifier = Modifier
) {
    val density = LocalDensity.current
    val scale = 1f

    val widthDp = with(density) { (bgBitmap.width * scale).toDp() }
    val heightDp = with(density) { (bgBitmap.height * scale).toDp() }
    val topDp = with(density) { topMarginPx.toDp() }
    val rightDp = with(density) { sideMarginPx.toDp() }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .padding(top = topDp, end = rightDp)
            .size(widthDp, heightDp)
    ) {
        Image(
            bitmap = bgBitmap,
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxSize()
        )

        Text(
            text = score.toString(),
            fontSize = 40.sp,
            style = MaterialTheme.typography.titleLarge,
            color = Color.Black,

            )
    }
}