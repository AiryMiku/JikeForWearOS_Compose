package airy.github.jike.wearos.presentation.api

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.POST

interface JikeGraphqlApi {

 @POST("graphql")
 suspend fun query(@Body body: RequestBody): ResponseBody
}
