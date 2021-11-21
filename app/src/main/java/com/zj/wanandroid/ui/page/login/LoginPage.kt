package com.zj.wanandroid.ui.page.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.ScaffoldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.zj.wanandroid.theme.AppTheme
import com.zj.wanandroid.theme.ToolBarHeight
import com.zj.wanandroid.ui.widgets.*
import com.zj.wanandroid.utils.RouteUtils.back

@ExperimentalComposeUiApi
@Composable
fun LoginPage(
    navCtrl: NavHostController,
    scaffoldState: ScaffoldState,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val viewStates = viewModel.viewStates
    val keyboardController = LocalSoftwareKeyboardController.current
    val coroutineState = rememberCoroutineScope()

    if (viewStates.isLogged) {
        navCtrl.popBackStack()
    }

    //SnackBar弹窗显示信息
    if (viewStates.errorMessage != null) {
        popupSnackBar(coroutineState, scaffoldState, label = SNACK_ERROR, viewStates.errorMessage!!)
        viewModel.dispatch(LoginViewAction.ClearErrorMessage)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.themeUi)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        keyboardController?.hide()
                    }
                )
            },
    ) {
        item {
            Box(
                modifier = Modifier
                    .padding(bottom = 80.dp)
                    .fillMaxWidth()
                    .height(ToolBarHeight)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,
                    tint = AppTheme.colors.mainColor,
                    modifier = Modifier
                        .padding(start = 20.dp)
                        .align(Alignment.CenterStart)
                        .clickable { navCtrl.back() }
                )
                TextContent(
                    text = "用户注册",
                    modifier = Modifier
                        .padding(end = 20.dp)
                        .align(Alignment.CenterEnd)
                        .clickable {

                        },
                    color = AppTheme.colors.mainColor
                )
            }
        }
        item {
            Box(Modifier.fillMaxWidth()) {
                LargeTitle(
                    title = "WanAndroid",
                    modifier = Modifier
                        .padding(bottom = 50.dp)
                        .align(Alignment.Center),
                    color = AppTheme.colors.mainColor
                )
            }
        }
        item {
            LoginEditView(
                text = viewStates.account,
                labelText = "账号",
                hintText = "请输入用户名",
                onValueChanged = { viewModel.dispatch(LoginViewAction.UpdateAccount(it)) },
                onDeleteClick = { viewModel.dispatch(LoginViewAction.ClearAccount) }
            )
        }
        item {
            LoginEditView(
                text = viewStates.password,
                labelText = "密码",
                hintText = "请输入密码",
                onValueChanged = { viewModel.dispatch(LoginViewAction.UpdatePassword(it)) },
                onDeleteClick = { viewModel.dispatch(LoginViewAction.ClearPassword) },
                modifier = Modifier.padding(top = 20.dp, bottom = 20.dp),
                isPassword = true
            )
        }
        item {
            AppButton(
                text = "登录",
                modifier = Modifier.padding(horizontal = 20.dp)
            ) {
                keyboardController?.hide()
                viewModel.dispatch(LoginViewAction.ClearErrorMessage)
                viewModel.dispatch(LoginViewAction.Login)
            }
        }
    }
}