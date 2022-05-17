package airy.github.jike.wearos.presentation.repository

import airy.github.jike.wearos.presentation.api.RetrofitService
import airy.github.jike.wearos.presentation.api.graphql.getBasicProfileParam
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody

class JikeRepository {

    suspend fun getBasicProfile() = RetrofitService
        .graphApi
        .query(getBasicProfileParam().toRequestBody())

}