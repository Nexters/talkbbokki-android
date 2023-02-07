package com.hammer.talkbbokki

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.SnapLayoutInfoProvider
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.*
import androidx.compose.ui.zIndex
import kotlin.math.absoluteValue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface {
                Sample()
            }
        }
    }
}


@Composable
private fun Sample() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("horiz_pager") },
                backgroundColor = MaterialTheme.colors.surface,
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { padding ->
        HorizontalLazyColumnWithOffsetTransition(
            Modifier
                .padding(padding)
                .background(color = Color.White))
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HorizontalLazyColumnWithOffsetTransition(modifier: Modifier = Modifier) {
    val listState = rememberLazyListState()
    val itemWidth = with(LocalDensity.current) { 120.dp.toPx() }
    val currentIndex = remember { derivedStateOf { listState.firstVisibleItemIndex } }
    val offset = remember { derivedStateOf { listState.firstVisibleItemScrollOffset } }

    val snappingLayout = remember(listState) { SnapLayoutInfoProvider(listState) }
    val snapFlingBehavior = rememberSnapFlingBehavior(snappingLayout)

    val currentOffset = currentIndex.value+(offset.value / itemWidth)

    val cardList = (0..10).toList()
    BoxWithConstraints {
        LazyRow(state = listState,
            modifier = modifier.fillMaxSize(),
            flingBehavior = snapFlingBehavior,
            verticalAlignment = Alignment.CenterVertically,
            contentPadding = PaddingValues(start = ((maxWidth-120.dp)/2), end = ((maxWidth-120.dp)/2))
        ) {
            itemsIndexed(cardList) { definiteIndex, item ->
                CardItems(definiteIndex, currentOffset)
            }
        }
    }

}

@Composable
fun CardItems(definiteIndex:Int, currentOffset: Float) {
    var colors = arrayListOf<Color>(Color.Red, Color.Green, Color.Blue, Color.Cyan, Color.Yellow)
    val pageOffsetWithSign = definiteIndex - currentOffset
    val pageOffset = pageOffsetWithSign.absoluteValue
    Card(
        Modifier
            .width(120.dp)
            .aspectRatio(0.7f)
            .zIndex(5f - pageOffset)
            .graphicsLayer {

                // 중간으로 올수록 1.6배 크게 보임
                lerp(
                    start = 1f.dp,
                    stop = 1.6f.dp,
                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                ).let { scale ->
                    scaleX = scale.value
                    scaleY = scale.value
                }

                // 중간으로 올수록 0도에 가까워짐. (카드 사이 각도 10도씩 틀어짐)
                lerp(
                    start = pageOffsetWithSign * 10f.dp,  // -20, -10, 0, 10, 20 ...
                    stop = 0f.dp,
                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                ).value.let { angle ->
                    rotationZ = angle
                }

                // 중간에서 멀어질수록 수직으로 내려가도록 조정.
                lerp(
                    start = pageOffset * 50f.dp,
                    stop = 0f.dp,
                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                ).value.let { yOffset ->
                    translationY = yOffset
                }

                // 카드가 겹치게 보이도록 중앙으로 모이도록 조정.
                lerp(
                    start = pageOffsetWithSign * pageOffset * (50f * -1).dp,
                    stop = 0f.dp,
                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                ).value.let { xOffset ->
                    Log.d("AAAA $definiteIndex", "xOffset $xOffset")
                    translationX = xOffset
                }

            },
    ) {
        Box(modifier = Modifier.fillMaxSize().background(colors[definiteIndex % colors.size])) {
            /*Image(
                painter = rememberImagePainter(
                    data = "https://avatars.githubusercontent.com/u/7722921?v=4",
                ),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
            )*/
            Text(
                text = definiteIndex.toString()
            )
        }
    }
}