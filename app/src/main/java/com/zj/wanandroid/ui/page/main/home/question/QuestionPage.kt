package com.zj.wanandroid.ui.page.main.home.question

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.Card
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.zj.wanandroid.data.bean.Article
import com.zj.wanandroid.data.bean.WebData
import com.zj.wanandroid.theme.AppTheme
import com.zj.wanandroid.ui.page.common.RouteName
import com.zj.wanandroid.ui.widgets.MainTitle
import com.zj.wanandroid.ui.widgets.MiniTitle
import com.zj.wanandroid.ui.widgets.RefreshList
import com.zj.wanandroid.ui.widgets.TextContent
import com.zj.wanandroid.utils.RegexUtils
import com.zj.wanandroid.utils.RouteUtils

@Composable
fun QuestionPage(
    navCtrl: NavHostController,
    scaffoldState: ScaffoldState,
    viewModel: QuestionViewModel = hiltViewModel()
) {
    val viewStates = viewModel.viewStates
    val questionData = viewStates.pagingData.collectAsLazyPagingItems()
    val listState = if (questionData.itemCount > 0) viewStates.listState else LazyListState()

    RefreshList(questionData,listState = listState) {
        itemsIndexed(questionData) { _, item ->
            WenDaItem(item!!) {
                item.run {
                    RouteUtils.navTo(
                        navCtrl,
                        RouteName.WEB_VIEW,
                        WebData(title, link!!)
                    )
                }
            }
        }
    }
}

@Composable
private fun WenDaItem(article: Article, isLoading: Boolean = false, onClick: () -> Unit = {}) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(5.dp)
            .clickable(enabled = !isLoading) {
                onClick.invoke()
            }
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {

            //标题
            MainTitle(
                title = titleSubstring(article.title) ?: "每日一问",
                maxLine = 2,
                isLoading = isLoading,
                modifier = Modifier.fillMaxWidth()
            )
            Row(
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
                    .padding(vertical = 5.dp)
            ) {

                //UserIcon(isLoading = isLoading)
                MiniTitle(
                    text = "作者:${article.author ?: "xxx"}",
                    color = AppTheme.colors.textSecondary,
                    modifier = Modifier
                        .padding(start = if (isLoading) 5.dp else 0.dp)
                        .align(Alignment.CenterVertically),
                    isLoading = isLoading
                )
                Spacer(modifier = Modifier.width(10.dp))
                //发布时间
                //TimerIcon(isLoading = isLoading)
                MiniTitle(
                    modifier = Modifier
                        .padding(start = if (isLoading) 5.dp else 0.dp)
                        .align(Alignment.CenterVertically),
                    text = "日期:${RegexUtils().timestamp(article.niceDate) ?: "2020"}",
                    color = AppTheme.colors.textSecondary,
                    maxLines = 1,
                    isLoading = isLoading
                )
            }

            TextContent(
                text = RegexUtils().symbolClear(article.desc),
                maxLines = 3,
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 60.dp),
                isLoading = isLoading
            )
        }
    }
}

private fun titleSubstring(oldText: String?): String? {
    return oldText?.run {
        var newText = this
        if (startsWith("每日一问") && contains(" | ")) {
            newText = substring(indexOf(" | ") + 3, length)
        }
        newText
    }
}