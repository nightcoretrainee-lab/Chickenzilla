package com.example.chickenzillav2.ui.screens.game.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
//import com.example.chickenzillav2.ui.screens.game.components.ScoreBoard
import kotlin.math.roundToInt
import com.example.chickenzillav2.R
@Composable
fun GameHUD(
    score: Int,
    lives: Int,
    onPauseClick: () -> Unit = {}
) {
    Row(horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 30.dp, vertical = 50.dp)
        ) {


        Image(
            painter = painterResource(R.drawable.pause),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .weight(80f/352f)
                .clickable { onPauseClick() }

        )
        Spacer(modifier = Modifier.weight(127f/352f))
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.weight(145f/352f)
        ) {
            Image(
                painter = painterResource(id = R.drawable.score),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.FillWidth,
            )

            Text(
                text = score.toString(),
                style = MaterialTheme.typography.titleLarge,
                color = Color.Black,

                )

        }
    }



    // Lives
    LivesHearts(
        lives = lives,
        modifier = Modifier.fillMaxSize()
    )
}

