package com.example.chickenzillav2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.chickenzillav2.navigation.AppNavigation
import com.example.chickenzillav2.ui.theme.ChickenzillaV2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChickenzillaV2Theme {
                // Все, що тут залишилось — це виклик навігації
                AppNavigation()
            }
        }
    }
}