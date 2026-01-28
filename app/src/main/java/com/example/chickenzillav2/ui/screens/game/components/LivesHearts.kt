package com.example.chickenzillav2.ui.screens.game.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PageSize
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.chickenzillav2.R

@Composable
fun LivesHearts(
    lives: Int,
    modifier: Modifier = Modifier
) {
    val totalLives = 3
    val heartSize = 40.dp
    val heartSpacing = 12.dp

    Box(
        modifier = modifier.layout { measurable, constraints ->
            val placeable = measurable.measure(constraints)

            // Адаптивні координати
            val offsetX = (constraints.maxWidth * 0.55f).toInt()  // 214/412 ≈ 51.9%
            val offsetY = (constraints.maxHeight * 0.159f).toInt() // 146/917 ≈ 15.9%

            layout(placeable.width, placeable.height) {
                placeable.place(offsetX, offsetY)
            }
        }
//            .background(color = Color.Red)
    ) {
        Row {
            repeat(totalLives) { i ->
                val isAlive = i < lives
                Image(
                    painter = painterResource(
                        id = if (isAlive) R.drawable.heart_live else R.drawable.heart_black
                    ),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .size(heartSize)
                )
                Spacer(modifier = Modifier.size(10.dp))
            }
        }
    }
}