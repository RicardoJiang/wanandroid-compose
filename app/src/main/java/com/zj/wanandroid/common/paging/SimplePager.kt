package com.zj.wanandroid.common.paging

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.zj.wanandroid.MyApp
import com.zj.wanandroid.data.bean.BasicBean
import com.zj.wanandroid.data.bean.ListWrapper
import com.zj.wanandroid.data.http.HttpResult
import com.zj.wanandroid.utils.NetCheckUtil
import com.zj.wanandroid.utils.showToast
import kotlinx.coroutines.flow.Flow

fun <T : Any> ViewModel.simplePager(
    config: AppPagingConfig = AppPagingConfig(),
    callAction: suspend (page: Int) -> BasicBean<ListWrapper<T>>
): Flow<PagingData<T>> {
    return pager(config, 0) {
        val page = it.key ?: 0
        val response = try {
            HttpResult.Success(callAction.invoke(page))
        } catch (e: Exception) {
            if (NetCheckUtil.checkNet(MyApp.CONTEXT).not()) {
                showToast("没有网络,请重试")
            } else {
                showToast("请求失败，请重试")
            }
            HttpResult.Error(e)
        }
        when (response) {
            is HttpResult.Success -> {
                val data = response.result.data
                val hasNotNext = (data!!.datas.size < it.loadSize) && (data.over)
                PagingSource.LoadResult.Page(
                    data = response.result.data!!.datas,
                    prevKey = if (page - 1 > 0) page - 1 else null,
                    nextKey = if (hasNotNext) null else page + 1
                )
            }
            is HttpResult.Error -> {
                PagingSource.LoadResult.Error(response.exception)
            }
        }
    }
}

fun <K : Any, V : Any> ViewModel.pager(
    config: AppPagingConfig = AppPagingConfig(),
    initialKey: K? = null,
    loadData: suspend (PagingSource.LoadParams<K>) -> PagingSource.LoadResult<K, V>
): Flow<PagingData<V>> {
    val baseConfig = PagingConfig(
        config.pageSize,
        initialLoadSize = config.initialLoadSize,
        prefetchDistance = config.prefetchDistance,
        maxSize = config.maxSize,
        enablePlaceholders = config.enablePlaceholders
    )
    return Pager(
        config = baseConfig,
        initialKey = initialKey
    ) {
        object : PagingSource<K, V>() {
            override suspend fun load(params: LoadParams<K>): LoadResult<K, V> {
                return loadData.invoke(params)
            }

            override fun getRefreshKey(state: PagingState<K, V>): K? {
                return initialKey
            }

        }
    }.flow.cachedIn(viewModelScope)
}