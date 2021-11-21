package com.zj.wanandroid.ui.page.main.category.pubaccount

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zj.wanandroid.data.bean.ParentBean
import com.zj.wanandroid.data.http.HttpService
import com.zj.wanandroid.data.http.PageState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PubAccountViewModel @Inject constructor(
    private var service: HttpService
) : ViewModel() {
    var viewStates by mutableStateOf(PubAccountViewState())
        private set

    init {
        dispatch(PubAccountViewAction.FetchData)
    }

    fun dispatch(action: PubAccountViewAction) {
        when (action) {
            is PubAccountViewAction.FetchData -> fetchData()
        }
    }

    private fun fetchData() {
        viewModelScope.launch {
            flow {
                emit(service.getPublicInformation())
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

data class PubAccountViewState(
    val dataList: List<ParentBean> = emptyList(),
    val pageState: PageState = PageState.Loading,
    val listState: LazyListState = LazyListState()
) {
    val size = dataList.size
}

sealed class PubAccountViewAction() {
    object FetchData : PubAccountViewAction()
}