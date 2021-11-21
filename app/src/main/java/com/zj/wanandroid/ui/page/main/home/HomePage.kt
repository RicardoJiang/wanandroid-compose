package com.zj.wanandroid.ui.page.main.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.zj.wanandroid.ui.page.main.home.question.QuestionPage
import com.zj.wanandroid.ui.page.main.home.recommend.RecommendPage
import com.zj.wanandroid.ui.page.main.home.square.SquarePage
import com.zj.wanandroid.ui.widgets.HomeSearchBar
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
            initialPage = 0
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

        HomeSearchBar(
            onSearchClick = {
                //RouteUtils.navTo(navCtrl, RouteName.ARTICLE_SEARCH + "/111")
            }
        )

        HorizontalPager(
            count = titles.size,
            state = pagerState,
            modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 50.dp)
        ) { page ->
            when (page) {
                0 -> SquarePage(navCtrl, scaffoldState)
                1 -> RecommendPage(navCtrl, scaffoldState)
                2 -> QuestionPage(navCtrl, scaffoldState)
            }
        }
    }
}