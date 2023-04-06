package com.swaptech.calculator.data.calculation

import com.swaptech.calculator.domain.calculation.Calculation
import com.swaptech.calculator.domain.calculation.CalculationRepository
import com.swaptech.calculator.presentation.util.mappers.toCalculation
import com.swaptech.calculator.presentation.util.mappers.toCalculationDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CalculationRepositoryImpl @Inject constructor(
    private val calculationDao: CalculationDao
): CalculationRepository {

    override suspend fun getCalculations(): Flow<List<Calculation>> =
        calculationDao.getCalculations().map { calculationsDB ->
            calculationsDB.map { it.toCalculation() }
        }

    override suspend fun saveCalculation(calculation: Calculation) = withContext(Dispatchers.IO) {
        calculationDao.saveCalculation(calculation.toCalculationDB())
    }

    override suspend fun deleteAll() = withContext(Dispatchers.IO) {
        calculationDao.deleteAll()
    }
}