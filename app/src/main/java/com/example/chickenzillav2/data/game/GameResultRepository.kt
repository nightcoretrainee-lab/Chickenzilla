package com.example.chickenzillav2.data.game

import kotlinx.coroutines.flow.Flow

class GameResultRepository(private val gameResultDao: GameResultDao) {

    val allResults: Flow<List<GameResult>> = gameResultDao.getAllResults()

    suspend fun insertResult(gameResult: GameResult) {
        gameResultDao.insertResult(gameResult)
    }

}