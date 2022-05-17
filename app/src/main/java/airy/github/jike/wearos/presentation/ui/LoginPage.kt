package airy.github.jike.wearos.presentation.ui

import airy.github.jike.wearos.presentation.App
import airy.github.jike.wearos.presentation.api.RequestHelper
import airy.github.jike.wearos.presentation.api.RetrofitService
import airy.github.jike.wearos.presentation.api.graphql.getLoginParam
import airy.github.jike.wearos.presentation.api.graphql.getSmsCodeParam
import airy.github.jike.wearos.presentation.theme.JikeTheme
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.*
import kotlinx.coroutines.launch

@Composable
fun LoginPage() {
    val listState = rememberScalingLazyListState()

    Scaffold(
        positionIndicator = {
            PositionIndicator(scalingLazyListState = listState)
        }
    ) {
        val mobileHint = "mobile"
        var mobileValue by remember { mutableStateOf(TextFieldValue(text = mobileHint)) }
        val isMobileHint = { mobileValue.text == mobileHint }

        val smsCodeHint = "sms code"
        var smsCodeValue by remember { mutableStateOf(TextFieldValue(text = smsCodeHint)) }
        val isSmsCodeHint = { smsCodeValue.text == smsCodeHint }

        ScalingLazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = listState,
            contentPadding = PaddingValues(
                top = 8.dp,
                start = 8.dp,
                end = 8.dp,
                bottom = 8.dp
            )
        ) {
            item {
                BasicTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp),
                    value = mobileValue,
                    maxLines = 1,
                    textStyle = MaterialTheme.typography.body1.copy(color = LocalContentColor.current),
                    cursorBrush = SolidColor(LocalContentColor.current),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    onValueChange = {
                        mobileValue = it
                    })
            }

            item {
                BasicTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp),
                    value = smsCodeValue,
                    maxLines = 1,
                    textStyle = MaterialTheme.typography.body1.copy(color = LocalContentColor.current),
                    cursorBrush = SolidColor(LocalContentColor.current),
                    onValueChange = {
                        smsCodeValue = it
                    })
            }

            item {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                    getSmsCode(mobileValue.text)
                }) {
                    Text(text = "Get SMS Code")
                }
            }

            item {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                    login(mobileValue.text, smsCodeValue.text)
                }) {
                    Text(text = "Login")
                }
            }

        }
    }
}

fun getSmsCode(mobile: String) {
    Toast.makeText(App.getAppContext(), "mobile -> $mobile", Toast.LENGTH_SHORT).show()
    RequestHelper.networkScope.launch {
        try {
            val response = RetrofitService
                .graphApi
                .query(getSmsCodeParam(mobilePhoneNumber = mobile).toRequestBody())
        } catch (e: Exception) {
            Log.e("GetSMSCode", "Error", e)
        }

//        withContext(Dispatchers.Main) {
//            if (response. == 200) {
//                Toast.makeText(App.getAppContext(), "please check your sms", Toast.LENGTH_SHORT).show()
//            } else {
//                Toast.makeText(App.getAppContext(), "error!", Toast.LENGTH_SHORT).show()
//            }
//        }
    }
}

fun login(mobile: String, smsCode: String) {
    RequestHelper.networkScope.launch {
        try {
            val response = RetrofitService
                .graphApi
                .query(getLoginParam(mobilePhoneNumber = mobile, smsCode = smsCode).toRequestBody())
        } catch (e: Exception) {
            Log.e("login", "Error", e)
        }

//        withContext(Dispatchers.Main) {
//            if (response.code == 200) {
//                Toast.makeText(App.getAppContext(), "maybe success", Toast.LENGTH_SHORT).show()
//            } else {
//                Toast.makeText(App.getAppContext(), "error!", Toast.LENGTH_SHORT).show()
//            }
//        }
    }
    Toast.makeText(App.getAppContext(), "mobile -> $mobile\nsmsCode -> $smsCode", Toast.LENGTH_SHORT).show()
}

@Preview
@Composable
fun LoginPagePreview() {
    JikeTheme {
        LoginPage()
    }
}