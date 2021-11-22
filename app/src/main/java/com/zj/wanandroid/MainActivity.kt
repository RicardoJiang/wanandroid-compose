package com.zj.wanandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.ui.ExperimentalComposeUiApi
import com.google.accompanist.pager.ExperimentalPagerApi
import com.zj.wanandroid.ui.page.common.AppScaffold
import com.zj.wanandroid.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @ExperimentalFoundationApi
    @ExperimentalComposeUiApi
    @ExperimentalPagerApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                AppScaffold()
            }
        }
    }
}