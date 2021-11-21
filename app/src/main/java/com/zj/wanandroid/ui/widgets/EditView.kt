package com.zj.wanandroid.ui.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalTextInputService
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextInputSession
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zj.wanandroid.theme.AppTheme

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginEditView(
    text: String,
    labelText: String,
    hintText: String?,
    onValueChanged: (String) -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier,
    isPassword: Boolean = false
) {
    BasicLabelEditView(
        modifier = modifier,
        text = text,
        labelText = labelText,
        labelTextColor = AppTheme.colors.mainColor,
        hintText = hintText ?: "",
        onValueChanged = { onValueChanged.invoke(it) },
        deleteIconColor = AppTheme.colors.mainColor,
        onDeleteClick = { onDeleteClick.invoke() },
        inputCursorColor = AppTheme.colors.mainColor,
        inputTextColor = AppTheme.colors.mainColor,
        borderColor = AppTheme.colors.mainColor,
        keyboardType = if (isPassword) KeyboardType.Password else KeyboardType.Text
    )
}

@Composable
fun LabelEditView(
    text: String,
    labelText: String,
    hintText: String?,
    onValueChanged: (String) -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    BasicLabelEditView(
        modifier = modifier,
        text = text,
        labelText = labelText,
        hintText = hintText ?: "",
        onValueChanged = { onValueChanged.invoke(it) },
        onDeleteClick = { onDeleteClick.invoke() }
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun BasicLabelEditView(
    modifier: Modifier = Modifier,
    text: String,
    labelText: String,
    labelTextColor: Color = AppTheme.colors.textPrimary,
    hintText: String = "",
    deleteIconColor: Color = AppTheme.colors.themeUi,
    onValueChanged: (String) -> Unit,
    onDeleteClick: () -> Unit,
    inputCursorColor: Color = AppTheme.colors.themeUi,
    inputTextColor: Color = AppTheme.colors.textPrimary,
    borderColor: Color = AppTheme.colors.textSecondary,
    keyboardType: KeyboardType = KeyboardType.Text,
    isHideKeyboard: Boolean = true,
) {
    val keyboardService = LocalTextInputService.current

    TextField(
        value = text,
        onValueChange = { onValueChanged(it) },
        textStyle = TextStyle(lineHeight = 24.sp, fontSize = 16.sp),
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 20.dp)
            .pointerInteropFilter { false },
        label = {
            MediumTitle(
                labelText,
                color = labelTextColor,
                modifier = Modifier.padding(bottom = 10.dp)
            )
        },
        placeholder = {
            TextContent(hintText)
        },
        trailingIcon = {
            if (text.isNotEmpty()) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = null,
                    tint = deleteIconColor,
                    modifier = Modifier.clickable { onDeleteClick() }
                )
            }
        },
        singleLine = true,
        maxLines = 1,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = borderColor,
            unfocusedBorderColor = borderColor,
            textColor = inputTextColor,
            placeholderColor = AppTheme.colors.textSecondary,
            cursorColor = inputCursorColor
        ),
        keyboardActions = if (!isHideKeyboard) KeyboardActions.Default
        else KeyboardActions { keyboardService?.hideSoftwareKeyboard() },
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = ImeAction.Next
        )
    )
}
