package com.swaptech.calculator.di.data.db

import com.swaptech.calculator.data.Database
import com.swaptech.calculator.data.calculation.CalculationDao
import dagger.Module
import dagger.Provides

@Module
class DaoModule {

    @Provides
    fun provideCalculationDao(database: Database): CalculationDao = database.getCalculationDao()
}
