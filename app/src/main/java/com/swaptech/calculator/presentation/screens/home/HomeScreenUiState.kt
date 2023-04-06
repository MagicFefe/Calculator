package com.swaptech.calculator.presentation.screens.home

data class HomeScreenUiState(
    val expression: String = "",
    val result: String = "",
    val parenthesesOpen: Boolean = true,
    val error: Boolean = false
)
