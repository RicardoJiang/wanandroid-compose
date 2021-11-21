package com.zj.wanandroid.ui.page.main.category.navi

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zj.wanandroid.data.bean.NaviWrapper
import com.zj.wanandroid.data.http.HttpService
import com.zj.wanandroid.data.http.PageState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NaviViewModel @Inject constructor(
    private var service: HttpService
) : ViewModel() {
    var viewStates by mutableStateOf(NaviViewState())
        private set

    init {
        dispatch(NaviViewAction.FetchData)
    }

    fun dispatch(action: NaviViewAction) {
        when (action) {
            is NaviViewAction.FetchData -> fetchData()
        }
    }

    private fun fetchData() {
        viewModelScope.launch {
            flow {
                emit(service.getNavigationList())
            }.map {
                it.data ?: emptyList()
            }.onStart {
                viewStates = viewStates.copy(pageState = PageState.Loading)
            }.onEach {
                viewStates = viewStates.copy(
                    dataList = it,
                    pageState = PageState.Success(it.isEmpty())
                )
            }.catch {
                viewStates = viewStates.copy(pageState = PageState.Error(it))
            }.collect()
        }
    }
}

data class NaviViewState(
    val dataList: List<NaviWrapper> = emptyList(),
    val pageState: PageState = PageState.Loading,
    val listState: LazyListState = LazyListState()
) {
    val size = dataList.size
}

sealed class NaviViewAction() {
    object FetchData : NaviViewAction()
}