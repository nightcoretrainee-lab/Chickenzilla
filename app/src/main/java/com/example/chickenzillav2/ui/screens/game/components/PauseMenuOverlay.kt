package com.example.chickenzillav2.ui.screens.game.components

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntSize
import androidx.core.content.res.ResourcesCompat
import com.example.chickenzillav2.R
import com.example.chickenzillav2.ui.screens.game.GameViewModel

@Composable
fun PauseMenuOverlay(
    isGameOver: Boolean,
    screenSize: IntSize,
    bitmaps: Map<String, ImageBitmap>,
    score: Int,
    onResume: () -> Unit,
    onRestart: () -> Unit,
    onExit: () -> Unit,
    viewModel: GameViewModel? = null
) {
    val density = LocalDensity.current
    val context = LocalContext.current
    val backOffset = remember { Offset(60f, 80f) }

    val balooFont = remember {
        ResourcesCompat.getFont(context, R.font.baloo_bhai_2_bold)
    }

    val titleTextSizePx = with(density) {
        MaterialTheme.typography.titleLarge.fontSize.toPx()
    }

    val buttonTextSizePx = with(density) {
        MaterialTheme.typography.titleMedium.fontSize.toPx()
    }

    val textColor = Color(0xFF2B2B2B).toArgb()

    val titlePaint = remember {
        Paint().apply {
            isAntiAlias = true
            textAlign = Paint.Align.CENTER
            typeface = balooFont
        }
    }

    val buttonPaint = remember {
        Paint().apply {
            isAntiAlias = true
            textAlign = Paint.Align.CENTER
            typeface = balooFont
        }
    }

    titlePaint.textSize = titleTextSizePx
    titlePaint.color = textColor

    buttonPaint.textSize = buttonTextSizePx
    buttonPaint.color = textColor

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(isGameOver) {
                detectTapGestures { tap ->
                    val cx = screenSize.width / 2f
                    val cy = screenSize.height / 2f

                    val panel = bitmaps["panel"] ?: return@detectTapGestures
                    val btn = bitmaps["btn_bg"] ?: return@detectTapGestures
                    val back = bitmaps["back"] ?: return@detectTapGestures

                    val isBackHit =
                        tap.x in backOffset.x..(backOffset.x + back.width) &&
                                tap.y in backOffset.y..(backOffset.y + back.height)

                    val panelTop = cy - panel.height / 2f
                    val gameOverOffset = if (isGameOver) panel.height * 0.07f else 0f

                    val resumeY = panelTop + panel.height * 0.45f
                    val exitY = panelTop + panel.height * 0.65f
                    val playAgainY = panelTop + panel.height * 0.6f + gameOverOffset

                    fun hit(y: Float): Boolean =
                        tap.x in (cx - btn.width / 2f)..(cx + btn.width / 2f) &&
                                tap.y in (y - btn.height / 2f)..(y + btn.height / 2f)

                    when {
                        isGameOver && hit(playAgainY) -> onRestart()
                        !isGameOver && hit(resumeY) -> onResume()
                        !isGameOver && hit(exitY) -> {
                            viewModel?.exitAndSaveGame()
                            onExit()
                        }
                        isBackHit -> {
                            if (isGameOver) {
                                viewModel?.restartGame()
                            } else {
                                viewModel?.exitAndSaveGame()
                            }
                            onExit()
                        }
                    }
                }
            }
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {

            drawRect(Color.Black.copy(alpha = 0.6f))

            val cx = size.width / 2f
            val cy = size.height / 2f

            val panel = bitmaps["panel"] ?: return@Canvas
            val btn = bitmaps["btn_bg"] ?: return@Canvas
            val back = bitmaps["back"] ?: return@Canvas

            val panelTop = cy - panel.height / 2f
            val gameOverOffset = if (isGameOver) panel.height * 0.07f else 0f

            drawImage(
                panel,
                Offset(cx - panel.width / 2f, panelTop)
            )

            fun baseline(y: Float, paint: Paint): Float =
                y - (paint.fontMetrics.ascent + paint.fontMetrics.descent) / 2

            // ===== TITLE =====
            if (isGameOver) {
                val titleY1 = panelTop + panel.height * 0.2f + gameOverOffset
                val titleY2 = panelTop + panel.height * 0.3f + gameOverOffset
                val scoreY = panelTop + panel.height * 0.42f + gameOverOffset

                drawContext.canvas.nativeCanvas.drawText(
                    "GAME",
                    cx,
                    baseline(titleY1, titlePaint),
                    titlePaint
                )

                drawContext.canvas.nativeCanvas.drawText(
                    "OVER",
                    cx,
                    baseline(titleY2, titlePaint),
                    titlePaint
                )

                drawContext.canvas.nativeCanvas.drawText(
                    "Score: $score",
                    cx,
                    baseline(scoreY, buttonPaint),
                    buttonPaint
                )
            } else {
                val titleY = panelTop + panel.height * 0.22f

                drawContext.canvas.nativeCanvas.drawText(
                    "PAUSE",
                    cx,
                    baseline(titleY, titlePaint),
                    titlePaint
                )
            }

            // ===== BUTTONS =====
            if (isGameOver) {
                val playAgainY = panelTop + panel.height * 0.6f + gameOverOffset

                drawImage(
                    btn,
                    Offset(
                        cx - btn.width / 2f,
                        playAgainY - btn.height / 2f
                    )
                )

                drawContext.canvas.nativeCanvas.drawText(
                    "Play Again",
                    cx,
                    baseline(playAgainY, buttonPaint),
                    buttonPaint
                )
                drawImage(
                    back,
                    backOffset
                )

            } else {
                val resumeY = panelTop + panel.height * 0.45f
                val exitY = panelTop + panel.height * 0.65f

                drawImage(
                    btn,
                    Offset(cx - btn.width / 2f, resumeY - btn.height / 2f)
                )

                drawContext.canvas.nativeCanvas.drawText(
                    "Resume",
                    cx,
                    baseline(resumeY, buttonPaint),
                    buttonPaint
                )

                drawImage(
                    btn,
                    Offset(cx - btn.width / 2f, exitY - btn.height / 2f)
                )

                drawContext.canvas.nativeCanvas.drawText(
                    "Exit",
                    cx,
                    baseline(exitY, buttonPaint),
                    buttonPaint
                )
            }
        }
    }
}