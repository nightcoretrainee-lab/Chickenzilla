package com.example.chickenzillav2.ui.screens.game.components

import android.content.Context
import android.graphics.BitmapFactory
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.chickenzillav2.R
import androidx.core.graphics.scale
import com.example.chickenzillav2.ui.screens.game.EntityType
import com.example.chickenzillav2.ui.screens.game.FallingItem
import kotlin.collections.forEach
import kotlin.math.roundToInt

@Composable
fun loadGameBitmaps(context: Context, width: Int, height: Int): Map<String, ImageBitmap> {
    fun load(id: Int, w: Int, h: Int): ImageBitmap {
        val original = BitmapFactory.decodeResource(context.resources, id)
        val scaled = original.scale(w, h)
        return scaled.asImageBitmap()
    }

    // Завантажуємо картинки один раз
    return remember {
        mapOf(
            // Хороші предмети
            "good_0" to load(R.drawable.gem, 120, 120),
            "good_1" to load(R.drawable.diam, 120, 120),
            "good_2" to load(R.drawable.star, 120, 120),
            "good_3" to load(R.drawable.coin, 120, 120),
            "good_4" to load(R.drawable.spped, 120, 120),
            // Погані предмети
            "bad_0" to load(R.drawable.stone1, 130, 130),
            "bad_1" to load(R.drawable.stone2, 130, 130),
        )
    }
}

@Composable
fun PlayerCharacter(
    x: Float,
    y: Float,
    isFacingRight: Boolean
) {
        Image(
            painter = painterResource(R.drawable.chick_right),
            contentDescription = null,
            modifier = Modifier
                .size(120.dp)
                .offset { IntOffset(x.roundToInt(), y.roundToInt()) }
                .graphicsLayer {
                    scaleX = if (isFacingRight) 1f else -1f
                    transformOrigin = androidx.compose.ui.graphics.TransformOrigin.Center
                }
)

}

@Composable
fun FallingObjectsLayer(
    items: List<FallingItem>,
    bitmaps: Map<String, ImageBitmap>,
    trigger: Long
) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        trigger // Читаємо тригер для перемальовування

        items.forEach { item ->
            val key = if (item.type == EntityType.BAD) "bad_${item.bitmapIndex}" else "good_${item.bitmapIndex}"
            bitmaps[key]?.let {
                drawImage(
                    it,
                    topLeft = Offset(item.x, item.y),
                    alpha = 1f
                )
            }
        }
    }
}



