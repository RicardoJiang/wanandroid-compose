package com.zj.wanandroid.ui.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zj.wanandroid.R
import com.zj.wanandroid.theme.AppTheme
import com.zj.wanandroid.theme.SearchBarHeight

@Composable
fun HomeSearchBar(
    onSearchClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(SearchBarHeight)
            .background(color = AppTheme.colors.themeUi)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 32.dp)
                .height(30.dp)
                .align(alignment = Alignment.Top)
                .weight(1f)
                .background(
                    color = AppTheme.colors.mainColor,
                    shape = RoundedCornerShape(12.5.dp)
                )
                .clickable { onSearchClick() }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = "搜索",
                tint = AppTheme.colors.themeUi,
                modifier = Modifier
                    .size(25.dp)
                    .padding(start = 10.dp)
                    .align(Alignment.CenterVertically)
            )
            Box(
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
                    .padding(start = 10.dp)
            ) {
                Text(text = "搜索关键词以空格形式隔开", fontSize = 13.sp, color = AppTheme.colors.textSecondary)
            }
        }
    }
}