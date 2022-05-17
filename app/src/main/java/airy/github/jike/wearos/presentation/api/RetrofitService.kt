package airy.github.jike.wearos.presentation.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {

    const val htmlBaseUrl = "https://web.okjike.com/"
    const val graphqlBaseUrl = "https://web-api.okjike.com/api/"

    val graphApi = Retrofit.Builder()
        .baseUrl(graphqlBaseUrl)
        .client(RequestHelper.client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(JikeGraphqlApi::class.java)

    val htmlApi = Retrofit.Builder()
        .baseUrl(htmlBaseUrl)
        .client(RequestHelper.client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(JikeHTMLApi::class.java)
}