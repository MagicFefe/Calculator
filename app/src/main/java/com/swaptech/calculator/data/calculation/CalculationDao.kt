package com.swaptech.calculator.data.calculation

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CalculationDao {

    @Query("SELECT * FROM calculations")
    fun getCalculations(): Flow<List<CalculationDB>>

    @Insert
    fun saveCalculation(calculationDB: CalculationDB)

    @Query("DELETE FROM calculations")
    fun deleteAll()
}
