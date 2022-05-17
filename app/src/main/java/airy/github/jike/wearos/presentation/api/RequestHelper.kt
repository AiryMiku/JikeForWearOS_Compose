package airy.github.jike.wearos.presentation.api

import airy.github.jike.wearos.BuildConfig
import airy.github.jike.wearos.presentation.App
import airy.github.jike.wearos.presentation.util.persistentcookiejar.PersistentCookieJar
import airy.github.jike.wearos.presentation.util.persistentcookiejar.cache.SetCookieCache
import airy.github.jike.wearos.presentation.util.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

object RequestHelper {
    private const val CONNECT_TIME_OUT = 30L //连接超时时间
    private const val READ_TIME_OUT = 30L
    private val cookieJar =
        PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(App.getAppContext()))

    val networkScope = CoroutineScope(Dispatchers.IO)

    @JvmField
    val headersInterceptor: Interceptor = Interceptor { chain ->
        val original = chain.request()

        val request = original.newBuilder().apply {
//            header("User-Agent", Config.USER_AGENT)
        }.build()

        chain.proceed(request)
    }

    @JvmField
    val errorInterceptor = Interceptor { chain ->
        val response = chain.proceed(chain.request())
        if (response.code != 200) {
//            App.getAppContext().showToastLong("Network error: ${response.message}")
        }
        response
    }

    @JvmField
    val client: OkHttpClient = OkHttpClient.Builder().apply {
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                this.level = HttpLoggingInterceptor.Level.BODY
            }
            addInterceptor(loggingInterceptor)
            addNetworkInterceptor(loggingInterceptor)
        }
        connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
        writeTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
        readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
        cookieJar(cookieJar)
//        addInterceptor(headersInterceptor)
        addInterceptor(errorInterceptor)
        retryOnConnectionFailure(true)
    }.build()

//    internal fun newRequest(useMobile: Boolean = false): Request.Builder {
//        val ua = if (useMobile) {
//            Config.USER_AGENT_ANDROID
//        } else {
//            Config.USER_AGENT
//        }
//        return Request.Builder().apply {
//            header("User-Agent", ua)
//        }
//    }


    fun clearCookieAll() = cookieJar.clear()

    fun clearCookieSession() = cookieJar.clearSession()

//    fun isCookieExpired(): Boolean {
//        val url = Config.BASE_URL.toHttpUrlOrNull()
//        url?.let {
//            val cookies = cookieJar.loadForRequest(it)
//            for (c in cookies) {
//                if (c.name == "A2") {
//                    return System.currentTimeMillis() > c.expiresAt
//                }
//            }
//        }
//        return true
//    }
}