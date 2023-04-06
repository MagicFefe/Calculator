package com.swaptech.calculator.di.presentation.view_model

import androidx.lifecycle.ViewModel
import dagger.MapKey
import kotlin.reflect.KClass

@MapKey
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class CreateWithViewModelFactory(val modelClass: KClass<out ViewModel>)
