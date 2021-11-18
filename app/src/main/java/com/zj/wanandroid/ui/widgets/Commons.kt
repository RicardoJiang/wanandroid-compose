package com.zj.wanandroid.ui.widgets

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.placeholder.material.placeholder
import com.zj.wanandroid.theme.*
import com.zj.wanandroid.ui.page.common.BottomNavRoute

/**
 * 普通标题栏头部
 */
@Composable
fun HamToolBar(
    title: String,
    rightText: String? = null,
    onBack: (() -> Unit)? = null,
    onRightClick: (() -> Unit)? = null,
    imageVector: ImageVector? = null,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(ToolBarHeight)
            .background(AppTheme.colors.themeUi)
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            if (onBack != null) {
                Icon(
                    Icons.Default.ArrowBack,
                    null,
                    Modifier
                        .clickable(onClick = onBack)
                        .align(Alignment.CenterVertically)
                        .padding(12.dp),
                    tint = AppTheme.colors.mainColor
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            if (!rightText.isNullOrEmpty() && imageVector == null) {
                TextContent(
                    text = rightText,
                    color = AppTheme.colors.mainColor,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(horizontal = 20.dp)
                        .clickable { onRightClick?.invoke() }
                )
            }

            if (imageVector != null) {
                Icon(
                    imageVector = imageVector,
                    contentDescription = null,
                    tint = AppTheme.colors.mainColor,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(end = 12.dp)
                        .clickable {
                            onRightClick?.invoke()
                        })
            }
        }
        Text(
            text = title,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 40.dp),
            color = AppTheme.colors.mainColor,
            textAlign = TextAlign.Center,
            fontSize = if (title.length > 14) H5 else ToolBarTitleSize,
            fontWeight = FontWeight.W500,
            maxLines = 1
        )

    }
}


@Composable
fun HomeSearchBar(
    onSearchClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(SearchBarHeight)
            .background(color = AppTheme.colors.themeUi)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 32.dp)
                .height(30.dp)
                .align(alignment = Alignment.Top)
                .weight(1f)
                .background(
                    color = AppTheme.colors.mainColor,
                    shape = RoundedCornerShape(12.5.dp)
                )
                .clickable { onSearchClick() }
        ) {
            Icon(
                painter = painterResource(id = com.zj.wanandroid.R.drawable.ic_search),
                contentDescription = "搜索",
                tint = AppTheme.colors.themeUi,
                modifier = Modifier
                    .size(25.dp)
                    .padding(start = 10.dp)
                    .align(Alignment.CenterVertically)
            )
            Box(
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
                    .padding(start = 10.dp)
            ) {
                Text(text = "搜索关键词以空格形式隔开", fontSize = 13.sp, color = AppTheme.colors.textSecondary)
            }
        }
    }
}


/**
 * TabLayout
 */
@Composable
fun TextTabBar(
    index: Int,
    tabTexts: List<TabTitle>,
    modifier: Modifier = Modifier,
    bgColor: Color = AppTheme.colors.themeUi,
    contentColor: Color = Color.White,
    onTabSelected: ((index: Int) -> Unit)? = null
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(54.dp)
            .background(bgColor)
            .horizontalScroll(state = rememberScrollState())
    ) {
        Row(
            modifier = Modifier
                .align(Alignment.Center)
        ) {
            tabTexts.forEachIndexed { i, tabTitle ->
                Text(
                    text = tabTitle.text,
                    fontSize = if (index == i) 20.sp else 15.sp,
                    fontWeight = if (index == i) FontWeight.SemiBold else FontWeight.Normal,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(horizontal = 10.dp)
                        .clickable {
                            onTabSelected?.invoke(i)
                        },
                    color = contentColor
                )
            }
        }
    }
}

data class TabTitle(
    val id: Int,
    val text: String,
    var cachePosition: Int = 0,
    var selected: Boolean = false
)


@Composable
fun TabBar(
    index: Int = 0,
    tabTexts: List<String>,
    modifier: Modifier = Modifier,
    bgColor: Color,
    contentColor: Color,
    onTabSelected: ((index: Int) -> Unit)?,
    isScrollable: Boolean = true
) {
    if (isScrollable) {
        ScrollableTabRow(
            selectedTabIndex = index,
            modifier = modifier.height(TabBarHeight),
            edgePadding = 0.dp,
            backgroundColor = bgColor,
            contentColor = contentColor,
        ) {
            //var offset: Float by remember { mutableStateOf(0f) }
            tabTexts.forEachIndexed { i, tabText ->
                Text(
                    text = tabText,
                    fontSize = if (index == i) 20.sp else 15.sp,
                    fontWeight = if (index == i) FontWeight.SemiBold else FontWeight.Normal,
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .clickable {
                            if (onTabSelected != null) {
                                onTabSelected(i)
                            }
                        },
                    color = contentColor
                )
            }
        }
    } else {
        TabRow(
            selectedTabIndex = index,
            modifier = modifier.height(TabBarHeight),
            backgroundColor = bgColor,
            contentColor = contentColor,
        ) {
            tabTexts.forEachIndexed { i, tabText ->
                Text(
                    text = tabText,
                    fontSize = if (index == i) 20.sp else 15.sp,
                    fontWeight = if (index == i) FontWeight.SemiBold else FontWeight.Normal,
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .clickable {
                            if (onTabSelected != null) {
                                onTabSelected(i)
                            }
                        },
                    color = contentColor
                )
            }
        }
    }
}

@Composable
fun BottomNavBarView(navCtrl: NavHostController) {
    val bottomNavList = listOf(
        BottomNavRoute.Home,
        BottomNavRoute.Category,
        BottomNavRoute.Collection,
        BottomNavRoute.Profile
    )
    BottomNavigation {
        val navBackStackEntry by navCtrl.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        bottomNavList.forEach { screen ->
            BottomNavigationItem(
                modifier = Modifier.background(AppTheme.colors.themeUi),
                icon = {
                    Icon(
                        imageVector = screen.icon,
                        contentDescription = null
                    )
                },
                label = { Text(text = stringResource(screen.stringId)) },
                selected = currentDestination?.hierarchy?.any { it.route == screen.routeName } == true,
                onClick = {
                    println("BottomNavView当前路由 ===> ${currentDestination?.hierarchy?.toList()}")
                    println("当前路由栈 ===> ${navCtrl.graph.nodes}")
                    if (currentDestination?.route != screen.routeName) {
                        navCtrl.navigate(screen.routeName) {
                            popUpTo(navCtrl.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                })
        }
    }
}


@Composable
fun EmptyView(
    tips: String = "啥都没有~",
    imageVector: ImageVector = Icons.Default.Info,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize(1f)
            .defaultMinSize(minHeight = 480.dp)
    ) {
        Column(
            Modifier
                .wrapContentSize()
                .align(Alignment.Center)
                .clickable { onClick.invoke() }
        ) {
            Icon(
                imageVector = imageVector,
                contentDescription = null,
                tint = AppTheme.colors.textSecondary,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            TextContent(text = tips, modifier = Modifier.padding(top = 10.dp))
        }
    }
}

@Composable
fun SwitchTabBar(
    titles: MutableList<TabTitle>,
    heightValue: Dp? = null,
    selectIndex: Int,
    onSwitchClick: (index: Int) -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(heightValue ?: ToolBarHeight)
            .background(color = AppTheme.colors.themeUi)
            .padding(vertical = 5.dp)
    ) {
        Row(
            modifier = Modifier.align(Alignment.Center)
        ) {
            titles.forEachIndexed { index, tabTitle ->
                MediumTitle(
                    title = tabTitle.text,
                    color = if (index == selectIndex) AppTheme.colors.textPrimary
                    else AppTheme.colors.textSecondary,
                    modifier = Modifier
                        .defaultMinSize(100.dp, 24.dp)
                        .padding(horizontal = 10.dp)
                        .clickable { onSwitchClick.invoke(index) },
                    textAlign = TextAlign.Center
                )
            }
        }
        Box(
            modifier = Modifier
                .width(1.dp)
                .height(16.dp)
                .background(color = AppTheme.colors.textSecondary)
                .align(Alignment.Center)
        )
    }
}


@Composable
fun TagView(
    modifier: Modifier = Modifier,
    tagText: String,
    tagBgColor: Color = AppTheme.colors.background,
    borderColor: Color = AppTheme.colors.themeUi,
    tagTextColor: Color = AppTheme.colors.textSecondary,
    isLoading: Boolean = false,
) {
    Box(
        modifier = modifier
            .wrapContentSize()
            .background(color = tagBgColor)
            .clip(RoundedCornerShape(2.dp))
            .border(width = 1.dp, color = borderColor)
            .placeholder(
                visible = isLoading,
                color = AppTheme.colors.placeholder
            )
    ) {
        MiniTitle(
            text = tagText,
            color = tagTextColor,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 5.dp, vertical = 0.dp),
        )
    }
}


