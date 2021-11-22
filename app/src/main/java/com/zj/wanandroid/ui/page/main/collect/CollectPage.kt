package com.zj.wanandroid.ui.page.main.collect

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Face
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.zj.wanandroid.theme.BottomNavBarHeight
import com.zj.wanandroid.ui.page.common.RouteName
import com.zj.wanandroid.ui.widgets.AppToolsBar
import com.zj.wanandroid.ui.widgets.EmptyView
import com.zj.wanandroid.utils.RouteUtils

@Composable
fun CollectPage(
    navCtrl: NavHostController,
    scaffoldState: ScaffoldState,
    viewModel: CollectViewModel = hiltViewModel()
) {
    val viewStates = viewModel.viewStates
    DisposableEffect(Unit) {
        Log.i("debug","onStart")
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
            Text(text = "我的收藏")
        }
    }
}