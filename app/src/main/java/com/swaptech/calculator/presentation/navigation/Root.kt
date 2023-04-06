package com.swaptech.calculator.presentation.navigation

sealed class Root(
    open val route: String
) {
    object Home : Root("home") {
        fun getRouteWithArgument(historyValue: String) = "home?$HISTORY_VALUE_KEY=$historyValue"

        const val HISTORY_VALUE_KEY = "value"

        override val route: String = "home?$HISTORY_VALUE_KEY={$HISTORY_VALUE_KEY}"
    }
    object History : Root("history")
}
