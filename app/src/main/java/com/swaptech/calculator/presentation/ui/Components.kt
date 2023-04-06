package com.swaptech.calculator.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.max
import kotlin.math.min

@Composable
fun CalculatorButton(
    modifier: Modifier = Modifier,
    buttonSize: Dp,
    value: String,
    onClick: (String) -> Unit,
    backgroundColor: Color = Color.LightGray,
) {
    val interactionSource = remember {
        MutableInteractionSource()
    }
    val fraction: Double = 1 / 2.0
    val fontSize = with(LocalDensity.current) {
        (buttonSize.toPx() * fraction).toFloat().toSp()
    }
    /**
     *  Uses clip() because ripple effect does not work correctly
     *  with "shape" parameter of Surface
     */
    Surface(
        modifier = modifier
            .clip(RoundedCornerShape(buttonSize))
            .clickable(
                interactionSource = interactionSource,
                indication = rememberRipple()
            ) {
                onClick(value)
            }
            .size(buttonSize),
        color = backgroundColor,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = value,
                fontSize = fontSize,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview
@Composable
fun CalculatorButton_Preview() {
    CalculatorButton(
        value = "/",
        buttonSize = 70.dp,
        onClick = {}
    )
}


@Composable
fun AdaptiveGrid(
    modifier: Modifier = Modifier,
    alignmentInContainer: Alignment = Alignment.BottomCenter,
    rowCount: Int,
    columnCount: Int,
    gridItem: @Composable (size: Dp, padding: Dp, position: Pair<Int, Int>) -> Unit,
    gridItemPadding: Dp,
) {
    val density = LocalDensity.current
    BoxWithConstraints(
        modifier = modifier,
        contentAlignment = alignmentInContainer
    ) {
        val contentWidthPx = with(density) {
            maxWidth.toPx()
        }
        val contentHeightPx = with(density) {
            maxHeight.toPx()
        }
        val maxCountItemsFromRowAndColumn = max(rowCount, columnCount)
        val gridItemPaddingPx = with(density) {
            gridItemPadding.toPx()
        }
        val countPaddingSides = 4
        val gridItemSizePx =
            (min(contentWidthPx, contentHeightPx) / maxCountItemsFromRowAndColumn) -
                    (gridItemPaddingPx * countPaddingSides)
        val gridItemSizeDp = with(density) {
            gridItemSizePx.toDp()
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(gridItemPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            repeat(rowCount) { i ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    repeat(columnCount) { j ->
                        gridItem(
                            gridItemSizeDp,
                            gridItemPadding,
                            Pair(i, j)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Calculation(
    expression: String,
    verticalOrientation: Boolean,
    result: String,
    onHistoryValueClick: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(6.dp)
                .fillMaxWidth(),
        ) {
            Text(
                modifier = Modifier
                    .clickable {
                        onHistoryValueClick(expression)
                    }
                    .padding(
                        horizontal = 16.dp,
                        vertical = if (verticalOrientation) 8.dp else 2.dp
                    )
                    .fillMaxWidth(),
                text = expression,
                fontSize = if (verticalOrientation) 40.sp else 16.sp,
                fontWeight = FontWeight.SemiBold,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                modifier = Modifier
                    .clickable {
                        onHistoryValueClick(result)
                    }
                    .padding(
                        horizontal = 16.dp,
                        vertical = if (verticalOrientation) 8.dp else 2.dp
                    )
                    .fillMaxWidth(),
                text = result,
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f),
                fontWeight = FontWeight.SemiBold,
                fontSize = if (verticalOrientation) 40.sp else 16.sp,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview
@Composable
fun HistoryItem_Preview() {
    Calculation(
        expression = "2*(2+2)",
        verticalOrientation = false,
        result = "8",
        onHistoryValueClick = {
        }
    )
}
