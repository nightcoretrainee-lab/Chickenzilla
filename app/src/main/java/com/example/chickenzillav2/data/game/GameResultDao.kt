package com.example.chickenzillav2.data.game

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface GameResultDao {

    @Insert
    suspend fun insertResult(gameResult: GameResult)

    @Query("SELECT * FROM game_results ORDER BY timestamp DESC")
    fun getAllResults(): Flow<List<GameResult>>

}