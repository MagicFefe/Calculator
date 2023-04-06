package com.swaptech.calculator.presentation.screens.home

import android.content.res.Configuration
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.swaptech.calculator.R
import com.swaptech.calculator.presentation.ui.AdaptiveGrid
import com.swaptech.calculator.presentation.ui.CalculatorButton

@Composable
fun HomeScreen(
    historyValue: String,
    viewModel: HomeScreenViewModel,
    onShowHistoryButtonClick: () -> Unit
) {
    val resultScrollState = rememberScrollState(0)
    val expressionScrollState = rememberScrollState(0)
    val configuration = LocalConfiguration.current
    val vertical = configuration.orientation == Configuration.ORIENTATION_PORTRAIT
    val uiState = viewModel.uiState
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                },
                backgroundColor = MaterialTheme.colors.surface,
                actions = {
                    IconButton(
                        onClick = onShowHistoryButtonClick
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_history_24),
                            contentDescription = null
                        )
                    }
                },
                elevation = 0.dp
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.padding(if (vertical) 16.dp else 8.dp)
            ) {
                ExpressionEditor(
                    vertical = vertical,
                    expression = uiState.expression,
                    result = uiState.result,
                    resultScrollState = resultScrollState,
                    expressionScrollState = expressionScrollState
                )
                Spacer(
                    modifier = Modifier
                        .weight(1f)
                        .padding(2.dp)
                )
                ButtonsGrid(
                    onButtonClick = { operation ->
                        when (operation) {
                            ButtonValue.DELETE.value -> {
                                viewModel.onDeleteFromExpression()
                            }
                            ButtonValue.CLEAR.value -> {
                                viewModel.clearExpression()
                            }
                            ButtonValue.GET_RESULT.value -> {
                                viewModel.onGetResult()
                            }
                            else -> {
                                if (operation == ButtonValue.PARENTHESIS.value) {
                                    viewModel.onWriteExpression(
                                        if(uiState.parenthesesOpen) "(" else ")"
                                    )
                                    viewModel.reverseParentheses()
                                } else {
                                    viewModel.onWriteExpression(operation)
                                }
                            }
                        }
                    }
                )
            }
        }
    }
    LaunchedEffect(Unit) {
        if (historyValue.isNotEmpty()) {
            viewModel.onExpressionChange(historyValue)
        }
    }
}

@Composable
fun ExpressionEditor(
    vertical: Boolean,
    expression: String,
    result: String,
    expressionScrollState: ScrollState,
    resultScrollState: ScrollState,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Text(
            modifier = Modifier
                .horizontalScroll(expressionScrollState)
                .padding(
                    horizontal = 16.dp,
                    vertical = if (vertical) 8.dp else 2.dp
                )
                .fillMaxWidth(),
            text = expression,
            fontSize = if (vertical) 40.sp else 16.sp,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            modifier = Modifier
                .horizontalScroll(resultScrollState)
                .padding(
                    horizontal = 16.dp,
                    vertical = if (vertical) 8.dp else 2.dp
                )
                .fillMaxWidth(),
            text = result,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f),
            fontWeight = FontWeight.SemiBold,
            fontSize = if (vertical) 40.sp else 16.sp
        )
    }
    LaunchedEffect(expression) {
        expressionScrollState.animateScrollTo(
            value = expressionScrollState.maxValue,
            animationSpec = tween(
                durationMillis = 100
            )
        )
    }
}

@Composable
fun ButtonsGrid(
    modifier: Modifier = Modifier,
    onButtonClick: (String) -> Unit,
) {
    val values = listOf(
        listOf(
            ButtonValue.CLEAR,
            ButtonValue.PARENTHESIS,
            ButtonValue.MODULE,
            ButtonValue.DELETE
        ),
        listOf(
            ButtonValue.ONE,
            ButtonValue.TWO,
            ButtonValue.THREE,
            ButtonValue.DIVIDE,
        ),
        listOf(
            ButtonValue.FOUR,
            ButtonValue.FIVE,
            ButtonValue.SIX,
            ButtonValue.MULTIPLY,
        ),
        listOf(
            ButtonValue.SEVEN,
            ButtonValue.EIGHT,
            ButtonValue.NINE,
            ButtonValue.MINUS,
        ),
        listOf(
            ButtonValue.POINT,
            ButtonValue.ZERO,
            ButtonValue.GET_RESULT,
            ButtonValue.PLUS,
        )
    )
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        AdaptiveGrid(
            modifier = Modifier.align(Alignment.BottomCenter),
            rowCount = 5,
            columnCount = 4,
            gridItem = { size: Dp, padding: Dp, position: Pair<Int, Int> ->
                CalculatorButton(
                    modifier = Modifier.padding(padding),
                    buttonSize = size,
                    value = values[position.first][position.second].value,
                    onClick = { value ->
                        onButtonClick(value)
                    }
                )
            },
            gridItemPadding = 2.dp
        )
    }
}

@Preview
@Composable
fun ButtonsGrid_Preview() {
    ButtonsGrid(
        onButtonClick = {
        }
    )
}

enum class ButtonValue(val value: String) {

    ZERO("0"),
    ONE("1"),
    TWO("2"),
    THREE("3"),
    FOUR("4"),
    FIVE("5"),
    SIX("6"),
    SEVEN("7"),
    EIGHT("8"),
    NINE("9"),

    PLUS("+"),
    MINUS("-"),
    MULTIPLY("*"),
    DIVIDE("/"),
    MODULE("%"),

    DELETE("DEL"),
    CLEAR("C"),
    PARENTHESIS("()"),
    POINT("."),
    GET_RESULT("=")
}
