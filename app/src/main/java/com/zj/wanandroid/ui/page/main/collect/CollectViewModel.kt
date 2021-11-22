package com.zj.wanandroid.ui.page.main.collect

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.zj.wanandroid.common.paging.simplePager
import com.zj.wanandroid.data.bean.CollectBean
import com.zj.wanandroid.data.bean.ParentBean
import com.zj.wanandroid.data.http.HttpService
import com.zj.wanandroid.utils.AppUserUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectViewModel @Inject constructor(
    private var service: HttpService
) : ViewModel() {
    private val pager by lazy {
        simplePager {
            service.getCollectionList(it)
        }.cachedIn(viewModelScope)
    }
    var viewStates by mutableStateOf(CollectViewState())
        private set

    fun dispatch(action: CollectViewAction) {
        when (action) {
            is CollectViewAction.OnStart -> onStart()
            is CollectViewAction.Refresh -> refresh()
        }
    }

    private fun onStart() {
        viewStates = viewStates.copy(isLogged = AppUserUtil.isLogged)
        if (viewStates.isLogged && viewStates.isInit.not()) {
            viewStates = viewStates.copy(isInit = true)
            initData()
        }
    }

    private fun initData() {
        viewStates = viewStates.copy(pagingData = pager)
        fetchUrls()
    }

    private fun refresh() {
        fetchUrls()
    }

    private fun fetchUrls() {
        viewModelScope.launch {
            flow {
                emit(service.getCollectUrls())
            }.onStart {
                viewStates = viewStates.copy(isRefreshing = true)
            }.map {
                it.data ?: emptyList()
            }.onEach {
                viewStates = viewStates.copy(urlList = it, isRefreshing = false)
            }.catch {
                viewStates = viewStates.copy(isRefreshing = false)
            }.collect()
        }
    }

}

data class CollectViewState(
    val isInit: Boolean = false,
    val isRefreshing: Boolean = false,
    val urlList: List<ParentBean> = emptyList(),
    val pagingData: PagingCollect? = null,
    val isLogged: Boolean = AppUserUtil.isLogged
)

sealed class CollectViewAction {
    object OnStart : CollectViewAction()
    object Refresh : CollectViewAction()
}

typealias PagingCollect = Flow<PagingData<CollectBean>>