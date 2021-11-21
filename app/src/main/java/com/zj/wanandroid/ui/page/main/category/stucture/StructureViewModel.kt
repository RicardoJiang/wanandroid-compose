package com.zj.wanandroid.ui.page.main.category.stucture

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
class StructureViewModel @Inject constructor(
    private var service: HttpService
) : ViewModel() {
    var viewStates by mutableStateOf(StructureViewState())
        private set

    init {
        dispatch(StructureViewAction.FetchData)
    }

    fun dispatch(action: StructureViewAction) {
        when (action) {
            is StructureViewAction.FetchData -> fetchData()
        }
    }

    private fun fetchData() {
        viewModelScope.launch {
            flow {
                emit(service.getStructureList())
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

data class StructureViewState(
    val dataList: List<ParentBean> = emptyList(),
    val pageState: PageState = PageState.Loading,
    val listState: LazyListState = LazyListState()
) {
    val size = dataList.size
}

sealed class StructureViewAction() {
    object FetchData : StructureViewAction()
}