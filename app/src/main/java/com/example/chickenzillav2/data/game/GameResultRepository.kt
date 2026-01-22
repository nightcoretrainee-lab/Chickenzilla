package com.example.chickenzillav2.data.game

import kotlinx.coroutines.flow.Flow

class GameResultRepository(private val gameResultDao: GameResultDao) {

    val allResults: Flow<List<GameResult>> = gameResultDao.getAllResults()

    suspend fun insertResult(gameResult: GameResult) {
        gameResultDao.insertResult(gameResult)
    }

    suspend fun getHighestScore(): GameResult? {
        return gameResultDao.getHighestScore()
    }

    suspend fun getRecentResults(limit: Int = 10): List<GameResult> {
        return gameResultDao.getRecentResults(limit)
    }

    suspend fun deleteAllResults() {
        gameResultDao.deleteAllResults()
    }

    suspend fun deleteResult(gameResult: GameResult) {
        gameResultDao.deleteResult(gameResult)
    }

    suspend fun getResultsCount(): Int {
        return gameResultDao.getResultsCount()
    }
}