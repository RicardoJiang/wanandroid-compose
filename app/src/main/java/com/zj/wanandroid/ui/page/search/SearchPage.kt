package com.zj.wanandroid.ui.page.search

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.google.accompanist.flowlayout.FlowRow
import com.zj.wanandroid.R
import com.zj.wanandroid.data.bean.Hotkey
import com.zj.wanandroid.theme.AppTheme
import com.zj.wanandroid.theme.ToolBarHeight
import com.zj.wanandroid.ui.page.common.RouteName
import com.zj.wanandroid.ui.widgets.LabelTextButton
import com.zj.wanandroid.ui.widgets.ListTitle
import com.zj.wanandroid.ui.widgets.MediumTitle
import com.zj.wanandroid.ui.widgets.MultiStateItemView
import com.zj.wanandroid.utils.RouteUtils

@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun SearchPage(
    navCtrl: NavHostController,
    scaffoldState: ScaffoldState,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val viewStates = viewModel.viewStates
    val keyboardCtrl = LocalSoftwareKeyboardController.current
    val hotkeys = viewStates.hotKeys
    val queries = viewStates.searchResult?.collectAsLazyPagingItems()
    Column(Modifier.fillMaxSize()) {

        SearchHead(
            keyWord = viewStates.searchContent,
            onTextChange = {
                viewModel.dispatch(SearchViewAction.SetSearchKey(it))
            },
            onSearchClick = {
                if (it.trim().isNotEmpty()) {
                    viewModel.dispatch(SearchViewAction.Search)
                }
                keyboardCtrl?.hide()
            },
            navController = navCtrl
        )

        LazyColumn {
            // part1. 搜索热词
            stickyHeader {
                ListTitle(title = "搜索热词")
            }
            item {
                if (hotkeys.isNotEmpty()) {
                    Box {
                        HotkeyItem(
                            hotkeys = hotkeys,
                            onSelected = { text ->
                                viewModel.dispatch(SearchViewAction.SetSearchKey(text))
                                viewModel.dispatch(SearchViewAction.Search)
                            })
                    }
                }
            }

            // part2. 搜索列表
            stickyHeader {
                ListTitle(title = "搜索结果")
            }
            if (queries != null) {
                itemsIndexed(queries) { index, item ->
                    MultiStateItemView(
                        data = item!!,
                        onSelected = { data ->
                            RouteUtils.navTo(navCtrl, RouteName.WEB_VIEW, data)
                        })
                }
            }
        }
    }
}

/**
 * 搜索框
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SearchHead(
    keyWord: String,
    onTextChange: (text: String) -> Unit,
    onSearchClick: (key: String) -> Unit,
    navController: NavHostController
) {

    Box(
        Modifier
            .fillMaxWidth()
            .height(ToolBarHeight)
            .background(AppTheme.colors.themeUi)
    ) {
        Row(
            Modifier
                .align(Alignment.Center)
                .padding(10.dp)
        ) {
            Icon(
                painterResource(R.drawable.icon_back_white),
                null,
                Modifier
                    .clickable(onClick = {
                        navController.popBackStack()
                    })
                    .align(Alignment.CenterVertically)
                    .size(20.dp)
                    .padding(end = 10.dp),
                tint = AppTheme.colors.mainColor
            )
            BasicTextField(
                value = keyWord,
                onValueChange = { onTextChange(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .height(28.dp)
                    .background(
                        color = AppTheme.colors.mainColor,
                        shape = RoundedCornerShape(14.dp),
                    )
                    .padding(start = 10.dp, top = 4.dp)
                    .align(Alignment.CenterVertically),
                maxLines = 1,
                singleLine = true,
                textStyle = TextStyle(
                    color = AppTheme.colors.textSecondary,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = { onSearchClick(keyWord) }),
            )
            MediumTitle(
                title = "搜索",
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(start = 10.dp)
                    .combinedClickable(onClick = { onSearchClick(keyWord) }),
                color = AppTheme.colors.mainColor,
            )
        }
    }
}

/**
 * 搜索热词的item
 */
@Composable
fun HotkeyItem(
    hotkeys: List<Hotkey>,
    onSelected: (key: String) -> Unit
) {
    FlowRow(Modifier.padding(10.dp)) {
        hotkeys.forEach {
            LabelTextButton(
                text = it.name ?: "",
                isSelect = false,
                modifier = Modifier.padding(end = 5.dp, bottom = 5.dp),
                onClick = {
                    onSelected(it.name!!)
                }
            )
        }
    }
}