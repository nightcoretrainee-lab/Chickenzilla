package com.example.chickenzillav2.ui.screens.game

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.IntSize
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chickenzillav2.data.game.GameDatabase
import com.example.chickenzillav2.data.game.GameResult
import com.example.chickenzillav2.data.game.GameResultRepository
import kotlinx.coroutines.launch
import kotlin.random.Random

// --- DATA CLASSES (Винесено сюди) ---
enum class EntityType { GOOD, BAD }

class FallingItem(
    var x: Float = 0f,
    var y: Float = 0f,
    var type: EntityType = EntityType.GOOD,
    var bitmapIndex: Int = 0,
    var speed: Float = 0f,
    var isActive: Boolean = false
)

class GameViewModel(context: Context? = null) : ViewModel() {

    private val repository: GameResultRepository? = if (context != null) {
        val database = GameDatabase.getDatabase(context)
        GameResultRepository(database.gameResultDao())
    } else {
        null
    }

    // --- STATE (Стан гри) ---
    var score by mutableIntStateOf(0)
        private set
    var lives by mutableIntStateOf(3)
        private set
    var isPaused by mutableStateOf(false)
    var isGameOver by mutableStateOf(false)
        private set

    // Гравець
    var playerX by mutableFloatStateOf(0f)
        private set
    var isFacingRight by mutableStateOf(true)
        private set

    // Список об'єктів
    val items = mutableStateListOf<FallingItem>()

    // Тригер для примусового перемальовування Canvas (оптимізація)
    var gameStateTrigger by mutableLongStateOf(0L)
        private set

    // Константи гри
    private val playerWidth = 200f
    private val playerBottomMargin = 420f

    // --- LOGIC (Логіка) ---

    fun initPlayerPosition(screenWidth: Int) {
        if (playerX == 0f) {
            playerX = screenWidth / 2f - 100f
        }
    }

    fun updateGameLogic(screenSize: IntSize) {
        if (isPaused || isGameOver || screenSize == IntSize.Zero) return

        val width = screenSize.width.toFloat()
        val height = screenSize.height.toFloat()

        if (Random.nextInt(100) < 3) {
            val isBad = Random.nextBoolean()
            val keyIndex = if (isBad) Random.nextInt(2) else Random.nextInt(5)

            items.add(FallingItem(
                x = Random.nextFloat() * (width - 150),
                y = -150f,
                type = if (isBad) EntityType.BAD else EntityType.GOOD,
                bitmapIndex = keyIndex,
                speed = if (isBad) 20f else 12f,
                isActive = true
            ))
        }

        val playerY = height - playerBottomMargin

        val iterator = items.iterator()
        while (iterator.hasNext()) {
            val item = iterator.next()
            item.y += item.speed

            val hitX = item.x + 50 > playerX && item.x < playerX + playerWidth
            val hitY = item.y + 50 > playerY && item.y < height

            if (hitX && hitY) {
                if (item.type == EntityType.GOOD) {
                    score += 10
                } else {
                    lives--
                    if (lives <= 0) {
                        isGameOver = true
                        saveGameResult()
                    }
                }
                iterator.remove()
            } else if (item.y > height) {
                iterator.remove()
            }
        }

        gameStateTrigger = System.nanoTime()
    }

    fun onDrag(dragAmountX: Float, screenWidth: Int) {
        if (!isPaused && !isGameOver) {
            playerX = (playerX + dragAmountX).coerceIn(0f, screenWidth.toFloat() - 250f)

            if (dragAmountX < 0) isFacingRight = false
            else if (dragAmountX > 0) isFacingRight = true
        }
    }

    fun onTap(offsetApi: androidx.compose.ui.geometry.Offset) {
        if (offsetApi.x < 200 && offsetApi.y < 200 && !isGameOver) {
            isPaused = true
        }
    }

    // Збереження результату в БД (тільки якщо score > 0)
    private fun saveGameResult() {
        if (score > 0) {
            viewModelScope.launch {
                repository?.insertResult(
                    GameResult(
                        score = score,
                        lives = lives,
                        timestamp = System.currentTimeMillis()
                    )
                )
            }
        }
    }

    fun restartGame() {
        lives = 3
        score = 0
        items.clear()
        isGameOver = false
        isPaused = false
    }

    fun resumeGame() {
        isPaused = false
    }

    // Збереження результату та вихід
    fun exitAndSaveGame() {
        if (score > 0) {
            viewModelScope.launch {
                repository?.insertResult(
                    GameResult(
                        score = score,
                        lives = lives,
                        timestamp = System.currentTimeMillis()
                    )
                )
            }
        }
        // Скидаємо стан
        restartGame()
    }

    fun getPlayerYPosition(screenHeight: Int): Float {
        return screenHeight - playerBottomMargin
    }
}