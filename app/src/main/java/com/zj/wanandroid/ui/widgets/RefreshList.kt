package com.zj.wanandroid.ui.widgets

import android.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.zj.wanandroid.theme.AppTheme

@Composable
fun <T : Any> RefreshList(
    lazyPagingItems: LazyPagingItems<T>,
    listState: LazyListState = rememberLazyListState(),
    itemContent: LazyListScope.() -> Unit
) {
    val rememberSwipeRefreshState = rememberSwipeRefreshState(isRefreshing = false)
    //错误页
    val err = lazyPagingItems.loadState.refresh is LoadState.Error
    if (err) {
        ErrorContent { lazyPagingItems.retry() }
        return
    }
    SwipeRefresh(
        state = rememberSwipeRefreshState,
        onRefresh = { lazyPagingItems.refresh() }
    ) {
        //刷新状态
        rememberSwipeRefreshState.isRefreshing =
            lazyPagingItems.loadState.refresh is LoadState.Loading
        //列表
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            state = listState
        ) {
            //条目布局
            itemContent()
            //加载更多状态：加载中和加载错误,没有更多
            if (!rememberSwipeRefreshState.isRefreshing) {
                item {
                    lazyPagingItems.apply {
                        when (loadState.append) {
                            is LoadState.Loading -> LoadingItem()
                            is LoadState.Error -> ErrorItem { retry() }
                            is LoadState.NotLoading -> {
                                if (loadState.append.endOfPaginationReached) {
                                    NoMoreItem()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ErrorContent(retry: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.align(Alignment.Center)) {
            Image(
                painter = painterResource(id = R.drawable.stat_notify_error),
                contentDescription = null,
                colorFilter = ColorFilter.tint(Color.Red),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Text(
                text = "请求出错啦",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 10.dp)
            )
            Button(
                onClick = { retry() },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(10.dp),
                colors = buttonColors(backgroundColor = AppTheme.colors.themeUi)
            ) {
                Text(text = "重试")
            }
        }
    }
}

@Composable
fun ErrorItem(retry: () -> Unit) {
    Button(
        onClick = { retry() },
        modifier = Modifier.padding(10.dp),
        colors = buttonColors(backgroundColor = AppTheme.colors.themeUi)
    ) {
        Text(text = "重试")
    }
}

@Composable
fun NoMoreItem() {
    Text(
        text = "没有更多了",
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        textAlign = TextAlign.Center
    )
}

@Composable
fun LoadingItem() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp), contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = AppTheme.colors.themeUi,
            modifier = Modifier
                .padding(10.dp)
                .height(50.dp)
        )
    }
}
