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
            is ProfileViewAction.ShowLogoutDialog -> showLogout()
            is ProfileViewAction.DismissLogoutDialog -> dismissLogout()
            is ProfileViewAction.Logout -> logout()
        }
    }

    private fun logout() {
        AppUserUtil.onLogOut()
        viewStates = viewStates.copy(isLogged = false, showLogout = false, userInfo = null)
    }

    private fun dismissLogout() {
        viewStates = viewStates.copy(showLogout = false)
    }

    private fun showLogout() {
        viewStates = viewStates.copy(showLogout = true)
    }

    private fun onStart() {
        viewStates =
            viewStates.copy(isLogged = AppUserUtil.isLogged, userInfo = AppUserUtil.userInfo)
    }
}

data class ProfileViewState(
    val isLogged: Boolean = AppUserUtil.isLogged,
    val userInfo: UserInfo? = AppUserUtil.userInfo,
    val showLogout: Boolean = false
)

sealed class ProfileViewAction {
    object OnStart : ProfileViewAction()
    object ShowLogoutDialog : ProfileViewAction()
    object DismissLogoutDialog : ProfileViewAction()
    object Logout : ProfileViewAction()
}