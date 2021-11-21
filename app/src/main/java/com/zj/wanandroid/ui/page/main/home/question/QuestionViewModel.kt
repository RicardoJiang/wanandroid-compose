package com.zj.wanandroid.ui.page.main.home.question

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.zj.wanandroid.common.paging.simplePager
import com.zj.wanandroid.data.http.HttpService
import com.zj.wanandroid.ui.page.main.home.square.PagingArticle
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class QuestionViewModel @Inject constructor(
    private var service: HttpService,
) : ViewModel() {
    private val pager by lazy {
        simplePager {
            service.getWendaData(it)
        }
    }
    var viewStates by mutableStateOf(QuestionViewState(pagingData = pager))
        private set
}

data class QuestionViewState(
    val isRefreshing: Boolean = false,
    val pagingData: PagingArticle
)