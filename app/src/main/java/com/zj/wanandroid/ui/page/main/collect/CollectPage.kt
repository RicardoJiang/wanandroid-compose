package com.zj.wanandroid.ui.page.main.collect

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.ScaffoldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.google.accompanist.flowlayout.FlowRow
import com.zj.wanandroid.data.bean.WebData
import com.zj.wanandroid.theme.BottomNavBarHeight
import com.zj.wanandroid.ui.page.common.RouteName
import com.zj.wanandroid.ui.widgets.*
import com.zj.wanandroid.utils.RouteUtils

@ExperimentalFoundationApi
@Composable
fun CollectPage(
    navCtrl: NavHostController,
    scaffoldState: ScaffoldState,
    viewModel: CollectViewModel = hiltViewModel()
) {
    val viewStates = viewModel.viewStates
    val collectPaging = viewStates.pagingData?.collectAsLazyPagingItems()
    val webUrls = viewStates.urlList
    val titles = viewStates.titles
    val isRefreshing = viewStates.isRefreshing
    val listState =
        if ((collectPaging?.itemCount ?: 0) > 0) viewStates.listState else LazyListState()

    DisposableEffect(Unit) {
        Log.i("debug", "onStart")
        viewModel.dispatch(CollectViewAction.OnStart)
        onDispose {
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = BottomNavBarHeight)
    ) {
        AppToolsBar(title = "我的收藏")

        if (!viewStates.isLogged) {
            EmptyView(
                tips = "点击登录",
                imageVector = Icons.Default.Face
            ) {
                RouteUtils.navTo(navCtrl, RouteName.LOGIN)
            }
        } else {
            collectPaging?.let {
                RefreshList(it, listState = listState, isRefreshing = isRefreshing, onRefresh = {
                    viewModel.dispatch(CollectViewAction.Refresh)
                }) {
                    if (!webUrls.isNullOrEmpty()) {
                        stickyHeader {
                            ListTitle(title = titles[1].text)
                        }
                        item {
                            FlowRow(
                                modifier = Modifier.padding(10.dp)
                            ) {
                                webUrls?.forEachIndexed { index, website ->
                                    LabelTextButton(
                                        text = website.name ?: "标签",
                                        modifier = Modifier.padding(end = 10.dp, bottom = 10.dp),
                                        onClick = {
                                            RouteUtils.navTo(
                                                navCtrl,
                                                RouteName.WEB_VIEW,
                                                WebData(website.name, website.link!!)
                                            )
                                        }
                                    )
                                }
                            }
                        }
                    }
                    stickyHeader {
                        ListTitle(title = titles[0].text)
                    }
                    if (collectPaging.itemCount > 0) {
                        items(collectPaging) { collectItem ->
                            CollectListItemView(
                                collectItem!!,
                                onClick = {
                                    RouteUtils.navTo(
                                        navCtrl,
                                        RouteName.WEB_VIEW,
                                        WebData(collectItem.title, collectItem.link)
                                    )
                                })
                        }
                    }
                }
            }
        }
    }
}