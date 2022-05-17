package airy.github.jike.wearos.presentation.api.graphql

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

data class QueryParam(
    val operationName: String,
    val variables: JSONObject,
    val query: String
    ) {

    fun toJSONObject(): JSONObject {
        return JSONObject().apply {
            put("operationName", operationName)
            put("variables", variables)
            put("query", query)
        }
    }

    fun toRequestBody() =
        toJSONObject().toString().toRequestBody("application/json".toMediaTypeOrNull())
}

fun getSmsCodeParam(areaCode: String = "+86", mobilePhoneNumber: String) = QueryParam(
    operationName = "GetSmsCode",
    variables = JSONObject().apply {
        put("areaCode", areaCode)
        put("mobilePhoneNumber", mobilePhoneNumber)
    },
    query = "mutation GetSmsCode(\$mobilePhoneNumber: String!, \$areaCode: String!) {  getSmsCode(action: PHONE_MIX_LOGIN, mobilePhoneNumber: \$mobilePhoneNumber, areaCode: \$areaCode) {    action    __typename  }}"
)

fun getLoginParam(areaCode: String = "+86", mobilePhoneNumber: String, smsCode: String) = QueryParam(
    operationName = "MixLoginWithPhone",
    variables = JSONObject().apply {
        put("areaCode", areaCode)
        put("mobilePhoneNumber", mobilePhoneNumber)
        put("smsCode", smsCode)
    },
    query = "mutation MixLoginWithPhone(\$smsCode: String!, \$mobilePhoneNumber: String!, \$areaCode: String!) {  mixLoginWithPhone(smsCode: \$smsCode, mobilePhoneNumber: \$mobilePhoneNumber, areaCode: \$areaCode) {    isRegister    user {      distinctId: id      ...TinyUserFragment      __typename    }    __typename  }}fragment TinyUserFragment on UserInfo {  avatarImage {    thumbnailUrl    smallPicUrl    picUrl    __typename  }  isSponsor  username  screenName  briefIntro  __typename}"
)

fun getBasicProfileParam() = QueryParam(
    operationName = "BasicProfile",
    variables = JSONObject(),
    query = "query BasicProfile {  profile {    distinctId: id    ...UserCardFragment    __typename  }}fragment UserCardFragment on UserInfo {  ...TinyUserFragment  statsCount {    followedCount    followingCount    __typename  }  backgroundImage {    picUrl    __typename  }  following  __typename}fragment TinyUserFragment on UserInfo {  avatarImage {    thumbnailUrl    smallPicUrl    picUrl    __typename  }  isSponsor  username  screenName  briefIntro  __typename}"
)