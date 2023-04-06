package com.swaptech.calculator.presentation.screens.history

import com.swaptech.calculator.domain.calculation.Calculation

data class HistoryScreenUiState(
    val calculations: List<Calculation> = listOf()
)
