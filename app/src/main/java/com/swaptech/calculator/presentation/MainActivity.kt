package com.swaptech.calculator.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.swaptech.calculator.di.presentation.view_model.ViewModelFactory
import com.swaptech.calculator.presentation.screens.root.RootScreen
import com.swaptech.calculator.presentation.theme.CalculatorTheme
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as CalculatorApplication).androidInjector().inject(this)
        setContent {
            CalculatorTheme {
                RootScreen(
                    viewModelFactory = viewModelFactory
                )
            }
        }
    }
}
