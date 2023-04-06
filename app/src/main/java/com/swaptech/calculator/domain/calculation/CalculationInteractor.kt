package com.swaptech.calculator.domain.calculation

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CalculationInteractor @Inject constructor(
    private val calculationRepository: CalculationRepository
) {
    suspend fun getCalculations(): Flow<List<Calculation>> = calculationRepository.getCalculations()

    suspend fun saveCalculation(calculation: Calculation) =
        calculationRepository.saveCalculation(calculation)

    suspend fun deleteAll() = calculationRepository.deleteAll()
}
