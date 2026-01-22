package com.example.chickenzillav2.ui.screens.game.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.IntOffset
import com.example.chickenzillav2.ui.screens.game.components.ScoreBoard
import kotlin.math.roundToInt

@Composable
fun GameHUD(
    score: Int,
    lives: Int,
    bitmaps: Map<String, ImageBitmap>,
    font: android.graphics.Typeface?
) {
    Box(modifier = Modifier.fillMaxSize()) {
        val topMargin = 80f
        val sideMargin = 60f

        // Pause Button
        bitmaps["pause_btn"]?.let { btn ->
            Image(
                bitmap = btn,
                contentDescription = "Pause",
                modifier = Modifier
                    .offset { IntOffset(sideMargin.roundToInt(), topMargin.roundToInt()) }
            )
        }

        // Score Board
        bitmaps["score_bg"]?.let { bg ->
            Box(modifier = Modifier.fillMaxSize()) {

                ScoreBoard(
                    score = score,
                    bgBitmap = bg,
                    font = font,
                    topMarginPx = topMargin,
                    sideMarginPx = sideMargin,
                    modifier = Modifier.align(Alignment.TopEnd)
                )

            }

        }

        // Lives
        LivesHearts(
            lives = lives,
            bitmaps = bitmaps,
            modifier = Modifier.fillMaxSize()
        )
    }
}
