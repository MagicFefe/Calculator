package com.swaptech.calculator.data.calculation

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "calculations"
)
data class CalculationDB(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val expression: String,
    val result: String
)
