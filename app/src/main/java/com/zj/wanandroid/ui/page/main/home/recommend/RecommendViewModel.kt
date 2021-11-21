package com.zj.wanandroid.ui.page.main.home.recommend

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.zj.wanandroid.common.paging.simplePager
import com.zj.wanandroid.data.bean.Article
import com.zj.wanandroid.data.http.HttpService
import com.zj.wanandroid.ui.page.main.home.square.PagingArticle
import com.zj.wanandroid.ui.widgets.BannerData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecommendViewModel @Inject constructor(
    private var service: HttpService,
) : ViewModel() {
    private val pager by lazy {
        simplePager {
            service.getIndexList(it)
        }.cachedIn(viewModelScope)
    }
    var viewStates by mutableStateOf(RecommendViewState(pagingData = pager))
        private set

    init {
        dispatch(RecommendViewAction.FetchData)
    }

    fun dispatch(action: RecommendViewAction) {
        when (action) {
            is RecommendViewAction.FetchData -> fetchData()
            is RecommendViewAction.Refresh -> refresh()
        }
    }

    private fun fetchData() {
        val imageListFlow = flow {
            kotlinx.coroutines.delay(2000)
            emit(service.getBanners())
        }.map { bean ->
            val result = mutableListOf<BannerData>()
            bean.data?.forEach {
                result.add(BannerData(it.title ?: "", it.imagePath ?: "", it.url ?: ""))
            }
            result
        }
        val topListFlow = flow {
            emit(service.getTopArticles())
        }.map {
            it.data ?: emptyList()
        }
        viewModelScope.launch {
            imageListFlow.zip(topListFlow) { banners, tops ->
                viewStates =
                    viewStates.copy(imageList = banners, topArticles = tops, isRefreshing = false)
            }.onStart {
                viewStates = viewStates.copy(isRefreshing = true)
            }.catch {
                viewStates = viewStates.copy(isRefreshing = false)
            }.collect()
        }
    }

    private fun refresh() {
        fetchData()
    }

}

data class RecommendViewState(
    val pagingData: PagingArticle,
    val isRefreshing: Boolean = false,
    val imageList: List<BannerData> = emptyList(),
    val topArticles: List<Article> = emptyList(),
    val listState: LazyListState = LazyListState()
)

sealed class RecommendViewAction {
    object FetchData : RecommendViewAction()
    object Refresh : RecommendViewAction()
}