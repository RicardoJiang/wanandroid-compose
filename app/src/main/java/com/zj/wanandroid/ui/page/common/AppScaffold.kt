package com.zj.wanandroid.ui.page.common

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.pager.ExperimentalPagerApi
import com.zj.wanandroid.data.bean.WebData
import com.zj.wanandroid.ui.page.login.LoginPage
import com.zj.wanandroid.ui.page.main.category.CategoryPage
import com.zj.wanandroid.ui.page.main.collect.CollectPage
import com.zj.wanandroid.ui.page.main.home.HomePage
import com.zj.wanandroid.ui.page.main.profile.ProfilePage
import com.zj.wanandroid.ui.page.webview.WebViewPage
import com.zj.wanandroid.ui.widgets.BottomNavBarView
import com.zj.wanandroid.utils.RouteUtils
import com.zj.wanandroid.utils.fromJson

@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@ExperimentalPagerApi
@Composable
fun AppScaffold() {
    val navCtrl = rememberNavController()
    val navBackStackEntry by navCtrl.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        modifier = Modifier
            .statusBarsPadding()
            .navigationBarsPadding(),
        bottomBar = {
            when (currentDestination?.route) {
                RouteName.HOME -> BottomNavBarView(navCtrl = navCtrl)
                RouteName.CATEGORY -> BottomNavBarView(navCtrl = navCtrl)
                RouteName.COLLECTION -> BottomNavBarView(navCtrl = navCtrl)
                RouteName.PROFILE -> BottomNavBarView(navCtrl = navCtrl)
            }
        },
        content = {
            var homeIndex = remember { 0 }
            var categoryIndex = remember { 0 }

            NavHost(
                modifier = Modifier.background(MaterialTheme.colors.background),
                navController = navCtrl,
                startDestination = RouteName.HOME
            ) {
                //首页
                composable(route = RouteName.HOME) {
                    HomePage(navCtrl, scaffoldState)
                }

                //分类
                composable(route = RouteName.CATEGORY) {
                    CategoryPage(navCtrl)
                }

                //收藏
                composable(route = RouteName.COLLECTION) {
                    CollectPage(navCtrl, scaffoldState)
                }

                //我的
                composable(route = RouteName.PROFILE) {
                    ProfilePage(navCtrl, scaffoldState)
                }

                //WebView
                composable(route = RouteName.WEB_VIEW + "/{webData}",
                    arguments = listOf(navArgument("webData") { type = NavType.StringType })
                ) {
                    val args = it.arguments?.getString("webData")?.fromJson<WebData>()
                    if (args != null) {
                        WebViewPage(webData = args, navCtrl = navCtrl)
                    }
                }

                //登录
                composable(route = RouteName.LOGIN) {
                    LoginPage(navCtrl, scaffoldState)
                }
            }
        }
    )
}