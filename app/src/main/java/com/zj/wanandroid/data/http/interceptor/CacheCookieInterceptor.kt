package com.zj.wanandroid.data.http.interceptor

import com.zj.wanandroid.data.store.DataStoreUtils
import okhttp3.Interceptor
import okhttp3.Response

class CacheCookieInterceptor: Interceptor {

    private val loginUrl = "user/login"
    private val registerUrl = "user/register"
    private val SET_COOKIE_KEY = "set-cookie"

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        val requestUrl = request.url().toString()
        val domain = request.url().host()
        if (aboutUser(requestUrl)) {
            val cookies = response.headers(SET_COOKIE_KEY)
            if (cookies.isNotEmpty()) {
                //cookie可能有多个，都保存下来
                DataStoreUtils.putSyncData(domain, encodeCookie(cookies))
            }
        }
        return response
    }

    private fun aboutUser(url: String): Boolean = url.contains(loginUrl) or url.contains(registerUrl)
}

/**
 * 整理cookie
 */
private fun encodeCookie(cookies: List<String>): String {
    val sb = StringBuilder()
    val set = HashSet<String>()
    cookies
        .map { cookie ->
            cookie.split(";".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        }
        .forEach { it ->
            it.filterNot { set.contains(it) }.forEach { set.add(it) }
        }

    val ite = set.iterator()
    while (ite.hasNext()) {
        val cookie = ite.next()
        sb.append(cookie).append(";")
    }

    val last = sb.lastIndexOf(";")
    if (sb.length - 1 == last) {
        sb.deleteCharAt(last)
    }

    return sb.toString()
}
