package com.zj.wanandroid.ui.page.main.category

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.zj.wanandroid.ui.widgets.TabTitle

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor() : ViewModel() {

    val titles by mutableStateOf(
        mutableListOf(
            TabTitle(201, "体系"),
            TabTitle(202, "导航"),
            TabTitle(203, "公众号"),
        )
    )

    override fun onCleared() {
        super.onCleared()
        println("CategoryViewModel ==> onClear")
    }

}