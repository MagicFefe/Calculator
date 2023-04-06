package com.swaptech.calculator.presentation.screens.root

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.swaptech.calculator.di.presentation.view_model.ViewModelFactory
import com.swaptech.calculator.presentation.navigation.Root
import com.swaptech.calculator.presentation.screens.history.HistoryScreen
import com.swaptech.calculator.presentation.screens.history.HistoryScreenViewModel
import com.swaptech.calculator.presentation.screens.home.HomeScreen
import com.swaptech.calculator.presentation.screens.home.HomeScreenViewModel
import com.swaptech.calculator.presentation.util.ext.replaceTo

@Composable
fun RootScreen(
    viewModelFactory: ViewModelFactory
) {
    val navController = rememberNavController()
    val systemUiController = rememberSystemUiController()
    val surfaceColor = MaterialTheme.colors.surface
    Scaffold { contentPadding ->
        Column(
            modifier = Modifier.padding(contentPadding)
        ) {
            NavHost(
                navController = navController,
                startDestination = Root.Home.route
            ) {

                composable(
                    route = Root.Home.route,
                    arguments = listOf(
                        navArgument(Root.Home.HISTORY_VALUE_KEY) {
                            type = NavType.StringType
                            defaultValue = ""
                        }
                    )
                ) { navBackStackEntry ->
                    val historyValue = navBackStackEntry.arguments
                        ?.getString(Root.Home.HISTORY_VALUE_KEY) ?: ""
                    HomeScreen(
                        historyValue = historyValue,
                        viewModel = viewModel(
                            modelClass = HomeScreenViewModel::class.java,
                            factory = viewModelFactory
                        ),
                        onShowHistoryButtonClick = {
                            navController.replaceTo(Root.History.route)
                        }
                    )
                }

                composable(
                    route = Root.History.route
                ) {
                    HistoryScreen(
                        onExitButtonClick = {
                            navController.replaceTo(Root.Home.route)
                        },
                        onHistoryValueClick = { historyValue ->
                            navController.replaceTo(
                                Root.Home.getRouteWithArgument(historyValue)
                            )
                        },
                        viewModel = viewModel(
                            modelClass = HistoryScreenViewModel::class.java,
                            factory = viewModelFactory
                        )
                    )
                }
            }
        }
    }
    SideEffect {
        systemUiController.setStatusBarColor(surfaceColor)
    }
}
