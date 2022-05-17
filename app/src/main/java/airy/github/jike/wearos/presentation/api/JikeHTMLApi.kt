package airy.github.jike.wearos.presentation.api

import okhttp3.ResponseBody
import retrofit2.http.GET

interface JikeHTMLApi {

    @GET("/")
    suspend fun index(): ResponseBody
}