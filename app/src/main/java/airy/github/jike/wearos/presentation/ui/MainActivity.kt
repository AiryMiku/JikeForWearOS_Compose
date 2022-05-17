package airy.github.jike.wearos.presentation.ui

import airy.github.jike.wearos.presentation.theme.JikeTheme
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.foundation.*
import androidx.wear.compose.material.*
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.*

class MainActivity : ComponentActivity() {

    private val mainScope = MainScope()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WearApp("Android", mainScope)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mainScope.cancel()
    }
}

@OptIn(ExperimentalWearMaterialApi::class, ExperimentalPagerApi::class)
@Composable
fun WearApp(greetingName: String, scope: CoroutineScope) {
    JikeTheme {
        /* If you have enough items in your list, use [ScalingLazyColumn] which is an optimized
         * version of LazyColumn for wear devices with some added features. For more information,
         * see d.android.com/wear/compose.
         */
        val maxPages = 2
        val pageTitle = listOf("Login", "Me")
        val pagerState = rememberPagerState()

        val pageIndicatorState: PageIndicatorState = remember {
            object : PageIndicatorState {
                override val pageOffset: Float
                    get() = pagerState.currentPageOffset
                override val selectedPage: Int
                    get() = pagerState.currentPage
                override val pageCount: Int
                    get() = pagerState.pageCount
            }
        }


        Scaffold(
            timeText = {
                TimeText(
                    startCurvedContent = {
                        basicCurvedText(
                            "即刻", CurvedTextStyle(
                                color = Color.White
                            )
                        )
                    },
                    endCurvedContent = {
                        basicCurvedText(
                            pageTitle[pagerState.currentPage], CurvedTextStyle(
                                color = Color.White
                            )
                        )
                    }
                )
            },
            pageIndicator = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(6.dp)
                ) {
                    HorizontalPageIndicator(
                        pageIndicatorState = pageIndicatorState
                    )
                }
            }
        ) {
            HorizontalPager(
                count = maxPages,
                state = pagerState
            ) { page ->
                when (page) {
                    0 -> LoginPage()
                    1 -> UserProfilePage()
                }
            }
        }

//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(MaterialTheme.colors.background),
//            verticalArrangement = Arrangement.Center
//        ) {
////            Greeting(greetingName = greetingName)
//
//        }
    }
}

//@Composable
//fun Greeting(greetingName: String) {
//    Text(
//        modifier = Modifier.fillMaxWidth(),
//        textAlign = TextAlign.Center,
//        color = MaterialTheme.colors.primary,
//        text = stringResource(R.string.hello_world, greetingName)
//    )
//}


const val WEAR_PREVIEW_ELEMENT_WIDTH_DP = 100
const val WEAR_PREVIEW_ELEMENT_HEIGHT_DP = 100

const val WEAR_PREVIEW_ROW_WIDTH_DP = 300
const val WEAR_PREVIEW_ROW_HEIGHT_DP = 100

const val WEAR_PREVIEW_DEVICE_WIDTH_DP = 300
const val WEAR_PREVIEW_DEVICE_HEIGHT_DP = 300

const val WEAR_PREVIEW_API_LEVEL = 26

const val WEAR_PREVIEW_UI_MODE = Configuration.UI_MODE_TYPE_WATCH

const val WEAR_PREVIEW_BACKGROUND_COLOR_BLACK: Long = 0x000000
const val WEAR_PREVIEW_SHOW_BACKGROUND = true

@Preview(
    widthDp = WEAR_PREVIEW_DEVICE_WIDTH_DP,
    heightDp = WEAR_PREVIEW_DEVICE_HEIGHT_DP,
    apiLevel = WEAR_PREVIEW_API_LEVEL,
    uiMode = WEAR_PREVIEW_UI_MODE,
    backgroundColor = WEAR_PREVIEW_BACKGROUND_COLOR_BLACK,
    showBackground = WEAR_PREVIEW_SHOW_BACKGROUND
)
@Composable
fun DefaultPreview() {
    WearApp("Preview Android", MainScope())
}

