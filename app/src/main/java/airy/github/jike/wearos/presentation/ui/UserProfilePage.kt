package airy.github.jike.wearos.presentation.ui

import airy.github.jike.wearos.presentation.api.RequestHelper
import airy.github.jike.wearos.presentation.api.RetrofitService
import airy.github.jike.wearos.presentation.api.graphql.getBasicProfileParam
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.*
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.launch

@Composable
fun UserProfilePage() {
    val context = LocalContext.current  // only in compose function
    val listState = rememberScalingLazyListState()

    Scaffold(
        positionIndicator = {
            PositionIndicator(scalingLazyListState = listState)
        }) {

        ProfilePageCards(listState = listState)
    }
}
//{
//    "data": {
//    "profile": {
//    "distinctId": "58671996c07d1500126045d5",
//    "avatarImage": {
//    "thumbnailUrl": "https://cdn.jellow.site/Fl2nNa6cQ6pvx6LV1EHk32ImepOh.jpeg?imageMogr2/auto-orient/heic-exif/1/format/jpeg/thumbnail/!120x120r/gravity/Center/crop/!120x120a0a0",
//    "smallPicUrl": "https://cdn.jellow.site/Fl2nNa6cQ6pvx6LV1EHk32ImepOh.jpeg?imageMogr2/auto-orient/heic-exif/1/format/jpeg/thumbnail/!300x300r/gravity/Center/crop/!300x300a0a0",
//    "picUrl": "https://cdn.jellow.site/Fl2nNa6cQ6pvx6LV1EHk32ImepOh.jpeg?imageMogr2/auto-orient/heic-exif/1/format/jpeg/thumbnail/!1000x1000r/gravity/Center/crop/!1000x1000a0a0",
//    "__typename": "AvatarImage"
//},
//    "isSponsor": false,
//    "username": "c1a0120d-6909-4409-82ce-5d6d1ea9f277",
//    "screenName": "Airy今天跑路了吗",
//    "briefIntro": "📍小公司干Android/Rails/DevOps/SpringBoot没拿过年终奖的底层码农\n📷 有相机了！\n⌨ Leetcode渣渣",
//    "__typename": "UserSelfInfo",
//    "statsCount": {
//    "followedCount": 62,
//    "followingCount": 104,
//    "__typename": "UserStats"
//},
//    "backgroundImage": {
//    "picUrl": "https://cdn.jellow.site/FrPxfiuZBx7JRINp4XryWdl51c3gv2.png?imageMogr2/auto-orient/heic-exif/1/format/jpeg/thumbnail/1000x1000%3E",
//    "__typename": "BaseImage"
//},
//    "following": null
//}
//}
//}

@Composable
fun InfoCard(
    screenName: String,
    briefIntro: String,
    followedCount: Int,
    followingCount: Int,
    avatarImageUrl: String,
    backgroundImageUrl: String
) {
    Card(
        onClick = { },
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        backgroundPainter = CardDefaults.imageWithScrimBackgroundPainter(
            backgroundImagePainter = rememberAsyncImagePainter(
                model = backgroundImageUrl,
                filterQuality = FilterQuality.Low,
            )
        )
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = avatarImageUrl,
                    contentDescription = "user profile screen name",
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(width = 32.dp, height = 32.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Left,
                    color = MaterialTheme.colors.onBackground,
                    text = screenName
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = followingCount.toString(), color = MaterialTheme.colors.onBackground)
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "关注", fontWeight = FontWeight.Light, fontSize = 12.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = followedCount.toString(),  color = MaterialTheme.colors.onBackground)
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "被关注", fontWeight = FontWeight.Light, fontSize = 12.sp)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Left,
                color = MaterialTheme.colors.onBackground,
                text = briefIntro,
                fontSize = 12.sp
            )
        }
    }
}

@Preview
@Composable
fun InfoCardPreview() {
    InfoCard(
        screenName = "Airy今天跑路了吗",
        briefIntro = "📍小公司干Android/Rails/DevOps/SpringBoot没拿过年终奖的底层码农\n📷 有相机了！\n⌨ Leetcode渣渣",
        followedCount = 62,
        followingCount = 104,
        avatarImageUrl = "https://cdn.jellow.site/Fl2nNa6cQ6pvx6LV1EHk32ImepOh.jpeg?imageMogr2/auto-orient/heic-exif/1/format/jpeg/thumbnail/!120x120r/gravity/Center/crop/!120x120a0a0",
        backgroundImageUrl = "https://cdn.jellow.site/FrPxfiuZBx7JRINp4XryWdl51c3gv2.png?imageMogr2/auto-orient/heic-exif/1/format/jpeg/thumbnail/1000x1000%3E"
    )
}

@Composable
fun ProfilePageCards(listState: ScalingLazyListState, onCardItemClick:((Int, String) -> Unit)? = null) {
    ScalingLazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        state = listState,
        contentPadding = PaddingValues(
            top = 32.dp,
            start = 8.dp,
            end = 8.dp,
            bottom = 32.dp
        )
    ) {
        item {
            InfoCard(
                screenName = "Airy今天跑路了吗",
                briefIntro = "📍小公司干Android/Rails/DevOps/SpringBoot没拿过年终奖的底层码农\n📷 有相机了！\n⌨ Leetcode渣渣",
                followedCount = 62,
                followingCount = 104,
                avatarImageUrl = "https://cdn.jellow.site/Fl2nNa6cQ6pvx6LV1EHk32ImepOh.jpeg?imageMogr2/auto-orient/heic-exif/1/format/jpeg/thumbnail/!120x120r/gravity/Center/crop/!120x120a0a0",
                backgroundImageUrl = "https://cdn.jellow.site/FrPxfiuZBx7JRINp4XryWdl51c3gv2.png?imageMogr2/auto-orient/heic-exif/1/format/jpeg/thumbnail/1000x1000%3E"
            )
        }

        item {
            Card(
                onClick = {
                    RequestHelper.networkScope.launch {
                        try {
                            val response = RetrofitService.htmlApi.index()
                        } catch (e: Exception) {

                        }
                    }
                },
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colors.primary,
                    text = "Get Web Page"
                )
            }
        }

        item {
            Card(
                onClick = {
                    RequestHelper.networkScope.launch {
                        try {
                            val response = RetrofitService.graphApi.query(getBasicProfileParam().toRequestBody())
                        } catch (e: Exception) {

                        }
                    }
                },
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colors.primary,
                    text = "Get Basic Profile"
                )
            }
        }

//        items(data.size) { index ->
//            val messgae = data[index]
//            Card(
//                onClick = { onCardItemClick?.invoke(index, data[index]) },
//                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
//            ) {
//                Text(
//                    modifier = Modifier.fillMaxWidth(),
//                    textAlign = TextAlign.Center,
//                    color = MaterialTheme.colors.primary,
//                    text = messgae
//                )
//            }
//        }
    }
}