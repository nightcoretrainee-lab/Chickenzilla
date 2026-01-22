package com.example.chickenzillav2.navigation

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.runtime.*
import kotlinx.coroutines.delay
// Перевірте, чи всі імпорти правильні
import com.example.chickenzillav2.ui.screens.ChikenzillaSplashScreen
import com.example.chickenzillav2.ui.screens.game.GameScreen
import com.example.chickenzillav2.ui.screens.MainMenuScreen
import com.example.chickenzillav2.ui.screens.PrivacyPolicy
import com.example.chickenzillav2.ui.screens.record.RecordsScreen


enum class Screen {
    Splash,
    Menu,
    PrivacyPolicy,
    GameScreen,
    Records
}

@Composable
fun AppNavigation() {
    var currentScreen by remember { mutableStateOf(Screen.Splash) }
    var progressIndex by remember { mutableIntStateOf(0) }

    // Логіка сплеш-скріну
    LaunchedEffect(Unit) {
        val stepDelay = 600L
        for (i in 0..4) {
            progressIndex = i
            delay(stepDelay)
        }
        currentScreen = Screen.Menu
    }

    Crossfade(
        targetState = currentScreen,
        animationSpec = tween(durationMillis = 1000),
        label = "ScreenTransition"
    ) { screen ->

        when (screen) {
            Screen.Splash -> {
                ChikenzillaSplashScreen(progressIndex)
            }
            Screen.Menu -> {
                MainMenuScreen(
                    PrivacyClick = { currentScreen = Screen.PrivacyPolicy },
                    StartClick = { currentScreen = Screen.GameScreen },
                    RecordsClick = { currentScreen = Screen.Records }
                )
            }
            Screen.PrivacyPolicy -> {
                PrivacyPolicy(
                    MainMenuClick = { currentScreen = Screen.Menu },
                    StartClick = { currentScreen = Screen.GameScreen },
                    RecordsClick = { currentScreen = Screen.Records }
                )
            }
            Screen.GameScreen -> {
                // Викликаємо гру, передаємо функцію виходу в меню
                GameScreen(
                    onBackToMenu = { currentScreen = Screen.Menu }
                )
            }
            // 👇 ОСЬ ЦЮ ЧАСТИНУ ВИ ЗАБУЛИ (АБО ВОНА БУЛА В else) 👇
            Screen.Records -> {
                RecordsScreen(
                    MainMenuClick = { currentScreen = Screen.Menu },

                )
            }
        }
    }
}