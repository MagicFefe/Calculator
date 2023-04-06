package com.swaptech.calculator.data

import androidx.room.RoomDatabase
import com.swaptech.calculator.data.calculation.CalculationDB
import com.swaptech.calculator.data.calculation.CalculationDao

@androidx.room.Database(
    entities = [CalculationDB::class],
    version = 1,
    exportSchema = false
)
abstract class Database: RoomDatabase() {

    abstract fun getCalculationDao(): CalculationDao
}