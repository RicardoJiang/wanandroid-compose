package com.zj.wanandroid.ui.page.main.home.square

import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
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
    val squareData = viewModel.viewStates.pagingData.collectAsLazyPagingItems()

    RefreshList(squareData) {
        itemsIndexed(squareData) { _, item ->
            MultiStateItemView(
                data = item!!,
                onSelected = {
                    RouteUtils.navTo(navCtrl, RouteName.WEB_VIEW, it)
                })
        }
    }
}