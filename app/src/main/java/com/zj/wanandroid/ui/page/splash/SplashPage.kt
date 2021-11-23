package com.zj.wanandroid.ui.page.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zj.wanandroid.theme.AppTheme
import com.zj.wanandroid.theme.white
import kotlinx.coroutines.delay

@Composable
fun SplashPage(onNextPage: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.themeUi), contentAlignment = Alignment.TopCenter
    ) {
        LaunchedEffect(Unit) {
            delay(500)
            onNextPage.invoke()
        }
        Text(
            text = "Wan Android",
            fontSize = 32.sp,
            color = white,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(0.dp, 150.dp, 0.dp, 0.dp)
        )
    }
}