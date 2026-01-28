package com.example.chickenzillav2.ui.screens.record

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chickenzillav2.data.game.GameResult
import com.example.chickenzillav2.data.game.GameResultRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class RecordsViewModel(private val repository: GameResultRepository) : ViewModel() {

    // Топ-6 результатів (Flow для реактивного оновлення)
    val topResults: Flow<List<GameResult>> = repository.allResults.map { results ->
        results.sortedByDescending { it.score }.take(6)
    }

    // Стан: чи є результати
    private val _hasResults = mutableStateOf(false)
    val hasResults: State<Boolean> = _hasResults

    init {
        // Слухаємо зміни результатів
        viewModelScope.launch {
            topResults.collect { results ->
                _hasResults.value = results.isNotEmpty()
            }
        }
    }
}