package com.zj.wanandroid.ui.page.main.category

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.zj.wanandroid.theme.BottomNavBarHeight
import com.zj.wanandroid.ui.page.main.category.navi.NaviPage
import com.zj.wanandroid.ui.page.main.category.pubaccount.PublicAccountPage
import com.zj.wanandroid.ui.page.main.category.stucture.StructurePage
import com.zj.wanandroid.ui.widgets.TextTabBar

import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun CategoryPage(
    navCtrl: NavHostController,
    categoryIndex: Int = 0,
    viewModel: CategoryViewModel = hiltViewModel(),
    onPageSelected: (position: Int) -> Unit,
) {

    val titles = viewModel.titles
    Box(modifier = Modifier.padding(bottom = BottomNavBarHeight)) {
        Column {
            val pagerState = rememberPagerState(
                initialPage = categoryIndex,
            )
            val scopeState = rememberCoroutineScope()

            Row {
                TextTabBar(
                    index = pagerState.currentPage,
                    tabTexts = titles,
                    modifier = Modifier.weight(1f),
                    contentAlign = Alignment.CenterStart,
                    onTabSelected = { index ->
                        scopeState.launch {
                            pagerState.scrollToPage(index)
                        }
                    }
                )
            }

            HorizontalPager(count = titles.size,state = pagerState) { page ->
                onPageSelected(pagerState.currentPage)
                when (page) {
                    0 -> StructurePage(navCtrl)
                    1 -> NaviPage(navCtrl)
                    2 -> PublicAccountPage(navCtrl)
                }
            }
        }
    }
}
