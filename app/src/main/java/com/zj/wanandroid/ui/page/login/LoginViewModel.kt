package com.zj.wanandroid.ui.page.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zj.wanandroid.data.http.HttpResult
import com.zj.wanandroid.data.http.HttpService
import com.zj.wanandroid.utils.AppUserUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private var service: HttpService
) : ViewModel() {
    var viewStates by mutableStateOf(LoginViewState())
        private set
    private val _viewEvents = Channel<LoginViewEvent>(Channel.BUFFERED)
    val viewEvents = _viewEvents.receiveAsFlow()

    fun dispatch(action: LoginViewAction) {
        when (action) {
            is LoginViewAction.Login -> login()
            is LoginViewAction.ClearAccount -> clearAccount()
            is LoginViewAction.ClearPassword -> clearPassword()
            is LoginViewAction.UpdateAccount -> updateAccount(action.account)
            is LoginViewAction.UpdatePassword -> updatePassword(action.password)
        }
    }

    private fun login() {
        viewModelScope.launch {
            flow {
                emit(service.login(viewStates.account.trim(), viewStates.password.trim()))
            }.map {
                if (it.errorCode == 0) {
                    if (it.data != null) {
                        HttpResult.Success(it.data!!)
                    } else {
                        throw Exception("the result of remote's request is null")
                    }
                } else {
                    throw Exception(it.errorMsg)
                }
            }.onEach {
                AppUserUtil.onLogin(it.result)
                _viewEvents.send(LoginViewEvent.PopBack)
            }.catch {
                _viewEvents.send(LoginViewEvent.ErrorMessage(it.message ?: ""))
            }.collect()
        }
    }

    private fun clearAccount() {
        viewStates = viewStates.copy(account = "")
    }

    private fun clearPassword() {
        viewStates = viewStates.copy(password = "")
    }

    private fun updateAccount(account: String) {
        viewStates = viewStates.copy(account = account)
    }

    private fun updatePassword(password: String) {
        viewStates = viewStates.copy(password = password)
    }

}

data class LoginViewState(
    val account: String = "",
    val password: String = "",
    val isLogged: Boolean = false
)

/**
 * 一次性事件
 */
sealed class LoginViewEvent {
    object PopBack : LoginViewEvent()
    data class ErrorMessage(val message: String) : LoginViewEvent()
}

sealed class LoginViewAction {
    object Login : LoginViewAction()
    object ClearAccount : LoginViewAction()
    object ClearPassword : LoginViewAction()
    data class UpdateAccount(val account: String) : LoginViewAction()
    data class UpdatePassword(val password: String) : LoginViewAction()
}