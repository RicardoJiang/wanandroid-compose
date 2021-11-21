package com.zj.wanandroid.ui.page.main.category.pubaccount

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.zj.wanandroid.data.bean.ParentBean
import com.zj.wanandroid.theme.AppTheme
import com.zj.wanandroid.theme.white1
import com.zj.wanandroid.ui.widgets.LcePage

@ExperimentalFoundationApi
@Composable
fun PublicAccountPage(
    navCtrl: NavHostController,
    viewModel: PubAccountViewModel = hiltViewModel()
) {
    val viewStates = viewModel.viewStates

    LcePage(pageState = viewStates.pageState, onRetry = {
        viewModel.dispatch(PubAccountViewAction.FetchData)
    }) {
        LazyVerticalGrid(
            cells = GridCells.Fixed(2),
            modifier = Modifier
                .background(AppTheme.colors.background)
                .wrapContentHeight()
                .padding(10.dp),
            state = viewStates.listState
        ) {
            itemsIndexed(viewStates.dataList) { index, item ->
                Box(Modifier.padding(vertical = 5.dp)) {
                    when (index % 4) {
                        0 -> PublicAccountItem(
                            parent = item,
                            click = {},
                            isPrimary = true,
                            roundedCorner = RoundedCornerShape(topStart = 5.dp, bottomStart = 5.dp)
                        )
                        1 -> PublicAccountItem(
                            parent = item,
                            click = {},
                            isPrimary = false,
                            roundedCorner = RoundedCornerShape(topEnd = 5.dp, bottomEnd = 5.dp)
                        )
                        2 -> PublicAccountItem(
                            parent = item,
                            click = {},
                            isPrimary = false,
                            roundedCorner = RoundedCornerShape(topStart = 5.dp, bottomStart = 5.dp)
                        )
                        3 -> PublicAccountItem(
                            parent = item,
                            click = {},
                            isPrimary = true,
                            roundedCorner = RoundedCornerShape(topEnd = 5.dp, bottomEnd = 5.dp)
                        )
                    }
                }

            }

        }
    }
}

@Composable
fun SpecialText(
    parent: ParentBean,
    textColor: Color,
    bgColor: Color,
    shape: RoundedCornerShape,
    onClick: (ParentBean) -> Unit
) {
    Text(
        text = parent.name!!,
        textAlign = TextAlign.Center,
        fontSize = 18.sp,
        color = textColor,
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(
                color = bgColor,
                shape = shape
            )
            .padding(top = 30.dp)
            .clickable {
                onClick(parent)
            },
        fontWeight = FontWeight.W500,
    )
}

@Composable
private fun PublicAccountItem(
    parent: ParentBean,
    click: (ParentBean) -> Unit,
    isPrimary: Boolean = true,
    roundedCorner: RoundedCornerShape = RoundedCornerShape(5.dp)
) {
    SpecialText(
        parent = parent,
        textColor = if (isPrimary) white1 else AppTheme.colors.textSecondary,
        bgColor = if (isPrimary) AppTheme.colors.themeUi else white1,
        shape = roundedCorner
    ) {
        click(it)
    }
}