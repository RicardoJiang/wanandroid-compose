package com.zj.wanandroid.ui.page.main.home.square

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.zj.wanandroid.ui.widgets.MultiStateItemView

@Composable
fun SquarePage(
    navCtrl: NavHostController,
    scaffoldState: ScaffoldState,
    viewModel: SquareViewModel = hiltViewModel()
) {
    val squareData = viewModel.viewStates.pagingData?.collectAsLazyPagingItems()
    val refreshing: Boolean = viewModel.viewStates.isRefreshing
    val swipeRefreshState = rememberSwipeRefreshState(refreshing)

    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = { //viewModel.refresh()
        }
    ) {

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(top = 10.dp)
        ) {
            if (squareData!!.itemCount > 0) {
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
//                onScrollChangeListener(listState.firstVisibleItemIndex)
            } else {
                item {
//                    EmptyView()
                }
            }


        }
    }
}