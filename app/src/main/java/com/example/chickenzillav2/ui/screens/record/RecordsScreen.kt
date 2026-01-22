package com.example.chickenzillav2.ui.screens.record

import RecordItemCard
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chickenzillav2.R
import com.example.chickenzillav2.data.game.GameResult

@Composable
fun RecordsScreen(
    MainMenuClick: () -> Unit,
) {
    val context = LocalContext.current
    val viewModel = remember { RecordsViewModel(context) }

    val topResults by viewModel.topResults.collectAsState(initial = emptyList())
    val hasResults by viewModel.hasResults

    Image(
        painter = painterResource(R.drawable.back1),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize()
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        // Header з назвою та кнопкою назад
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 50.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.undo),
                contentDescription = null,
                modifier = Modifier
                    .width(80.dp)
                    .height(80.dp)
                    .clickable { MainMenuClick() }
            )

            Box(
                contentAlignment = Alignment.Center
            ) {
                Image(
                    modifier = Modifier
                        .width(220.dp)
                        .height(55.dp),
                    painter = painterResource(id = R.drawable.frame_round),
                    contentDescription = null,
                )

                Text(
                    text = "Records",
                    fontSize = 30.sp,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(35.dp)
                )
            }
        }

        // Список результатів або повідомлення
        if (!hasResults) {
            // Немає результатів - показуємо у рамці
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 100.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .width(360.dp)
                        .height(85.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.frame_score),
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier.fillMaxSize()
                    )

                    Text(
                        text = "No records yet",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
        } else {
            // Відображення топ-6 результатів
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(topResults) { result ->
                    RecordItemCard(result = result)
                }
            }
        }
    }
}
