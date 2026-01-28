package com.example.chickenzillav2.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.chickenzillav2.R

@Composable
fun PrivacyPolicy(onMainMenuClick: () -> Unit) {
    Image(
        painter = painterResource(R.drawable.back1),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize()

    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            // Color.Black з прозорістю 0.5f (50% затемнення)
            .background(Color.Black.copy(alpha = 0.7f))
    )

    Row(verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
            .padding(top = 50.dp)) {
        Image(

            painter = painterResource(id = R.drawable.undo),
            contentDescription = null,
            modifier = Modifier
                .width(80.dp)
                .height(80.dp)
                .clickable { onMainMenuClick() }
        )

        Box(
            contentAlignment = Alignment.Center
        ){
            Image(
                modifier = Modifier
                    .width(220.dp)
                    .height(55.dp),
                painter = painterResource(id = R.drawable.frame_round),
                contentDescription = null,


                )

            Text(
                text = stringResource(R.string.privacy_policy),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .padding(35.dp)


            )
        }
    }


}

