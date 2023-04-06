package com.swaptech.calculator.presentation.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swaptech.calculator.domain.calculation.Calculation
import com.swaptech.calculator.domain.calculation.CalculationInteractor
import com.swaptech.calculator.presentation.util.calculate
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeScreenViewModel @Inject constructor(
    private val calculationInteractor: CalculationInteractor
): ViewModel() {

    var uiState by mutableStateOf(HomeScreenUiState())
        private set

    fun onExpressionChange(input: String) {
        uiState = uiState.copy(
            result = "",
            error = false
        )
        uiState = uiState.copy(
            expression = input
        )
        onResultChange()
    }

    fun onWriteExpression(input: String) {
        val expression = StringBuilder(uiState.expression)
        expression.append(input)
        onExpressionChange(expression.toString())
    }

    fun onDeleteFromExpression() {
        if(uiState.expression.isNotEmpty()) {
            onExpressionChange(uiState.expression.dropLast(1))
        }
    }

    fun clearExpression() {
        onExpressionChange("")
    }

    fun onResultChange() {
        uiState.expression.calculate()
            .onSuccess { result ->
                uiState = uiState.copy(
                    result = result.toString()
                )
            }
            .onFailure {
                uiState = uiState.copy(
                    error = true
                )
            }
    }

    fun onGetResult() {
        viewModelScope.launch {
            onResultChange()
            if (!uiState.error) {
                calculationInteractor.saveCalculation(
                    Calculation(
                        expression = uiState.expression,
                        result = uiState.result
                    )
                )
            }
            uiState = uiState.copy(
                expression = uiState.result,
                result = ""
            )
        }
    }

    fun reverseParentheses() {
        uiState = uiState.copy(
            parenthesesOpen = !uiState.parenthesesOpen
        )
    }
}