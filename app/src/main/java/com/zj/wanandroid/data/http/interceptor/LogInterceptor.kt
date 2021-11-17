package com.zj.wanandroid.data.http.interceptor

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import okhttp3.*
import java.io.IOException
import java.net.URLDecoder
import javax.inject.Inject

/**
 * Created by Superman on 2021/1/29.
 */
class LogInterceptor @Inject constructor() : Interceptor {

    private val logTag = "http ## "

    @RequiresApi(Build.VERSION_CODES.N)
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response? {
        val request = chain.request()
        return kotlin.runCatching { chain.proceed(request) }
            .onSuccess {
                logRequest(request, chain.connection())
                if (it.isSuccessful) {
                    logResponse(it)
                } else {
                    logThat(ColorLevel.WARN(it.message() ?: "未知异常"))
                }
            }
            .onFailure {
                logThat(ColorLevel.ERROR(it.message ?: "未知异常"))
            }
            .getOrThrow()
    }

    private fun logResponse(response: Response) {
        val strb = StringBuffer()
        strb.appendLine("\r\n")
        strb.appendLine("<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-")

        var headerText = ""
        response.headers().toMultimap().forEach { header->
            headerText += "请求 Header:{${header.key}=${header.value}}\n"
        }
        strb.appendln(headerText)
        kotlin.runCatching {
            //peek类似于clone数据流，监视，窥探，不能直接用原来的body的string流数据作为日志，会消费掉io，所有这里是peek，监测
            val peekBody: ResponseBody = response.peekBody(1024 * 1024)
            strb.appendln(peekBody.string())
        }.getOrNull()

        strb.appendLine(
            "<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<" +
                    "-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-"
        )
        logThat(ColorLevel.INFO(strb.toString()))
    }


    /**
     * 打印日志
     */
    private fun logThat(tempLevel: ColorLevel) {
        when (tempLevel) {
            is ColorLevel.VERBOSE -> Log.v(logTag, tempLevel.logText)
            is ColorLevel.DEBUG -> Log.d(logTag, tempLevel.logText)
            is ColorLevel.INFO -> Log.i(logTag, tempLevel.logText)
            is ColorLevel.WARN -> Log.w(logTag, tempLevel.logText)
            is ColorLevel.ERROR -> Log.e(logTag, tempLevel.logText)
        }
    }

    private fun logRequest(request: Request, connection: Connection?) {
        val strb = StringBuilder()
        strb.appendLine("\r\n")
        strb.appendLine(
            "->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->"
        )
        logHeaders(strb, request, connection)
        strb.appendLine("RequestBody:${request.body().toString()}")
        logThat(ColorLevel.VERBOSE(strb.toString()))
    }

    private fun logHeaders(strb: StringBuilder, request: Request, connection: Connection?) {
        logBasic(strb, request, connection)
        var headerStr = ""
        request.headers().toMultimap().forEach { header->
            headerStr += "请求 Header:{${header.key}=${header.value}}\n"
        }
        strb.appendln(headerStr)
    }

    private fun logBasic(strb: StringBuilder, request: Request, connection: Connection?) {
        strb.appendLine(
            "请求 method：${request.method()} url:${decodeUrlStr(request.url().toString())} tag:" +
                    "${request.tag()} protocol:${connection?.protocol() ?: Protocol.HTTP_1_1}"
        )
    }

    /**
     * 对于url编码的string解码
     */
    private fun decodeUrlStr(url: String): String? {
        return kotlin.runCatching {
            URLDecoder.decode(url, "utf-8")
        }.onFailure { it.printStackTrace() }.getOrNull()
    }

    /**
     * 打印日志范围
     */
    enum class LogLevel {
        NONE,//不打印
        BASIC,//只打印行首，请求/响应
        HEADERS, //打印请求和响应的所有header
        BODY //打印所有
    }

    /**
     * Log颜色等级，应用于android Logcat分为 v、d、i、w、e
     */
    sealed class ColorLevel {
        data class VERBOSE(val logText: String): ColorLevel()
        data class DEBUG(val logText: String): ColorLevel()
        data class INFO(val logText: String): ColorLevel()
        data class WARN(val logText: String): ColorLevel()
        data class ERROR(val logText: String): ColorLevel()
    }


}

