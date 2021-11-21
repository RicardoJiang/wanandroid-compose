package com.zj.wanandroid.ui.page.main.category.navi

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.flowlayout.FlowRow
import com.zj.wanandroid.data.bean.NaviWrapper
import com.zj.wanandroid.data.bean.WebData
import com.zj.wanandroid.theme.AppTheme
import com.zj.wanandroid.ui.widgets.LabelTextButton
import com.zj.wanandroid.ui.widgets.LcePage
import com.zj.wanandroid.ui.widgets.ListTitle

@ExperimentalFoundationApi
@Composable
fun NaviPage(navCtrl: NavHostController, viewModel: NaviViewModel = hiltViewModel()) {
    val viewStates = viewModel.viewStates

    LcePage(pageState = viewStates.pageState, onRetry = {
        viewModel.dispatch(NaviViewAction.FetchData)
    }) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = viewStates.listState,
            contentPadding = PaddingValues(vertical = 10.dp)
        ) {
            viewStates.dataList.forEachIndexed { index, naviBean ->
                stickyHeader { ListTitle(title = naviBean.name ?: "标题") }
                item {
                    NaviItem(naviBean, onSelected = {
                    })
                    if (index <= viewStates.size - 1) {
                        Divider(
                            startIndent = 10.dp,
                            color = AppTheme.colors.divider,
                            thickness = 0.8f.dp
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }
    }
}

@Composable
fun NaviItem(
    wrapper: NaviWrapper,
    isLoading: Boolean = false,
    onSelected: (WebData) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
    ) {
        if (isLoading) {
            ListTitle(title = "我是标题")
            FlowRow(
                modifier = Modifier.padding(top = 10.dp)
            ) {
                for (i in 0..7) {
                    LabelTextButton(
                        text =  "android",
                        modifier = Modifier.padding(start = 5.dp, bottom = 5.dp),
                        isLoading = true
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
        } else {
            if (!wrapper.articles.isNullOrEmpty()) {
                FlowRow(
                    modifier = Modifier.padding(top = 10.dp)
                ) {
                    for (item in wrapper.articles!!) {
                        LabelTextButton(
                            text = item.title ?: "android",
                            modifier = Modifier.padding(start = 5.dp, bottom = 5.dp),
                            onClick = {
                                val webData = WebData(item.title, item.link!!)
                                onSelected(webData)
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
        }

    }
}