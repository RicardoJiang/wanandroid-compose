package com.zj.wanandroid.ui.page.main.home.square

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.zj.wanandroid.common.paging.simplePager
import com.zj.wanandroid.data.bean.Article
import com.zj.wanandroid.data.http.HttpService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SquareViewModel @Inject constructor(
    private var service: HttpService,
) : ViewModel() {
    private val pager by lazy {
        simplePager {
            if (it >= 3) {
                throw NullPointerException("")
            }
            delay(2000)
            service.getSquareData(it)
        }
    }
    var viewStates by mutableStateOf(SquareViewState(pagingData = pager))
        private set

    fun dispatch(action: SquareViewAction) {
    }


}

data class SquareViewState(
    val isRefreshing: Boolean = false,
    val pagingData: PagingArticle
)

sealed class SquareViewAction {
    object Refresh : SquareViewAction()
}

typealias PagingArticle = Flow<PagingData<Article>>
