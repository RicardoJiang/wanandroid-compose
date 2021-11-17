package com.zj.wanandroid.data.http

sealed class PageResult {
    object Loading : PageResult()
    data class Success(val isEmpty: Boolean) : PageResult()
    data class Error(val exception: Exception) : PageResult()
}
