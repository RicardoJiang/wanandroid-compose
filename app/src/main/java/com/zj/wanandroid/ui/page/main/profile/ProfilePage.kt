package com.zj.wanandroid.ui.page.main.profile

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.ScaffoldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.google.accompanist.placeholder.material.placeholder
import com.zj.wanandroid.R
import com.zj.wanandroid.data.bean.PointsBean
import com.zj.wanandroid.data.bean.UserInfo
import com.zj.wanandroid.data.bean.WebData
import com.zj.wanandroid.theme.AppTheme
import com.zj.wanandroid.theme.BottomNavBarHeight
import com.zj.wanandroid.theme.ToolBarHeight
import com.zj.wanandroid.theme.white
import com.zj.wanandroid.ui.page.common.RouteName
import com.zj.wanandroid.ui.widgets.*
import com.zj.wanandroid.utils.RouteUtils

@Composable
fun ProfilePage(
    navCtrl: NavHostController,
    scaffoldState: ScaffoldState,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val viewStates = viewModel.viewStates
    val isLogged = viewStates.isLogged
    val userInfo = viewStates.userInfo

    DisposableEffect(Unit) {
        Log.i("debug", "onStart")
        viewModel.dispatch(ProfileViewAction.OnStart)
        onDispose {
        }
    }

    if (viewStates.showLogout) {
        SampleAlertDialog(
            title = "提示",
            content = "退出后，将无法查看我的文章、消息、收藏、积分、浏览记录等功能，确定退出登录吗？",
            onConfirmClick = {
                viewModel.dispatch(ProfileViewAction.Logout)
            },
            onDismiss = {
                viewModel.dispatch(ProfileViewAction.DismissLogoutDialog)
            }
        )
    }

    Column(
        modifier = Modifier
            .padding(bottom = BottomNavBarHeight)
            .fillMaxSize()
            .background(color = AppTheme.colors.mainColor)
    ) {
        if (isLogged && userInfo != null) {
            LazyColumn() {
                item {
                    HeaderPart(
                        navCtrl = navCtrl,
                        userInfo = userInfo,
                        messageCount = 0
                    )
                }
                item {
                    FooterPart(
                        navCtrl = navCtrl,
                        messageCount = 0,
                        onLogout = {
                            viewModel.dispatch(ProfileViewAction.ShowLogoutDialog)
                        })
                }
            }
        } else {
            AppToolsBar(title = "我的")
            EmptyView(
                tips = "点击登录",
                imageVector = Icons.Default.Face
            ) {
                RouteUtils.navTo(navCtrl, RouteName.LOGIN)
            }
        }
    }
}

//基本操作
@Composable
fun FooterPart(
    navCtrl: NavHostController,
    messageCount: Int,
    onLogout: () -> Unit,
) {

    Column(
        modifier = Modifier.padding(bottom = 10.dp)
    ) {
        ArrowRightListItem(
            iconRes = painterResource(R.drawable.ic_message),
            title = "消息",
            msgCount = messageCount
        ) {
            //TODO
        }
        ArrowRightListItem(
            iconRes = painterResource(R.drawable.ic_menu_settings),
            title = "设置"
        ) {
            onLogout.invoke()
        }
        ArrowRightListItem(
            iconRes = painterResource(R.drawable.ic_feedback),
            title = "WanAndroid"
        ) {
            RouteUtils.navTo(
                navCtrl = navCtrl,
                destinationName = RouteName.WEB_VIEW,
                args = WebData(title = "官方网站", url = "https://www.wanandroid.com/index")
            )
        }
        ArrowRightListItem(
            iconRes = painterResource(R.drawable.ic_data),
            title = "积分规则"
        ) {
            RouteUtils.navTo(
                navCtrl = navCtrl,
                destinationName = RouteName.WEB_VIEW,
                args = WebData(title = "积分规则", url = "https://www.wanandroid.com/blog/show/2653")
            )
        }
        ArrowRightListItem(
            iconRes = painterResource(R.drawable.ic_message),
            title = "退出登录"
        ) {
            onLogout.invoke()
        }
    }
}

//用户信息
@Composable
fun HeaderPart(
    navCtrl: NavHostController,
    userInfo: UserInfo,
    messageCount: Int,
) {
    Column {
        ProfileToolBar(
            msgCount = messageCount,
            onMessageIconClick = {
                //TODO
            },
            onDeleteIconClick = {
                //TODO
            },
            onDashboardIconClick = {
                //TODO
            },
        )
        UserInfoItem(null, userInfo)
        UserOptionsItem(
            onCollectClick = {
                try {
                    navCtrl.navigate(RouteName.COLLECTION) {
                        popUpTo(navCtrl.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
            },
            onMyShareClick = {
                //TODO
            },
            onHistoryClick = {
                //TODO
            },
            onRankingClick = {
                //TODO
            }
        )
    }
}

//调色板、清理缓存、消息的ToolBar
@Composable
private fun ProfileToolBar(
    msgCount: Int,
    onMessageIconClick: () -> Unit,
    onDeleteIconClick: () -> Unit,
    onDashboardIconClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(ToolBarHeight)
            .background(AppTheme.colors.themeUi)
    ) {
        Row(modifier = Modifier.align(Alignment.CenterEnd)) {
            Box(
                modifier = Modifier.wrapContentSize()
            ) {
                NotificationIcon(
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { onMessageIconClick.invoke() }
                )
                if (msgCount > 0) {
                    DotView(modifier = Modifier.align(Alignment.TopEnd))
                }
            }
            Icon(
                Icons.Default.Delete,
                contentDescription = "Clear cache",
                modifier = Modifier
                    .padding(start = 10.dp)
                    .clickable {
                        onDeleteIconClick.invoke()
                    },
                tint = white
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_theme),
                contentDescription = "Switch theme",
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .clickable {
                        onDashboardIconClick.invoke()
                    },
                tint = white
            )
        }
    }
}

//用户基本信息
@Composable
private fun UserInfoItem(myPoints: PointsBean?, userInfo: UserInfo?) {

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(AppTheme.colors.themeUi)
    ) {

        val (icon, info) = createRefs()

        Image(
            painter = painterResource(id = R.mipmap.ic_account),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(start = 20.dp)
                .width(48.dp)
                .height(48.dp)
                .clip(RoundedCornerShape(24.dp))
                .placeholder(
                    visible = userInfo == null,
                    color = AppTheme.colors.placeholder
                )
                .constrainAs(icon) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                },
        )

        Column(
            modifier = Modifier
                .padding(start = 10.dp, bottom = 20.dp)
                .constrainAs(info) {
                    start.linkTo(icon.end)
                    top.linkTo(parent.top)
                }
        ) {
            MainTitle(
                title = userInfo?.username ?: "",
                modifier = Modifier.placeholder(
                    visible = userInfo == null,
                    color = AppTheme.colors.placeholder
                )
            )
            if (userInfo?.email?.isNotEmpty() == true) {
                MiniTitle(
                    text = "email: ${userInfo.email}",
                    modifier = Modifier.padding(top = 5.dp)
                )
            }
            Row(
                modifier = Modifier.padding(top = 5.dp)
            ) {
                TagView(
                    tagText = "Lv${myPoints?.level ?: "99"}",
                    tagBgColor = AppTheme.colors.themeUi,
                    borderColor = AppTheme.colors.textSecondary,
                    isLoading = false
                )
                TagView(
                    modifier = Modifier.padding(start = 5.dp),
                    tagText = "积分${myPoints?.coinCount ?: "10086"}",
                    tagBgColor = AppTheme.colors.themeUi,
                    borderColor = AppTheme.colors.textSecondary,
                    isLoading = false
                )
            }
        }
    }
}

//我的收藏、我的分享、历史记录、积分排行榜
@Composable
private fun UserOptionsItem(
    onCollectClick: () -> Unit,
    onMyShareClick: () -> Unit,
    onHistoryClick: () -> Unit,
    onRankingClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 20.dp)
    ) {
        ProfileOptionItem(
            modifier = Modifier.weight(1f),
            title = "我的收藏",
            iconRes = Icons.Default.FavoriteBorder
        ) {
            onCollectClick.invoke()
        }
        ProfileOptionItem(
            modifier = Modifier.weight(1f),
            title = "我的文章",
            iconRes = painterResource(R.drawable.ic_article)
        ) {
            onMyShareClick.invoke()
        }
        ProfileOptionItem(
            modifier = Modifier.weight(1f),
            title = "历史浏览",
            iconRes = painterResource(R.drawable.ic_history_record)
        ) {
            onHistoryClick.invoke()
        }
        ProfileOptionItem(
            modifier = Modifier.weight(1f),
            title = "积分排行",
            iconRes = painterResource(R.drawable.ic_ranking)
        ) {
            onRankingClick.invoke()
        }
    }
}

@Composable
private fun ProfileOptionItem(
    modifier: Modifier,
    title: String,
    iconRes: Any,
    onClick: () -> Unit
) {
    Column(modifier = modifier.clickable { onClick() }) {
        when (iconRes) {
            is Painter -> {
                Icon(
                    painter = iconRes,
                    contentDescription = null,
                    tint = AppTheme.colors.icon,
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }
            is ImageVector -> {
                Icon(
                    imageVector = iconRes,
                    contentDescription = null,
                    tint = AppTheme.colors.icon,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }

        MiniTitle(
            text = title,
            modifier = Modifier
                .padding(top = 5.dp)
                .align(Alignment.CenterHorizontally)
        )
    }

}