package com.example.chickenzillav2.ui.screens.game.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.core.content.res.ResourcesCompat
import com.example.chickenzillav2.R
import com.example.chickenzillav2.ui.screens.game.GameViewModel

@Composable
fun PauseMenuOverlay(
    onResume: () -> Unit,
    onExit: () -> Unit,
    viewModel: GameViewModel? = null
) {


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.6f))
            .clickable(enabled = false) { }
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .wrapContentSize(Alignment.Center)
                .align(Alignment.Center)
        ) {

                Image(
                    painter = painterResource(R.drawable.results),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .fillMaxWidth(360f/412f)
                )



            Column(

                modifier = Modifier
                    .fillMaxHeight(0.28f),
//                    .wrapContentSize(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Title
                Text(
                    text = stringResource(R.string.pause),
                    style = MaterialTheme.typography.titleLarge,
                    color = Color(0xFF2B2B2B),
                    modifier = Modifier.wrapContentSize(Alignment.Center)
                )

                // Resume Button
                PauseMenuButton(
                    text = stringResource(R.string.resume),

                    onClick = onResume,
                )
                Spacer(Modifier.size(10.dp))
                // Exit Button
                PauseMenuButton(
                    text = stringResource(R.string.exit),

                    onClick = {
                        viewModel?.exitAndSaveGame()
                        onExit()
                    },
                )
            }
        }
    }
}

@Composable
fun PauseMenuButton(
    text: String,
    onClick: () -> Unit,
) {

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .clickable(onClick = onClick)
            ) {
            Image(
                painter = painterResource(R.drawable.frame),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth(180f / 360f)
                    .align(Alignment.Center)

            )

        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium,
            color = Color(0xFF2B2B2B),
            modifier = Modifier
//                .align(Alignment.Center)
        )
        }

}
