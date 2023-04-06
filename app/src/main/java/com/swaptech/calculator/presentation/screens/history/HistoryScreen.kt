package com.swaptech.calculator.presentation.screens.history

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.swaptech.calculator.R
import com.swaptech.calculator.presentation.ui.Calculation

@Composable
fun HistoryScreen(
    onExitButtonClick: () -> Unit,
    onHistoryValueClick: (String) -> Unit,
    viewModel: HistoryScreenViewModel
) {
    val uiState = viewModel.uiState
    val configuration = LocalConfiguration.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.history)
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onExitButtonClick
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_arrow_back_24),
                            contentDescription = null
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            viewModel.deleteAll()
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_delete_24),
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        if(uiState.calculations.isEmpty()) {
            NoHistoryPlaceholder()
        } else {
            LazyColumn(
                modifier = Modifier.padding(innerPadding)
            ) {
                items(items = uiState.calculations) {calculation ->
                    Calculation(
                        expression = calculation.expression,
                        verticalOrientation = configuration.orientation == Configuration.ORIENTATION_PORTRAIT,
                        result = calculation.result,
                        onHistoryValueClick = onHistoryValueClick
                    )
                }
            }
        }
    }
    LaunchedEffect(Unit) {
        viewModel.getCalculations()
    }
}

@Composable
fun NoHistoryPlaceholder() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = stringResource(R.string.no_history)
        )
    }
}
