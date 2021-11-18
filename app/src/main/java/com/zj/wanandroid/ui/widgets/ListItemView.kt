package com.zj.wanandroid.ui.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.google.accompanist.placeholder.material.placeholder
import com.zj.wanandroid.data.bean.Article
import com.zj.wanandroid.data.bean.CollectBean
import com.zj.wanandroid.data.bean.WebData
import com.zj.wanandroid.theme.*
import com.zj.wanandroid.utils.RegexUtils

@Composable
fun ListTitle(
    modifier: Modifier = Modifier,
    title: String,
    subTitle: String = "",
    isLoading: Boolean = false,
    onSubtitleClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .placeholder(false)
            .fillMaxWidth()
            .height(ListTitleHeight)
            .background(color = AppTheme.colors.background)
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .width(5.dp)
                .height(16.dp)
                .align(Alignment.CenterVertically)
                .background(color = AppTheme.colors.textPrimary)
        )
        MediumTitle(
            title = title,
            modifier = Modifier.align(Alignment.CenterVertically),
            isLoading = isLoading
        )
        Spacer(modifier = Modifier.weight(1f))
        TextContent(
            text = subTitle,
            modifier = Modifier
                .padding(end = 10.dp)
                .clickable {
                    onSubtitleClick.invoke()
                },
            isLoading = isLoading
        )
    }

}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CollectListItemView(
    collect: CollectBean,
    isLoading: Boolean = false,
    onClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 10.dp, vertical = 5.dp)
            .fillMaxWidth()
            .wrapContentWidth()
            .background(color = AppTheme.colors.card)
            .clickable(enabled = !isLoading) { onClick.invoke() }
    ) {
        ConstraintLayout(
            modifier = Modifier.padding(20.dp)
        ) {
            val (name, publishIcon, publishTime, title, delete) = createRefs()
            MediumTitle(
                title = if (collect.author.isNotEmpty()) collect.author else "无名",
                modifier = Modifier
                    .defaultMinSize(minWidth = 100.dp)
                    .constrainAs(name) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    },
                isLoading = isLoading
            )
            MiniTitle(
                text = RegexUtils().timestamp(collect.niceDate) ?: "2021",
                modifier = Modifier
                    .constrainAs(publishTime) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                        bottom.linkTo(publishIcon.bottom)
                    }
                    .defaultMinSize(minWidth = 40.dp),
                isLoading = isLoading
            )
            TimerIcon(
                modifier = Modifier
                    .constrainAs(publishIcon) {
                        top.linkTo(parent.top, margin = 2.5.dp)
                        end.linkTo(publishTime.start)
                    },
                isLoading = isLoading
            )
            TextContent(
                text = collect.title,
                maxLines = 3,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(top = 10.dp, bottom = 20.dp)
                    .constrainAs(title) {
                        top.linkTo(name.bottom)
                        end.linkTo(parent.end)
                    },
                isLoading = isLoading
            )
            if (!isLoading) {
                DeleteIcon(
                    modifier = Modifier
                        .padding(top = 5.dp)
                        .constrainAs(delete) {
                            top.linkTo(title.bottom)
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom)
                        },
                    onClick = onDeleteClick
                )
            }
        }
    }
}

@Composable
fun SimpleListItemView(
    data: Article,
    isLoading: Boolean = false,
    onClick: () -> Unit = {},
    onCollectClick: (articleId: Int) -> Unit = {},
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 10.dp, vertical = 5.dp)
            .background(color = AppTheme.colors.card)
            .fillMaxWidth()
            .wrapContentWidth()
            .clickable(enabled = !isLoading) {
                onClick.invoke()
            }
    ) {
        ConstraintLayout(
            modifier = Modifier.padding(20.dp),
        ) {
            val (name, publishIcon, publishTime, title, favourite) = createRefs()
            MediumTitle(
                title = data.author ?: data.shareUser ?: "作者",
                modifier = Modifier
                    .defaultMinSize(minWidth = 100.dp)
                    .constrainAs(name) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    },
                isLoading = isLoading
            )
            MiniTitle(
                text = RegexUtils().timestamp(data.niceDate) ?: "",
                modifier = Modifier.constrainAs(publishTime) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    bottom.linkTo(publishIcon.bottom)
                },
                isLoading = isLoading
            )
            TimerIcon(
                modifier = Modifier
                    .constrainAs(publishIcon) {
                        top.linkTo(parent.top)
                        end.linkTo(publishTime.start)
                    },
                isLoading = isLoading
            )
            TextContent(
                text = data.title ?: "",
                maxLines = 3,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(top = 10.dp, bottom = 20.dp)
                    .constrainAs(title) {
                        top.linkTo(name.bottom)
                        end.linkTo(parent.end)
                    },
                isLoading = isLoading
            )
            FavouriteIcon(
                modifier = Modifier.constrainAs(favourite) {
                    top.linkTo(title.bottom)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                },
                isFavourite = data.collect,
                onClick = {
                    onCollectClick.invoke(data.id)
                },
                isLoading = isLoading
            )
        }
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MultiStateItemView(
    modifier: Modifier = Modifier,
    data: Article,
    isTop: Boolean = false,
    onSelected: (data: WebData) -> Unit = {},
    onCollectClick: (articleId: Int) -> Unit = {},
    onUserClick: (userId: Int) -> Unit = {},
    isLoading: Boolean = false,
) {
    Card(
        modifier = modifier
            .padding(vertical = 5.dp, horizontal = 10.dp)
            .background(color = AppTheme.colors.card)
            .fillMaxWidth()
            .clickable(enabled = !isLoading) {
                onSelected.invoke(WebData(data.title!!, data.link!!))
            },
        shape = AppShapes.medium,
        backgroundColor = AppTheme.colors.listItem,
    ) {
        ConstraintLayout(
            modifier = Modifier.padding(20.dp),
        ) {
            val (circleText, name, publishIcon, publishTime, title, chip1, chip2, tag, favourite) = createRefs()
            Text(
                text = getFirstCharFromName(data),
                modifier = Modifier
                    .width(20.dp)
                    .height(20.dp)
                    .clip(RoundedCornerShape(20.dp / 2))
                    .background(color = AppTheme.colors.themeUi)
                    .constrainAs(circleText) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }
                    .placeholder(
                        visible = isLoading,
                        color = AppTheme.colors.placeholder
                    ),
                textAlign = TextAlign.Center,
                fontSize = H6,
                color = white1,
            )
            val titleModifier =
                if (isLoading) Modifier.width(80.dp) else Modifier.wrapContentWidth()
            MediumTitle(
                title = getAuthorName(data),
                modifier = titleModifier
                    .constrainAs(name) {
                        top.linkTo(parent.top)
                        start.linkTo(circleText.end)
                    }
                    .padding(start = 5.dp)
                    .clickable {
                        onUserClick.invoke(data.userId)
                    }
                    .pointerInteropFilter { false },
                isLoading = isLoading
            )
            val dateModifier =
                if (isLoading) Modifier.width(80.dp) else Modifier.wrapContentWidth()
            MiniTitle(
                text = RegexUtils().timestamp(data.niceDate!!) ?: "1970-1-1",
                modifier = dateModifier
                    .constrainAs(publishTime) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                    },
                isLoading = isLoading
            )
            TimerIcon(
                modifier = Modifier
                    .padding(end = if (isLoading) 5.dp else 0.dp)
                    .constrainAs(publishIcon) {
                        top.linkTo(parent.top)
                        end.linkTo(publishTime.start)
                        bottom.linkTo(publishTime.bottom)
                    },
                isLoading = isLoading
            )
            TextContent(
                text = data.title ?: "",
                maxLines = 3,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(top = 10.dp, bottom = 20.dp)
                    .constrainAs(title) {
                        top.linkTo(circleText.bottom)
                        end.linkTo(parent.end)
                    },
                isLoading = isLoading,
            )
            LabelTextButton(
                text = data.superChapterName ?: "热门",
                modifier = Modifier
                    .constrainAs(chip1) {
                        top.linkTo(title.bottom)
                        start.linkTo(parent.start)
                        bottom.linkTo(parent.bottom)
                    },
                isLoading = isLoading
            )
            LabelTextButton(
                text = data.chapterName ?: "android",
                modifier = Modifier
                    .constrainAs(chip2) {
                        top.linkTo(title.bottom)
                        start.linkTo(chip1.end, margin = 5.dp)
                        bottom.linkTo(parent.bottom)
                    },
                isLoading = isLoading,
            )
            FavouriteIcon(
                modifier = Modifier.constrainAs(favourite) {
                    top.linkTo(title.bottom)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                },
                isFavourite = data.collect,
                onClick = {
                    onCollectClick.invoke(data.id)
                },
                isLoading = isLoading
            )
            Row(
                modifier = Modifier
                    .defaultMinSize(minHeight = 20.dp)
                    .constrainAs(tag) {
                        top.linkTo(parent.top)
                        start.linkTo(name.end, margin = 5.dp)
                    }
            ) {
                if (isTop) {
                    HotIcon()
                }
                if (data.fresh) {
                    TagView(
                        tagText = "最新",
                        modifier = Modifier
                            .padding(start = 5.dp)
                            .align(Alignment.Bottom)
                    )
                }
            }

        }
    }
}

@Composable
fun ArrowRightListItem(
    iconRes: Any,
    title: String,
    msgCount: Int? = null,
    valueText: String = "",
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(horizontal = 10.dp)
            .clickable {
                onClick.invoke()
            }
    ) {

        when (iconRes) {
            is Painter -> {
                Icon(
                    painter = iconRes,
                    contentDescription = null,
                    tint = AppTheme.colors.icon,
                    modifier = Modifier
                        .size(30.dp)
                        .align(Alignment.CenterVertically)
                        .padding(end = 10.dp)
                )
            }
            is ImageVector -> {
                Icon(
                    imageVector = iconRes,
                    contentDescription = null,
                    tint = AppTheme.colors.icon,
                    modifier = Modifier
                        .size(30.dp)
                        .align(Alignment.CenterVertically)
                        .padding(end = 10.dp)
                )
            }
        }
        Row(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .weight(1f)
        ) {
            TextContent(text = title, modifier = Modifier.align(Alignment.CenterVertically))
            if (msgCount != null) {
                Text(
                    text = "（$msgCount）",
                    fontSize = H7,
                    color = AppTheme.colors.error,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
        }
        if (valueText.isNotEmpty()) {
            TextContent(
                text = valueText,
                modifier = Modifier
                    .padding(end = 5.dp)
                    .align(Alignment.CenterVertically)
            )
        }
        Icon(
            Icons.Default.KeyboardArrowRight,
            null,
            tint = AppTheme.colors.textSecondary,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
    Divider()
}


fun getAuthorName(data: Article?): String {
    return if (data?.shareUser.isNullOrEmpty()) {
        data?.author ?: ""
    } else {
        data?.shareUser ?: ""
    }
}

fun getFirstCharFromName(data: Article?): String {
    val author = getAuthorName(data)
    return if (author.isNotEmpty()) author.trim().substring(0, 1) else "?"
}


