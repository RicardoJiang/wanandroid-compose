package com.zj.wanandroid.data.http

import com.zj.wanandroid.data.bean.*
import retrofit2.http.*

/**
 * 网络请求接口
 * 注意：接口前无需加斜杠
 * create by Mqz at 4/19
 */
interface HttpService {

    companion object {
        const val url = "https://www.wanandroid.com"
    }

    //首页
    @GET("/article/list/{page}/json")
    suspend fun getIndexList(@Path("page") page: Int): BasicBean<ListWrapper<Article>>

    //广场
    @GET("/user_article/list/{page}/json")
    suspend fun getSquareData(@Path("page") page: Int): BasicBean<ListWrapper<Article>>

    //问答
    @GET("/wenda/list/{page}/json")
    suspend fun getWendaData(@Path("page") page: Int): BasicBean<ListWrapper<Article>>

    //置顶文章
    @GET("/article/top/json")
    suspend fun getTopArticles(): BasicBean<MutableList<Article>>

    //搜索热词
    @GET("/hotkey/json")
    suspend fun getHotkeys(): BasicBean<MutableList<Hotkey>>

    //banner
    @GET("/banner/json")
    suspend fun getBanners(): BasicBean<MutableList<BannerBean>>

    //体系
    @GET("/tree/json")
    suspend fun getStructureList(): BasicBean<MutableList<ParentBean>>

    //导航
    @GET("/navi/json")
    suspend fun getNavigationList(): BasicBean<MutableList<NaviWrapper>>

    //公众号列表
    @GET("/wxarticle/chapters/json")
    suspend fun getPublicInformation(): BasicBean<MutableList<ParentBean>>

    //某作者的公众号文章列表
    @GET("/wxarticle/list/{id}/{page}/json")
    suspend fun getPublicArticles(
        @Path("id") publicId: Int,
        @Path("page") page: Int
    ): BasicBean<ListWrapper<Article>>

    //某公众号作者的文章下搜索
    @GET("/wxarticle/list/{id}/{page}/json")
    suspend fun getPublicArticlesWithKey(
        @Path("id") publicId: Int,
        @Path("page") page: Int,
        @Query("k") key: String
    ): BasicBean<ListWrapper<Article>>

    //项目分类
    @GET("/project/tree/json")
    suspend fun getProjectCategory(): BasicBean<MutableList<ParentBean>>

    //某个项目分类下的列表
    @GET("/project/list/{page}/json")
    suspend fun getProjects(
        @Path("page") page: Int,
        @Query("cid") cid: Int
    ): BasicBean<ListWrapper<Article>>

    //某个体系下的文章列表
    @GET("/article/list/{page}/json")
    suspend fun getStructureArticles(
        @Path("page") page: Int,
        @Query("cid") cid: Int
    ): BasicBean<ListWrapper<Article>>

    //在某个体系下搜索某作者的文章
    @GET("/article/list/{page}/json")
    suspend fun getArticlesByAuthor(
        @Path("page") page: Int,
        @Query("author") author: String
    ): BasicBean<ListWrapper<Article>>

    //热门项目
    @GET("/article/listproject/{page}/json")
    suspend fun getHotProjects(@Path("page") page: Int): BasicBean<ListWrapper<Article>>

    //首页查询文章
    @FormUrlEncoded
    @POST("/article/query/{page}/json")
    suspend fun queryArticle(
        @Path("page") page: Int,
        @Field("k") key: String
    ): BasicBean<ListWrapper<Article>>

    //注册
    @FormUrlEncoded
    @POST("/user/register")
    suspend fun register(
        @Field("username") userName: String,
        @Field("password") password: String,
        @Field("repassword") repassword: String,
    ): BasicBean<UserInfo>

    //登录
    @FormUrlEncoded
    @POST("/user/login")
    suspend fun login(
        @Field("username") userName: String,
        @Field("password") password: String,
    ): BasicBean<UserInfo>

    //退出登录
    @GET("/user/logout/json")
    suspend fun logout(): BasicBean<Any>

    //排行榜
    @GET("/coin/rank/{page}/json")
    suspend fun getPointsRankings(@Path("page") page: Int): BasicBean<ListWrapper<PointsBean>>

    //积分记录
    @GET("/lg/coin/list/{page}/json")
    suspend fun getPointsRecords(@Path("page") page: Int): BasicBean<ListWrapper<PointsBean>>

    //我的积分
    @GET("/lg/coin/userinfo/json")
    suspend fun getMyPointsRanking(): BasicBean<PointsBean>

    //消息数量
    @GET("/message/lg/count_unread/json")
    suspend fun getMessageCount(): BasicBean<Int>

    //未读消息列表
    @GET("/message/lg/unread_list/{page}/json")
    suspend fun getUnreadMessage(@Path("page") page: Int): BasicBean<ListWrapper<Any>>

    //已读消息列表
    @GET("/message/lg/readed_list/{page}/json")
    suspend fun getReadedMessage(@Path("page") page: Int): BasicBean<ListWrapper<Any>>

    //收藏列表
    @GET("/lg/collect/list/{page}/json")
    suspend fun getCollectionList(@Path("page") page: Int): BasicBean<ListWrapper<CollectBean>>

    //收藏的网站
    @GET("/lg/collect/usertools/json")
    suspend fun getCollectUrls(): BasicBean<MutableList<ParentBean>>

    //收藏站内文章
    @POST("/lg/collect/{id}/json")
    suspend fun collectInnerArticle(@Path("id") id: Int): BasicBean<Any>

    //取消收藏站内文章（在首页等列表里取消）
    @POST("/lg/uncollect_originId/{id}/json")
    suspend fun uncollectInnerArticle(@Path("id") id: Int): BasicBean<Any>

    //取消收藏站内文章（在收藏列表里取消）
    @POST("/lg/uncollect/{id}/json")
    suspend fun uncollectArticleById(
        @Path("id") id: Int,
        @Query("originId") originId: Int
    ): BasicBean<Any>

    //添加收藏网站
    @FormUrlEncoded
    @POST("/lg/collect/addtool/json")
    suspend fun addNewWebsiteCollect(
        @Field("name") title: String,
        @Field("link") linkUrl: String
    ): BasicBean<ParentBean>

    //添加站外文章
    @FormUrlEncoded
    @POST("/lg/collect/add/json")
    suspend fun addNewArticleCollect(
        @Field("title") title: String,
        @Field("link") linkUrl: String,
        @Field("author") author: String,
    ): BasicBean<CollectBean>

    //删除收藏网站
    @FormUrlEncoded
    @POST("/lg/collect/deletetool/json")
    suspend fun deleteWebsite(@Field("id") id: Int): BasicBean<Any>

    //编辑收藏的网站
    @FormUrlEncoded
    @POST("/lg/collect/updatetool/json")
    suspend fun editCollectWebsite(
        @Field("id") id: Int,
        @Field("name") title: String,
        @Field("link") linkUrl: String,
    ): BasicBean<Any>

    //自己分享的文章
    @GET("/user/lg/private_articles/{page}/json")
    suspend fun getMyShareArticles(
        @Path("page") page: Int,
        @Query("page_size") size: Int = 40,
    ): BasicBean<SharerBean<Article>>

    //根据文章id删除自己分享的文章
    @POST("/lg/user_article/delete/{id}/json")
    suspend fun deleteMyShareArticle(@Path("id") id: Int): BasicBean<Any>

    //分享文章
    @FormUrlEncoded
    @POST("/lg/user_article/add/json")
    suspend fun addMyShareArticle(
        @Field("title") title: String,
        @Field("link") linkUrl: String,
        @Field("shareUser") shareUser: String,
    ): BasicBean<Any>

    //其他作者分享的文章
    @GET("/user/{userId}/share_articles/{page}/json")
    suspend fun getAuthorShareArticles(
        @Path("userId") userId: Int,
        @Path("page") page: Int,
        @Query("page_size") size: Int = 40,
    ): BasicBean<SharerBean<Article>>

    @GET("/user/lg/userinfo/json")
    suspend fun getBasicUserInfo(): BasicBean<BasicUserInfo>

    // 福利
    @GET("https://gank.io/api/v2/data/category/{category}/type/{type}/page/{page}/count/{pageSize}")
    suspend fun getWelfareList(
        @Path(value = "category", encoded = false) category: String = "Girl",
        @Path(value = "type", encoded = false) type: String = "Girl",
        @Path("page") page: Int,
        @Path("pageSize") pageCount: Int = 40
    ): WelfareBean


}
