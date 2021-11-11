package com.zj.wanandroid.ui.page.main.home

import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun HomePage(
    navCtrl: NavHostController,
    scaffoldState: ScaffoldState
) {
    Text(text = "首页")
}