package com.zj.wanandroid.ui.page.main.home.square

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.zj.wanandroid.ui.page.common.RouteName
import com.zj.wanandroid.ui.widgets.MultiStateItemView
import com.zj.wanandroid.ui.widgets.RefreshList
import com.zj.wanandroid.utils.RouteUtils

@Composable
fun SquarePage(
    navCtrl: NavHostController,
    scaffoldState: ScaffoldState,
    viewModel: SquareViewModel = hiltViewModel()
) {
    val viewStates = remember { viewModel.viewStates }
    val squareData = viewStates.pagingData.collectAsLazyPagingItems()
    val listState = if (squareData.itemCount > 0) viewStates.listState else LazyListState()

    RefreshList(squareData, listState = listState) {
        itemsIndexed(squareData) { _, item ->
            MultiStateItemView(
                data = item!!,
                onSelected = {
                    RouteUtils.navTo(navCtrl, RouteName.WEB_VIEW, it)
                })
        }
    }
}