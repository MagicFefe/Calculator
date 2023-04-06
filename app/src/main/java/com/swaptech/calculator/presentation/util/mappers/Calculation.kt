package com.swaptech.calculator.presentation.util.mappers

import com.swaptech.calculator.data.calculation.CalculationDB
import com.swaptech.calculator.domain.calculation.Calculation

fun CalculationDB.toCalculation() =
    Calculation(
        expression = expression,
        result = result
    )

fun Calculation.toCalculationDB() =
    CalculationDB(
        expression = expression,
        result = result
    )
