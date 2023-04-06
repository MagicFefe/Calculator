package com.swaptech.calculator.di.core

import android.content.Context
import com.swaptech.calculator.di.data.DataModule
import com.swaptech.calculator.di.presentation.PresentationModule
import com.swaptech.calculator.presentation.CalculatorApplication
import dagger.Module
import dagger.Provides

@Module(
    includes = [
        DataModule::class, PresentationModule::class
    ]
)
class ApplicationModule {

    @Provides
    fun provideContext(application: CalculatorApplication): Context = application.applicationContext
}
