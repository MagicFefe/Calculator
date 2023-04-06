package com.swaptech.calculator.di.presentation

import com.swaptech.calculator.di.presentation.activity.ActivityModule
import com.swaptech.calculator.di.presentation.view_model.ViewModelModule
import dagger.Module

@Module(
    includes = [
        ActivityModule::class, ViewModelModule::class
    ]
)
class PresentationModule