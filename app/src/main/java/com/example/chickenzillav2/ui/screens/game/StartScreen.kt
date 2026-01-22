package com.example.chickenzillav2.ui.screens.game

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.sp
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.example.chickenzillav2.R
import com.example.chickenzillav2.ui.screens.game.components.FallingObjectsLayer
import com.example.chickenzillav2.ui.screens.game.components.GameHUD
import com.example.chickenzillav2.ui.screens.game.components.LivesHearts
import com.example.chickenzillav2.ui.screens.game.components.PauseMenuOverlay
import com.example.chickenzillav2.ui.screens.game.components.PlayerCharacter
import com.example.chickenzillav2.ui.screens.game.components.loadGameBitmaps
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlin.math.roundToInt

// --- MAIN SCREEN ---
@Composable
fun GameScreen(
    onBackToMenu: () -> Unit,
    // Впроваджуємо ViewModel (створюється автоматично або передається)
    context: Context = LocalContext.current
) {
    val viewModel: GameViewModel = remember {
        GameViewModel(context)
    }
    val lifecycleOwner = LocalLifecycleOwner.current

    // Ресурси
    val balooFont = remember { ResourcesCompat.getFont(context, R.font.baloo_bhai_2_bold) }

    // Розмір екрану
    var screenSize by remember { mutableStateOf(IntSize.Zero) }

    BackHandler {
        if (!viewModel.isGameOver) {
            // Якщо гра активна, тоглюємо паузу
            viewModel.isPaused = !viewModel.isPaused
        } else {
            // Якщо game over, виходимо
            viewModel.restartGame()
            onBackToMenu()
        }
    }

    if (screenSize.width == 0) {
        Box(modifier = Modifier.fillMaxSize().onSizeChanged {
            screenSize = it
            // Ініціалізуємо гравця по центру, коли дізналися ширину
            viewModel.initPlayerPosition(it.width)
        })
        return
    }

    // Завантаження картинок
    val bitmaps = loadGameBitmaps(context, screenSize.width, screenSize.height)

    // --- LIFECYCLE OBSERVER ---
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_PAUSE && !viewModel.isGameOver) {
                viewModel.isPaused = true
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

    // --- GAME LOOP ---
    // Цикл залишається в UI, бо він керує частотою кадрів,
    // але логіку виконує ViewModel
    LaunchedEffect(Unit) {
        while (isActive) {
            val startTime = System.nanoTime()

            // Викликаємо оновлення логіки у ViewModel
            viewModel.updateGameLogic(screenSize)

            val frameDuration = (System.nanoTime() - startTime) / 1_000_000
            val delayTime = (16 - frameDuration).coerceAtLeast(0)
            delay(delayTime)
        }
    }

    // --- UI STRUCTURE ---
    Box(
        modifier = Modifier
            .fillMaxSize()
            // Жести передаємо у ViewModel
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    viewModel.onDrag(dragAmount.x, screenSize.width)
                }
            }
            .pointerInput(Unit) {
                detectTapGestures { offset ->
                    viewModel.onTap(offset)
                }
            }
    ) {
        // 1. Background
        GameBackground(bitmaps["bg"])

        // 2. Falling Objects (Canvas)
        // Дані беремо з viewModel.items
        FallingObjectsLayer(
            items = viewModel.items,
            bitmaps = bitmaps,
            trigger = viewModel.gameStateTrigger
        )

        // 3. Player
        // Дані беремо з viewModel
        PlayerCharacter(
            bitmap = bitmaps["player_right"],
            x = viewModel.playerX,
            y = viewModel.getPlayerYPosition(screenSize.height), // Позиція з ViewModel
            isFacingRight = viewModel.isFacingRight
        )

        // 4. HUD (Score, Lives)
        GameHUD(
            score = viewModel.score,
            lives = viewModel.lives,
            bitmaps = bitmaps,
            font = balooFont
        )

        // 5. Menu Overlay
        if (viewModel.isPaused || viewModel.isGameOver) {
            PauseMenuOverlay(
                isGameOver = viewModel.isGameOver,
                screenSize = screenSize,
                bitmaps = bitmaps,
                score = viewModel.score,
                onResume = { viewModel.resumeGame() },
                onRestart = { viewModel.restartGame() },
                onExit = onBackToMenu,
                viewModel = viewModel
            )
        }
    }
}

// --- SUB-COMPOSABLES (Майже без змін, лише чистіші) ---

@Composable
fun GameBackground(bitmap: ImageBitmap?) {
    if (bitmap != null) {
        Image(
            bitmap = bitmap,
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )
    }
}


