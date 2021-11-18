package com.zj.wanandroid.ui.widgets

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.placeholder.material.placeholder
import com.zj.wanandroid.theme.AppTheme
import com.zj.wanandroid.theme.buttonCorner
import com.zj.wanandroid.theme.buttonHeight
import com.zj.wanandroid.theme.white1
import org.jetbrains.annotations.NotNull


@Composable
fun AppButton(
    text: String,
    modifier: Modifier = Modifier,
    bgColor: Color = AppTheme.colors.secondBtnBg,
    textColor: Color = AppTheme.colors.textPrimary,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(buttonHeight)
            .background(color = bgColor, shape = RoundedCornerShape(buttonCorner))
            .clickable {
                onClick()
            }
    ) {
        TextContent(text = text, color = textColor, modifier = Modifier.align(Alignment.Center))
    }
}


@Composable
fun PrimaryButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    AppButton(
        text = text,
        modifier = modifier,
        textColor = AppTheme.colors.textPrimary,
        onClick = onClick,
        bgColor = AppTheme.colors.themeUi
    )
}

@Composable
fun SecondlyButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    AppButton(
        text = text,
        modifier = modifier,
        textColor = AppTheme.colors.textSecondary,
        onClick = onClick
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LabelTextButton(
    @NotNull text: String,
    modifier: Modifier = Modifier,
    isSelect: Boolean = true,
    specTextColor: Color? = null,
    cornerValue: Dp = 25.dp / 2,
    isLoading: Boolean = false,
    onClick: (() -> Unit)? = null,
    onLongClick: (() -> Unit)? = null
) {
    Text(
        text = text,
        modifier = modifier
            .height(25.dp)
            .clip(shape = RoundedCornerShape(cornerValue))
            .background(
                color = if (isSelect && !isLoading) AppTheme.colors.themeUi else AppTheme.colors.secondBtnBg,
            )
            .padding(
                horizontal = 10.dp,
                vertical = 3.dp
            )
            .combinedClickable(
                enabled = !isLoading,
                onClick = { onClick?.invoke() },
                onLongClick = { onLongClick?.invoke() }
            )
            .placeholder(
                visible = isLoading,
                color = AppTheme.colors.placeholder
            ),
        fontSize = 13.sp,
        textAlign = TextAlign.Center,
        color = specTextColor ?: if (isSelect) white1 else AppTheme.colors.textSecondary,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
    )
}

