package com.zj.wanandroid.ui.widgets

import androidx.compose.material.ScaffoldState
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarData
import androidx.compose.runtime.Composable
import com.zj.wanandroid.theme.AppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

const val SNACK_INFO = ""
const val SNACK_WARN = " "
const val SNACK_ERROR = "  "
const val SNACK_SUCCESS = "OK"

@Composable
fun AppSnackBar(data: SnackbarData) {
    Snackbar(
        snackbarData = data,
        backgroundColor = when (data.actionLabel) {
            SNACK_INFO -> AppTheme.colors.themeUi
            SNACK_WARN -> AppTheme.colors.warn
            SNACK_ERROR -> AppTheme.colors.error
            SNACK_SUCCESS -> AppTheme.colors.success
            else -> AppTheme.colors.themeUi
        },
        actionColor = AppTheme.colors.textPrimary,
        contentColor = AppTheme.colors.textPrimary,
    )
}

fun popupSnackBar(
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
    label: String,
    message: String,
    onDismissCallback: () -> Unit = {}
) {
    scope.launch {
        scaffoldState.snackbarHostState.showSnackbar(actionLabel = label, message = message)
        onDismissCallback.invoke()
    }

}