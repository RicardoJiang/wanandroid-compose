package com.zj.wanandroid.theme

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.TweenSpec
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.systemuicontroller.rememberSystemUiController

//夜色主题
private val DarkColorPalette = AppColors(
    themeUi = black1,
    background = black2,
    listItem = black3,
    divider = black4,
    textPrimary = white4,
    textSecondary = grey1,
    mainColor = white,
    card = white1,
    icon = white4,
    info = info,
    warn = warn,
    success = green3,
    error = red2,
    primaryBtnBg = black1,
    secondBtnBg = white1,
    hot = red,
    placeholder = grey1,
)

//白天主题
private val LightColorPalette = AppColors(
    themeUi = themeColor,
    background = white2,
    listItem = white,
    divider = white3,
    textPrimary = black3,
    textSecondary = grey1,
    mainColor = white,
    card = white1,
    icon = white4,
    info = info,
    warn = warn,
    success = green3,
    error = red2,
    primaryBtnBg = themeColor,
    secondBtnBg = white3,
    hot = red,
    placeholder = white3,
)
var LocalAppColors = compositionLocalOf {
    LightColorPalette
}

@Stable
object AppTheme {
    val colors: AppColors
        @Composable
        get() = LocalAppColors.current

    enum class Theme {
        Light, Dark
    }
}

@Stable
class AppColors(
    themeUi: Color,
    background: Color,
    listItem: Color,
    divider: Color,
    textPrimary: Color,
    textSecondary: Color,
    mainColor: Color,
    card: Color,
    icon: Color,
    info: Color,
    warn: Color,
    success: Color,
    error: Color,
    primaryBtnBg: Color,
    secondBtnBg: Color,
    hot: Color,
    placeholder: Color,
) {
    var themeUi: Color by mutableStateOf(themeUi)
        internal set
    var background: Color by mutableStateOf(background)
        private set
    var listItem: Color by mutableStateOf(listItem)
        private set
    var divider: Color by mutableStateOf(divider)
        private set
    var textPrimary: Color by mutableStateOf(textPrimary)
        internal set
    var textSecondary: Color by mutableStateOf(textSecondary)
        private set
    var mainColor: Color by mutableStateOf(mainColor)
        internal set
    var card: Color by mutableStateOf(card)
        private set
    var icon: Color by mutableStateOf(icon)
        private set
    var info: Color by mutableStateOf(info)
        private set
    var warn: Color by mutableStateOf(warn)
        private set
    var success: Color by mutableStateOf(success)
        private set
    var error: Color by mutableStateOf(error)
        private set
    var primaryBtnBg: Color by mutableStateOf(primaryBtnBg)
        internal set
    var secondBtnBg: Color by mutableStateOf(secondBtnBg)
        private set
    var hot: Color by mutableStateOf(hot)
        private set
    var placeholder: Color by mutableStateOf(placeholder)
        private set

}


@Composable
fun AppTheme(
    theme: AppTheme.Theme = AppTheme.Theme.Light,
    content: @Composable () -> Unit
) {

    val targetColors = when (theme) {
        AppTheme.Theme.Light -> {
            LightColorPalette.themeUi = themeColor
            LightColorPalette.primaryBtnBg = themeColor
            LightColorPalette
        }
        AppTheme.Theme.Dark -> DarkColorPalette
    }

    val themeUi = animateColorAsState(targetColors.themeUi, TweenSpec(600))
    val background = animateColorAsState(targetColors.background, TweenSpec(600))
    val listItem = animateColorAsState(targetColors.listItem, TweenSpec(600))
    val divider = animateColorAsState(targetColors.divider, TweenSpec(600))
    val textPrimary = animateColorAsState(targetColors.textPrimary, TweenSpec(600))
    val textSecondary = animateColorAsState(targetColors.textSecondary, TweenSpec(600))
    val mainColor = animateColorAsState(targetColors.mainColor, TweenSpec(600))
    val card = animateColorAsState(targetColors.card, TweenSpec(600))
    val icon = animateColorAsState(targetColors.icon, TweenSpec(600))
    val info = animateColorAsState(targetColors.info, TweenSpec(600))
    val warn = animateColorAsState(targetColors.warn, TweenSpec(600))
    val success = animateColorAsState(targetColors.success, TweenSpec(600))
    val error = animateColorAsState(targetColors.error, TweenSpec(600))
    val primaryBtnBg = animateColorAsState(targetColors.primaryBtnBg, TweenSpec(600))
    val secondBtnBg = animateColorAsState(targetColors.secondBtnBg, TweenSpec(600))
    val hot = animateColorAsState(targetColors.hot, TweenSpec(600))
    val placeholder = animateColorAsState(targetColors.placeholder, TweenSpec(600))
    val appColors = AppColors(
        themeUi = themeUi.value,
        background = background.value,
        listItem = listItem.value,
        divider = divider.value,
        textPrimary = textPrimary.value,
        textSecondary = textSecondary.value,
        mainColor = mainColor.value,
        card = card.value,
        icon = icon.value,
        primaryBtnBg = primaryBtnBg.value,
        secondBtnBg = secondBtnBg.value,
        info = info.value,
        warn = warn.value,
        success = success.value,
        error = error.value,
        hot = hot.value,
        placeholder = placeholder.value
    )

    val systemUiCtrl = rememberSystemUiController()
    systemUiCtrl.setStatusBarColor(appColors.themeUi)
    systemUiCtrl.setNavigationBarColor(appColors.themeUi)
    systemUiCtrl.setSystemBarsColor(appColors.themeUi)

    ProvideWindowInsets {
        CompositionLocalProvider(LocalAppColors provides appColors, content = content)
    }

}