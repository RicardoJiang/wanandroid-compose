package com.zj.wanandroid.ui.page.common

import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding

@Composable
fun AppScaffold() {
    val navCtrl = rememberNavController()
    val navBackStackEntry by navCtrl.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val scaffoldState = rememberScaffoldState()

//    Scaffold(
//        modifier = Modifier
//            .statusBarsPadding()
//            .navigationBarsPadding(),
//        bottomBar = {
//            when (currentDestination?.route) {
//                RouteName.HOME -> BottomNavBarView(navCtrl = navCtrl)
//                RouteName.CATEGORY -> BottomNavBarView(navCtrl = navCtrl)
//                RouteName.PROFILE -> BottomNavBarView(navCtrl = navCtrl)
//            }
//        },
//        content = {
//            var homeIndex = remember { 0 }
//            var categoryIndex = remember { 0 }
//
//            NavHost(
//                modifier = Modifier.background(HamTheme.colors.background),
//                navController = navCtrl,
//                startDestination = RouteName.HOME
//            ) {
//                //首页
//                composable(route = RouteName.HOME) {
//                    HomePage(navCtrl, scaffoldState, homeIndex) { homeIndex = it }
//                }
//
//                //分类
//                composable(route = RouteName.CATEGORY) {
//                    CategoryPage(navCtrl, categoryIndex) { categoryIndex = it }
//                }
//
//                //收藏
//                composable(route = RouteName.COLLECTION) {
//                    CollectionPage(navCtrl, scaffoldState)
//                }
//
//                //我的
//                composable(route = RouteName.PROFILE) {
//                    ProfilePage(navCtrl)
//                }
//            }
//        }
//        )
}