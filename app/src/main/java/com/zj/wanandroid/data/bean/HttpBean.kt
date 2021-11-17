package com.zj.wanandroid.data.bean

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.zj.wanandroid.theme.AppTheme
import kotlinx.android.parcel.Parcelize

data class HomeThemeBean(
    var theme: AppTheme.Theme
)

data class Hotkey(
    var link: String?,
    var name: String?,
    var order: Int,
    var visible: Int
)

data class BasicBean<T>(
    var data: T?,
    var errorCode: Int,
    var errorMsg: String
)

data class ListWrapper<T>(
    var curPage: Int,
    var offset: Int,
    var over: Boolean,
    var pageCount: Int,
    var size: Int,
    var total: Int,
    var datas: ArrayList<T>
)

data class BannerBean(
    var desc: String?,
    var id: Int,
    var imagePath: String?,
    var isVisible: Int,
    var order: Int,
    var title: String?,
    var type: Int,
    var url: String?
)

data class SharerBean<T>(
    val coinInfo: PointsBean,
    val shareArticles: ListWrapper<T>
)

data class BasicUserInfo(
    val coinInfo: PointsBean,
    val userInfo: UserInfo
)

@Parcelize
data class ParentBean(
    var children: MutableList<ParentBean>?,
    var courseId: Int = -1,
    var id: Int = -1,
    var name: String? = "分类",
    var order: Int = -1,
    var parentChapterId: Int = -1,
    var userControlSetTop: Boolean = false,
    var visible: Int = -1,
    var icon: String? = null,
    var link: String? = null
) : Parcelable

data class NaviWrapper(
    var articles: MutableList<Article>?,
    var cid: Int,
    var name: String?
)

@Parcelize
data class Article(
    var apkLink: String? = "",
    var audit: Int = -1,
    var author: String? = "作者",
    var canEdit: Boolean = false,
    var chapterId: Int = -1,
    var chapterName: String? = "章节",
    var collect: Boolean = false,
    var courseId: Int = -1,
    var desc: String? = "描述",
    var descMd: String? = "描述Md",
    var envelopePic: String? = "图片1",
    var fresh: Boolean = false,
    var host: String? = "https://www.wanandroid.com",
    var id: Int = -1,
    var link: String? = "https://www.wanandroid.com",
    var niceDate: String? = "1970-0-0",
    var niceShareDate: String? = "1970-0-0",
    var origin: String? = "",
    var prefix: String? = "",
    var projectLink: String? = "https://www.wanandroid.com",
    var publishTime: Long = 0L,
    var realSuperChapterId: Int = -1,
    var selfVisible: Int = -1,
    var shareDate: Long = 0L,
    var shareUser: String? = "分享者",
    var superChapterId: Int = -1,
    var superChapterName: String? = "超级分类",
    var tags: MutableList<ArticleTag>? = null,
    var title: String? = "标题",
    var type: Int = -1,
    var userId: Int = -1,
    var visible: Int = -1,
    var zan: Int = -1
) : Parcelable

@Parcelize
data class ArticleTag(
    var name: String,
    var url: String
) : Parcelable


@Parcelize
data class WebData(
    var title: String?,
    var url: String
) : Parcelable


@Parcelize
data class UserInfo(
    var id: Int,
    var admin: Boolean,
    var chapterTops: MutableList<Int>,
    var coinCount: Int,
    var collectIds: MutableList<Int>,
    var email: String,
    var icon: String,
    var nickname: String,
    var password: String,
    var token: String,
    var type: Int,
    var username: String,
) : Parcelable

data class PointsBean(
    var id: Int?,
    var coinCount: String,
    var level: Int?,
    var nickname: String,
    var rank: String?,
    var userId: Int,
    var username: String,
    var date: String?,
    var desc: String?,
    var reason: String?,
    var type: Int?,
)

data class CollectBean(
    var author: String = "",
    var chapterId: Int = 0,
    var chapterName: String = "",
    var courseId: Int = 0,
    var desc: String = "",
    var envelopePic: String = "",
    var id: Int = 0,
    var link: String = "",
    var niceDate: String = "",
    var origin: String = "",
    var originId: Int = 0,
    var publishTime: Long = 0,
    var title: String = "",
    var userId: Int = 0,
    var visible: Int = 0,
    var zan: Int = 0
)


/********************* Begin: Welfare Data ***********************/
abstract class GankBasedBean<T : Any?> {
    val page: Int = 1

    @SerializedName("page_count")
    val pageSize: Int = 20
    val status: Int = 100

    @SerializedName("total_counts")
    val totalSize: Int = 96
    abstract var data: T?
}

data class WelfareBean(
    override var data: List<WelfareData>?
) : GankBasedBean<List<WelfareData>>()

@Parcelize
data class WelfareData(
    @SerializedName("_id") var id: String?,
    var author: String?,
    var category: String?,
    var createAt: String?,
    var desc: String?,
    var images: MutableList<String>,
    var likeCounts: Int,
    var publishedAt: String?,
    var stars: Int,
    var title: String?,
    var type: String?,
    var url: String?,
    var views: Int,
) : Parcelable
/********************* Begin: Welfare Data ***********************/





