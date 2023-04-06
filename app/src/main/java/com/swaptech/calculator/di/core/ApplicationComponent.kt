package com.swaptech.calculator.di.core

import com.swaptech.calculator.presentation.CalculatorApplication
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Component(
    modules = [
        AndroidInjectionModule::class,
        ApplicationModule::class
    ]
)
@Singleton
interface ApplicationComponent: AndroidInjector<CalculatorApplication> {

    @Component.Factory
    interface Factory: AndroidInjector.Factory<CalculatorApplication>
}