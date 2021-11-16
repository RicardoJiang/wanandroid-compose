package com.zj.wanandroid.ui.page.main.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.zj.wanandroid.ui.page.main.home.question.QuestionPage
import com.zj.wanandroid.ui.page.main.home.recommend.RecommendPage
import com.zj.wanandroid.ui.page.main.home.square.SquarePage
import com.zj.wanandroid.ui.widgets.TextTabBar
import kotlinx.coroutines.launch

@ExperimentalPagerApi
@Composable
fun HomePage(
    navCtrl: NavHostController,
    scaffoldState: ScaffoldState,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val titles = viewModel.viewStates.titles
    val scopeState = rememberCoroutineScope()
    Column {
        val pagerState = rememberPagerState(
            pageCount = titles.size,
            initialPage = 0,
            initialOffscreenLimit = titles.size
        )

        TextTabBar(
            index = pagerState.currentPage,
            tabTexts = titles,
            onTabSelected = { index ->
                scopeState.launch {
                    pagerState.scrollToPage(index)
                }
            }
        )

        HorizontalPager(state = pagerState, dragEnabled = false) { page ->
            when (page) {
                0 -> SquarePage(navCtrl, scaffoldState)
                1 -> RecommendPage(navCtrl, scaffoldState)
                2 -> QuestionPage(navCtrl, scaffoldState)
            }
        }
    }
}