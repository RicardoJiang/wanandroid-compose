package com.zj.wanandroid.ui.page.main.category.stucture

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.flowlayout.FlowRow
import com.zj.wanandroid.data.bean.ParentBean
import com.zj.wanandroid.theme.AppTheme
import com.zj.wanandroid.ui.page.common.RouteName
import com.zj.wanandroid.ui.widgets.LabelTextButton
import com.zj.wanandroid.ui.widgets.LcePage
import com.zj.wanandroid.ui.widgets.ListTitle
import com.zj.wanandroid.utils.RouteUtils

@ExperimentalFoundationApi
@Composable
fun StructurePage(
    navCtrl: NavHostController,
    viewModel: StructureViewModel = hiltViewModel()
) {
    val viewStates = viewModel.viewStates

    LcePage(pageState = viewStates.pageState, onRetry = {
        viewModel.dispatch(StructureViewAction.FetchData)
    }) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(AppTheme.colors.background),
            state = viewStates.listState,
            contentPadding = PaddingValues(vertical = 10.dp)
        ) {
            viewStates.dataList.forEachIndexed { position, chapter1 ->
                stickyHeader { ListTitle(title = chapter1.name ?: "标题") }
                item {
                    StructureItem(chapter1, onSelect = { parent ->

                    })
                    if (position <= viewStates.size - 1) {
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
fun StructureItem(
    bean: ParentBean,
    isLoading: Boolean = false,
    onSelect: (parent: ParentBean) -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
    ) {
        if (isLoading) {
            ListTitle(title = "我都标题", isLoading = true)
            FlowRow(
                modifier = Modifier.padding(horizontal = 10.dp)
            ) {
                for (i in 0..7) {
                    LabelTextButton(
                        text = "android",
                        modifier = Modifier.padding(start = 5.dp, bottom = 5.dp),
                        isLoading = true
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
        } else {
            if (!bean.children.isNullOrEmpty()) {
                FlowRow(
                    modifier = Modifier.padding(horizontal = 10.dp)
                ) {
                    for (item in bean.children!!) {
                        LabelTextButton(
                            text = item.name ?: "android",
                            modifier = Modifier.padding(start = 5.dp, bottom = 5.dp),
                            onClick = {
                                onSelect(item)
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
        }

    }
}