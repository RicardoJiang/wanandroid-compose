package com.zj.wanandroid.ui.page.main.home.square

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.zj.wanandroid.common.paging.simplePager
import com.zj.wanandroid.data.bean.Article
import com.zj.wanandroid.data.http.HttpService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
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
}

data class SquareViewState(
    val isRefreshing: Boolean = false,
    val pagingData: PagingArticle
)

sealed class SquareViewAction {
    object Refresh : SquareViewAction()
}

typealias PagingArticle = Flow<PagingData<Article>>
