package com.example.chickenzillav2.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chickenzillav2.R

@Composable
fun MainMenuScreen(PrivacyClick: () -> Unit, StartClick: () -> Unit, RecordsClick: () -> Unit) {
    Image(
        painter = painterResource(R.drawable.back2),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize()
    )
Column(

    modifier = Modifier.fillMaxSize()
        .offset(y=50.dp),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally

) {

    Box(

        modifier = Modifier
            .width(380.dp)
            .height(155.dp)
            .clickable { StartClick() }
            .padding(15.dp),
        contentAlignment = Alignment.Center
    ) {

        Image(

            painter = painterResource(id = R.drawable.frame),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Text(
            text = "Start!",
            fontSize = 40.sp,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold

        )

    }

    Box(

        modifier = Modifier
            .width(380.dp)
            .height(155.dp)
            .clickable { RecordsClick() }
            .padding(15.dp),
        contentAlignment = Alignment.Center
    ) {

        Image(

            painter = painterResource(id = R.drawable.frame),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Text(
            text = "Records",
            fontSize = 40.sp,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold

        )

    }

    Box(

        modifier = Modifier
            .width(380.dp)
            .height(155.dp)
            .clickable { PrivacyClick() }
            .padding(15.dp),
        contentAlignment = Alignment.Center
    ) {

        Image(

            painter = painterResource(id = R.drawable.frame),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()

        )

        Text(
            text = "Privacy Policy",
            fontSize = 40.sp,
            style = MaterialTheme.typography.titleLarge,


        )

    }
    Box(
        contentAlignment = Alignment.BottomCenter
    ) {
        Image(

            painter = painterResource(id = R.drawable.chicken_main),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(246.dp)
                .height(272.dp)
                .offset(y = 40.dp),
        )
    }
}


}