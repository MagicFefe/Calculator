package com.swaptech.calculator.presentation.util.ext

import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder

fun NavController.replaceTo(
    toRoute: String,
    inclusive: Boolean = false,
    builder: NavOptionsBuilder.() -> Unit = {}
) {
    currentBackStackEntry?.destination?.route?.let { host ->
        navigate(toRoute) {
            launchSingleTop = true
            popUpTo(host) {
                this.inclusive = inclusive
            }
            builder()
        }
    }
}
