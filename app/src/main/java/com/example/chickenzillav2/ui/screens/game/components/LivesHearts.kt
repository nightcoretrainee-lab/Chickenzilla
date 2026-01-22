package com.example.chickenzillav2.ui.screens.game.components

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ImageBitmap

@Composable
fun LivesHearts(
    lives: Int,
    bitmaps: Map<String, ImageBitmap>,
    modifier: Modifier = Modifier
) {
    val heartFull = bitmaps["heart_full"]
    val heartBlack = bitmaps["heart_black"]

    if (heartFull == null || heartBlack == null) return

    Canvas(modifier = modifier) {
        val totalLives = 3
        val heartSpacing = 120f

        // 15% від висоти Canvas
        val heartsY = size.height * 0.12f

        for (i in 0 until totalLives) {

            // індекс справа наліво
            val reversedIndex = totalLives - 1 - i

            val heartBitmap =
                if (reversedIndex < lives) heartFull else heartBlack

            drawImage(
                image = heartBitmap,
                topLeft = Offset(
                    // справа наліво
                    x = size.width - 180f - (i * heartSpacing),
                    y = heartsY
                )
            )
        }
    }
}
