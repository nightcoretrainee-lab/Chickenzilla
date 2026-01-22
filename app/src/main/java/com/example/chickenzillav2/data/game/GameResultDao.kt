package com.example.chickenzillav2.data.game

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface GameResultDao {

    @Insert
    suspend fun insertResult(gameResult: GameResult)

    @Query("SELECT * FROM game_results ORDER BY timestamp DESC")
    fun getAllResults(): Flow<List<GameResult>>

    @Query("SELECT * FROM game_results ORDER BY score DESC LIMIT 1")
    suspend fun getHighestScore(): GameResult?

    @Query("SELECT * FROM game_results ORDER BY timestamp DESC LIMIT :limit")
    suspend fun getRecentResults(limit: Int = 10): List<GameResult>

    @Query("DELETE FROM game_results")
    suspend fun deleteAllResults()

    @Delete
    suspend fun deleteResult(gameResult: GameResult)

    @Query("SELECT COUNT(*) FROM game_results")
    suspend fun getResultsCount(): Int
}