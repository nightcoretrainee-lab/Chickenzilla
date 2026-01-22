package com.example.chickenzillav2.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
// ЗВЕРНИ УВАГУ: Цей імпорт важливий для доступу до картинок
import com.example.chickenzillav2.R
import com.example.chickenzillav2.ui.theme.BalooFont

@Composable
fun ChikenzillaSplashScreen(currentProgressIndex: Int) {
    val progressImages = listOf(
        R.drawable.progres0,
        R.drawable.progres25,
        R.drawable.progres50,
        R.drawable.progres75,
        R.drawable.progres100
    )

    Box(modifier = Modifier.fillMaxSize()) {
        // ФОН
        Image(
            painter = painterResource(R.drawable.back2),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // ЛОГО І КУРКА
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.chick_right),
                contentDescription = null,
                modifier = Modifier.size(170.dp).offset(y = 100.dp)
            )
            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = null,
                modifier = Modifier.size(580.dp).offset(y = -200.dp)
            )
        }

        // ПРОГРЕС БАР
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 50.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            val imageRes = if (currentProgressIndex in progressImages.indices) {
                progressImages[currentProgressIndex]
            } else {
                progressImages.last()
            }

            Image(
                painter = painterResource(imageRes),
                contentDescription = null,
                modifier = Modifier.width(200.dp).height(150.dp)
            )

            Box(contentAlignment = Alignment.BottomCenter) {
                Text(text = "Loading...", fontFamily = BalooFont, fontSize = 40.sp, color = Color.White)
            }
        }
    }
}