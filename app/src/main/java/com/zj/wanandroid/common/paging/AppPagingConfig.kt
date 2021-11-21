package com.zj.wanandroid.common.paging

import androidx.paging.PagingConfig

data class AppPagingConfig(
    val pageSize: Int = 20,
    val initialLoadSize: Int = 20,
    val prefetchDistance:Int = 1,
    val maxSize:Int = PagingConfig.MAX_SIZE_UNBOUNDED,
    val enablePlaceholders:Boolean = false
)