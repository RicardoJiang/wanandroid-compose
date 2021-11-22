package com.zj.wanandroid.ui.page.main.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.zj.wanandroid.data.bean.UserInfo
import com.zj.wanandroid.utils.AppUserUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor() : ViewModel() {
    var viewStates by mutableStateOf(ProfileViewState())
        private set

    fun dispatch(action: ProfileViewAction) {
        when (action) {
            is ProfileViewAction.OnStart -> onStart()
        }
    }

    private fun onStart() {
        viewStates =
            viewStates.copy(isLogged = AppUserUtil.isLogged, userInfo = AppUserUtil.userInfo)
    }
}

data class ProfileViewState(
    val isLogged: Boolean = AppUserUtil.isLogged,
    val userInfo: UserInfo? = AppUserUtil.userInfo
)

sealed class ProfileViewAction {
    object OnStart : ProfileViewAction()
}