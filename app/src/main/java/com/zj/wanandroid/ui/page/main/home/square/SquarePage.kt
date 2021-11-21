package com.zj.wanandroid.ui.page.main.home.square

import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.zj.wanandroid.ui.widgets.MultiStateItemView
import com.zj.wanandroid.ui.widgets.RefreshList

@Composable
fun SquarePage(
    navCtrl: NavHostController,
    scaffoldState: ScaffoldState,
    viewModel: SquareViewModel = hiltViewModel()
) {
    val squareData = viewModel.viewStates.pagingData.collectAsLazyPagingItems()

    RefreshList(squareData) {
            itemsIndexed(squareData) { index, item ->
                MultiStateItemView(
                    data = item!!,
                    onSelected = {
//                            viewModel.saveDataToHistory(item)
//                            viewModel.savePosition(listState.firstVisibleItemIndex)
//                            RouteUtils.navTo(navCtrl, RouteName.WEB_VIEW, it)
                    },
                    onCollectClick = {
//                            if (item.collect) {
//                                viewModel.uncollectArticleById(it)
//                                squareData.peek(index)?.collect = false
//                            } else {
//                                viewModel.collectArticleById(it)
//                                squareData.peek(index)?.collect = true
//                            }

                    },
                    onUserClick = { userId ->
//                            RouteUtils.navTo(navCtrl, RouteName.SHARER, userId)
                    })
        }
    }
}