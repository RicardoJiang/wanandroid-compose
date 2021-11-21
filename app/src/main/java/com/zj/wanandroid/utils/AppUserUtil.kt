package com.zj.wanandroid.utils

import com.zj.wanandroid.data.bean.UserInfo
import com.zj.wanandroid.data.store.DataStoreUtils

object AppUserUtil {
    private const val LOGGED_FLAG = "logged_flag"
    private const val USER_INFO = "user_info"
    var isLogged: Boolean
        get() = DataStoreUtils.readBooleanData(LOGGED_FLAG, false)
        set(value) = DataStoreUtils.saveSyncBooleanData(LOGGED_FLAG, value = value)

    var userInfo: UserInfo?
        get() = DataStoreUtils.readStringData(USER_INFO).fromJson()
        set(value) = DataStoreUtils.saveSyncStringData(LOGGED_FLAG, value = value?.toJson() ?: "")

    fun onLogin(userInfo: UserInfo) {
        isLogged = true
        this.userInfo = userInfo
    }

    fun onLogOut() {
        isLogged = false
        this.userInfo = null
    }
}