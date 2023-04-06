package com.swaptech.calculator.di.data.repository

import com.swaptech.calculator.data.calculation.CalculationRepositoryImpl
import com.swaptech.calculator.domain.calculation.CalculationRepository
import dagger.Binds
import dagger.Module

@Module
interface RepositoryModule {

    @Binds
    fun bindCalculationRepository(calculationRepositoryImpl: CalculationRepositoryImpl): CalculationRepository
}