package com.example.chickenzillav2.data.game

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Entity(tableName = "game_results")
data class GameResult(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val score: Int,
    val lives: Int,
    val timestamp: Long = System.currentTimeMillis()
) {
    fun getFormattedDate(): String {
        val date = Date(timestamp)
        val format = SimpleDateFormat("dd.MM", Locale.getDefault())
        return format.format(date)
    }
}