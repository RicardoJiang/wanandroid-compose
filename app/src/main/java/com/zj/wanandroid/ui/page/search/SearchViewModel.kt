package com.zj.wanandroid.ui.page.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.zj.wanandroid.common.paging.simplePager
import com.zj.wanandroid.data.bean.Hotkey
import com.zj.wanandroid.data.http.HttpService
import com.zj.wanandroid.ui.page.main.home.square.PagingArticle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private var service: HttpService
) : ViewModel() {
    var viewStates by mutableStateOf(SearchViewState())
        private set

    init {
        dispatch(SearchViewAction.GetHotKeys)
    }

    fun dispatch(action: SearchViewAction) {
        when (action) {
            is SearchViewAction.Search -> search()
            is SearchViewAction.GetHotKeys -> getHotKeys()
            is SearchViewAction.SetSearchKey -> setSearchKey(action.key)
        }
    }

    private fun setSearchKey(key: String) {
        viewStates = viewStates.copy(searchContent = key)
    }

    private fun getHotKeys() {
        viewModelScope.launch {
            flow {
                emit(service.getHotkeys())
            }.map {
                it.data ?: emptyList()
            }.onEach {
                viewStates = viewStates.copy(hotKeys = it)
            }.catch {

            }.collect()
        }
    }

    private fun search() {
        val key = viewStates.searchContent
        val paging = simplePager {
            service.queryArticle(page = it, key = key)
        }.cachedIn(viewModelScope)
        viewStates = viewStates.copy(searchResult = paging)
    }
}


data class SearchViewState(
    val searchResult: PagingArticle? = null,
    val searchContent: String = "",
    val hotKeys: List<Hotkey> = emptyList()
)

sealed class SearchViewAction {
    object GetHotKeys : SearchViewAction()
    object Search : SearchViewAction()
    data class SetSearchKey(val key: String) : SearchViewAction()
}
