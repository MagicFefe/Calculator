package com.swaptech.calculator.domain.calculation

import kotlinx.coroutines.flow.Flow

interface CalculationRepository {
    suspend fun getCalculations(): Flow<List<Calculation>>
    suspend fun saveCalculation(calculation: Calculation)
    suspend fun deleteAll()
}