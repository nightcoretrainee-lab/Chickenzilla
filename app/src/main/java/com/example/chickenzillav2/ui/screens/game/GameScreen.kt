package com.example.chickenzillav2.ui.screens.game

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntSize
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LifecycleEventEffect
import com.example.chickenzillav2.R
import com.example.chickenzillav2.ui.screens.game.components.FallingObjectsLayer
import com.example.chickenzillav2.ui.screens.game.components.GameHUD
import com.example.chickenzillav2.ui.screens.game.components.GameOverMenuOverlay
import com.example.chickenzillav2.ui.screens.game.components.PauseMenuOverlay
import com.example.chickenzillav2.ui.screens.game.components.PlayerCharacter
import com.example.chickenzillav2.ui.screens.game.components.loadGameBitmaps
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import org.koin.compose.viewmodel.koinViewModel

// --- MAIN SCREEN ---
@Composable
fun GameScreen(
    onBackToMenu: () -> Unit,
    context: Context = LocalContext.current
) {

    val lifecycleOwner = LocalLifecycleOwner.current
    // Отримуємо ViewModel через Koin
    val viewModel: GameViewModel = koinViewModel()

    // Розмір екрану
    var screenSize by remember { mutableStateOf(IntSize.Zero) }

    BackHandler {
        if (!viewModel.isGameOver) {
            // Якщо гра активна, то глюючуємо паузу
            viewModel.isPaused = !viewModel.isPaused
        } else {
            // Якщо game over, виходимо
            viewModel.restartGame()
            onBackToMenu()
        }
    }

    if (screenSize.width == 0) {
        Box(modifier = Modifier
            .fillMaxSize()
            .onSizeChanged {
                screenSize = it
                // Ініціалізуємо гравця по центру, коли дізнались ширину
                viewModel.initPlayerPosition(it.width)
            })
        return
    }

    // Завантаження картинок
    val bitmaps = loadGameBitmaps(context, screenSize.width, screenSize.height)

    // --- LIFECYCLE OBSERVER ---
    LifecycleEventEffect(Lifecycle.Event.ON_PAUSE) {
        if (!viewModel.isGameOver) {
            viewModel.isPaused = true
        }
    }
    LaunchedEffect(screenSize) {
        if (screenSize.width > 0) {
            viewModel.startGameLoop(screenSize)
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

    ) {
        // 1. Background
        Image(
            painter = painterResource(R.drawable.back3),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )

        // 2. Falling Objects (Canvas)
        FallingObjectsLayer(
            items = viewModel.items,
            bitmaps = bitmaps,
            trigger = viewModel.gameStateTrigger
        )

        // 3. Player
        PlayerCharacter(
            x = viewModel.playerX,
            y = viewModel.getPlayerYPosition(screenSize.height),
            isFacingRight = viewModel.isFacingRight
        )

        // 4. HUD (Score, Lives)
        GameHUD(
            score = viewModel.score,
            lives = viewModel.lives,
            onPauseClick = { viewModel.isPaused = true }
        )

        // 5. Menu Overlay
        if (viewModel.isPaused) {
            PauseMenuOverlay(
                onResume = { viewModel.resumeGame() },
                onExit = {
                    viewModel.exitAndSaveGame()
                    onBackToMenu()
                },
                viewModel = viewModel
            )
        }

        // 6. Game Over Overlay
        if (viewModel.isGameOver) {
            GameOverMenuOverlay(
                score = viewModel.score,
                onRestart = { viewModel.restartGame() },
                onExit = onBackToMenu,
                viewModel = viewModel
            )
        }
    }
}

// --- SUB-COMPOSABLES ---

