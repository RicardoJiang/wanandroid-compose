package com.zj.wanandroid.ui.page.main.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.zj.wanandroid.ui.widgets.TabTitle
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    var viewStates by mutableStateOf(HomeViewState())
        private set

    init {
        viewStates = viewStates.copy(
            titles = listOf(
                TabTitle(101, "广场"),
                TabTitle(102, "推荐"),
                TabTitle(103, "问答")
            )
        )
    }
}

data class HomeViewState(val titles: List<TabTitle> = emptyList())