package com.example.chickenzillav2.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource // Додано
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember // Додано
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.chickenzillav2.R

@Composable
fun MainMenuScreen(onPrivacyClick: () -> Unit, onStartClick: () -> Unit, onRecordsClick: () -> Unit) {

    Image(
        painter = painterResource(R.drawable.back2),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize()
    )
    Box(modifier = Modifier.fillMaxHeight()){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.75f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            MainButton(text = stringResource(R.string.start), onClick = onStartClick)
            MainButton(text = stringResource(R.string.records), onClick = onRecordsClick)
            MainButton(text = stringResource(R.string.privacy_policy), onClick = onPrivacyClick)

        }
        Image(
            painter = painterResource(R.drawable.chicken_main),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(256f / 412f)
        )
    }


}

@Composable
fun MainButton(
    text: String,
    onClick: () -> Unit,
){
    Box(
        modifier = Modifier
            .fillMaxWidth(360f / 412f)
            .clickable(indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.frame),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = text,
            style = MaterialTheme.typography.titleLarge,
        )
        Spacer(Modifier.size(150.dp))
    }
}