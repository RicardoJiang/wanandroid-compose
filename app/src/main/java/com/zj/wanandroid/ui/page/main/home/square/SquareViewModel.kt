package com.zj.wanandroid.ui.page.main.home.square

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zj.wanandroid.data.http.HttpService
import com.zj.wanandroid.common.paging.simplePager
import dagger.hilt.android.lifecycle.HiltViewModel
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
        simplePager { service.getSquareData(it) }
    }

    init {
        fetchData()
    }

    private fun fetchData() {
        Log.i("tiaoshi", "fetchData")
        viewModelScope.launch {
            flow {
                emit(service.getSquareData(1))
            }.onEach {
                Log.i("tiaoshi", "here:" + it)
            }.collect()
        }
    }

}

