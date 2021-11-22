package com.zj.wanandroid.ui.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.Card
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.zj.wanandroid.theme.AppTheme


@Composable
fun SampleAlertDialog(
    title: String,
    content: String,
    cancelText: String = "取消",
    confirmText: String = "继续",
    onConfirmClick: () -> Unit,
    //onCancelClick: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        title = {
            MediumTitle(title = title)
        },
        text = {
            TextContent(text = content)
        },
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onConfirmClick.invoke()
                onDismiss.invoke()
            }) {
                TextContent(text = confirmText, color = AppTheme.colors.textPrimary)
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss.invoke() }) {
                TextContent(text = cancelText)
            }
        },
        modifier = Modifier
            .padding(horizontal = 30.dp)
            .fillMaxWidth()
            .wrapContentHeight()
    )
}

@Composable
fun SelectAlertDialog(
    title: String,
    content: String,
    primaryButtonText: String,
    secondButtonText: String,
    onPrimaryButtonClick: () -> Unit,
    onSecondButtonClick: () -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card {
            Column(Modifier.padding(20.dp)) {
                MediumTitle(title = title, modifier = Modifier.padding(bottom = 20.dp))
                TextContent(text = content, modifier = Modifier.padding(bottom = 20.dp))
                PrimaryButton(text = primaryButtonText, Modifier.padding(bottom = 10.dp)) {
                    onPrimaryButtonClick.invoke()
                    onDismiss.invoke()
                }
                SecondlyButton(text = secondButtonText) {
                    onSecondButtonClick.invoke()
                    onDismiss.invoke()
                }
            }
        }
    }
}

@Composable
fun InfoDialog(
    title: String = "关于我",
    vararg content: String,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(dismissOnClickOutside = true),
        title = {
            MediumTitle(title = title)
        },
        text = {
            Column(
                Modifier.defaultMinSize(minWidth = 300.dp)
            ) {
                content.forEach {
                    TextContent(
                        text = it,
                        modifier = Modifier.padding(bottom = 10.dp),
                        canCopy = true
                    )
                }
            }
        },
        confirmButton = {
            TextContent(
                text = "关闭",
                modifier = Modifier
                    .padding(end = 18.dp, bottom = 18.dp)
                    .clickable { onDismiss.invoke() }
            )
        }
    )
}

