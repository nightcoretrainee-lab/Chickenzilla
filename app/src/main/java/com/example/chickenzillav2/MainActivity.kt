package com.example.chickenzillav2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.chickenzillav2.navigation.AppNavigation
import com.example.chickenzillav2.ui.theme.ChickenzillaV2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // --- ПОЧАТОК ЗМІН ---
        // Отримуємо контролер вікна
        val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)

        // Налаштовуємо поведінку: панель з'явиться ненадовго, якщо зробити свайп від краю екрану
        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

        // Приховуємо саме навігаційну панель (можна також приховати statusBars, якщо потрібно)
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
        // --- КІНЕЦЬ ЗМІН ---


        setContent {
            ChickenzillaV2Theme {
                AppNavigation()
            }
        }
    }
}