package com.zj.wanandroid.ui.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zj.wanandroid.theme.AppTheme
import com.zj.wanandroid.theme.TabBarHeight

/**
 * TabLayout
 */
@Composable
fun TextTabBar(
    index: Int,
    tabTexts: List<TabTitle>,
    modifier: Modifier = Modifier,
    bgColor: Color = AppTheme.colors.themeUi,
    contentColor: Color = Color.White,
    onTabSelected: ((index: Int) -> Unit)? = null
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(TabBarHeight)
            .background(bgColor)
            .horizontalScroll(state = rememberScrollState())
    ) {
        Row(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
        ) {
            tabTexts.forEachIndexed { i, tabTitle ->
                Text(
                    text = tabTitle.text,
                    fontSize = if (index == i) 20.sp else 15.sp,
                    fontWeight = if (index == i) FontWeight.SemiBold else FontWeight.Normal,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(horizontal = 10.dp)
                        .clickable {
                            onTabSelected?.invoke(i)
                        },
                    color = contentColor
                )
            }
        }
    }
}

data class TabTitle(
    val id: Int,
    val text: String,
    var cachePosition: Int = 0,
    var selected: Boolean = false
)