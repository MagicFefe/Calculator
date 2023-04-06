package com.swaptech.calculator.presentation.screens.history

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swaptech.calculator.domain.calculation.CalculationInteractor
import kotlinx.coroutines.launch
import javax.inject.Inject

class HistoryScreenViewModel @Inject constructor(
    private val calculationInteractor: CalculationInteractor,
) : ViewModel() {

    var uiState by mutableStateOf(HistoryScreenUiState())
        private set

    fun getCalculations() {
        viewModelScope.launch {
            calculationInteractor.getCalculations().collect { calculations ->
                uiState = uiState.copy(
                    calculations = calculations
                )
            }
        }
    }

    fun deleteAll() {
        viewModelScope.launch {
            calculationInteractor.deleteAll()
            getCalculations()
        }
    }
}
