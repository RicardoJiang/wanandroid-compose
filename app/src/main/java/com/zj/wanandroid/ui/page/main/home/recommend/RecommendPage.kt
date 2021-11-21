package com.zj.wanandroid.ui.page.main.home.recommend

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.google.accompanist.pager.ExperimentalPagerApi
import com.zj.wanandroid.data.bean.WebData
import com.zj.wanandroid.ui.page.common.RouteName
import com.zj.wanandroid.ui.widgets.Banner
import com.zj.wanandroid.ui.widgets.MultiStateItemView
import com.zj.wanandroid.ui.widgets.RefreshList
import com.zj.wanandroid.utils.RouteUtils


@ExperimentalPagerApi
@Composable
fun RecommendPage(
    navCtrl: NavHostController,
    scaffoldState: ScaffoldState,
    viewModel: RecommendViewModel = hiltViewModel()
) {
    val viewStates = viewModel.viewStates
    val recommendData = viewStates.pagingData.collectAsLazyPagingItems()
    val banners = viewStates.imageList
    val topArticle = viewStates.topArticles
    val isRefreshing = viewStates.isRefreshing
    val listState = if (recommendData.itemCount > 0) viewStates.listState else LazyListState()

    RefreshList(recommendData, listState = listState, isRefreshing = isRefreshing, onRefresh = {
        viewModel.dispatch(RecommendViewAction.Refresh)
    }) {
        if (banners.isNotEmpty()) {
            item {
                Banner(list = banners) { url, title ->
                    RouteUtils.navTo(navCtrl, RouteName.WEB_VIEW, WebData(title, url))
                }
            }
        }
        if (topArticle.isNotEmpty()) {
            itemsIndexed(topArticle) { index, item ->
                MultiStateItemView(
                    modifier = Modifier.padding(top = if (index == 0) 5.dp else 0.dp),
                    data = item,
                    isTop = true,
                    onSelected = { data ->
                        RouteUtils.navTo(navCtrl, RouteName.WEB_VIEW, data)
                    }
                )
            }
        }

        itemsIndexed(recommendData) { _, item ->
            MultiStateItemView(
                data = item!!,
                onSelected = {
                    RouteUtils.navTo(navCtrl, RouteName.WEB_VIEW, it)
                })
        }
    }
}