package com.hammer.talkbbokki.presentation.detail

import android.widget.Toast
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hammer.talkbbokki.R
import com.hammer.talkbbokki.domain.model.TopicItem
import com.hammer.talkbbokki.presentation.topics.TopicLevel
import com.hammer.talkbbokki.ui.theme.Gray03
import com.hammer.talkbbokki.ui.theme.Gray05
import com.hammer.talkbbokki.ui.theme.TalkbbokkiTypography
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

val level = "LEVEL1"

@Composable
fun DetailRoute(
    modifier: Modifier = Modifier,
    onClickToList: () -> Unit,
    id: String,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val toastMessage by viewModel.toastMessage.collectAsState()
    val item by viewModel.item.collectAsState()
    DetailScreen(
        onClickToList = onClickToList,
        item = item,
        onClickBookmark = {
            if (it) viewModel.addBookmark() else viewModel.removeBookmark()
        }
    )
    if (toastMessage > 0) {
        Toast.makeText(LocalContext.current, stringResource(id = toastMessage), Toast.LENGTH_SHORT)
            .show()
    }
}

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    onClickToList: () -> Unit,
    item: TopicItem,
    onClickBookmark: (Boolean) -> Unit
) {
    var cardFace by remember { mutableStateOf(CardFace.FRONT) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(TopicLevel.valueOf(level).backgroundColor)
    ) {
        DetailHeader(cardFace = cardFace, onBackClick = { cardFace = CardFace.BACK })
        Box(modifier = modifier.fillMaxSize()) {
            DetailFlipCard(
                Modifier.align(Alignment.Center),
                cardFace,
                item,
                onClickToList,
                onClickBookmark
            )
        }
    }
}

@Composable
fun DetailHeader(cardFace: CardFace, onBackClick: () -> Unit) {
    if (cardFace == CardFace.FRONT) {
        Icon(
            modifier = Modifier
                .padding(
                    horizontal = dimensionResource(id = R.dimen.horizontal_padding),
                    vertical = dimensionResource(id = R.dimen.vertical_padding)
                )
                .size(24.dp)
                .clickable { onBackClick() },
            painter = painterResource(id = R.drawable.ic_arrow_left),
            contentDescription = null
        )
    }
}

@Composable
fun DetailFlipCard(
    modifier: Modifier = Modifier,
    cardFace: CardFace,
    item: TopicItem,
    onClickToList: () -> Unit,
    onClickBookmark: (Boolean) -> Unit
) {
    var scale by remember { mutableStateOf(1f) }
    var rotation by remember { mutableStateOf(1f) }

    FlipCard(
        rotation = rotation,
        modifier = modifier
            .width(dimensionResource(id = R.dimen.selected_card_width))
            .aspectRatio(0.7f)
            .graphicsLayer(
                scaleX = scale,
                scaleY = scale,
                cameraDistance = 12f
            )
            .background(TopicLevel.valueOf(level).backgroundColor),
        front = {
            FrontCardFace(item.id)
        },
        back = {
            BackCardFace(item, onClickBookmark)
        }
    )

    if (cardFace == CardFace.FRONT) {
        LaunchedEffect(key1 = Unit) {
            coroutineScope {
                launch {
                    animate(
                        initialValue = 1f,
                        targetValue = 1.3f,
                        animationSpec = tween(
                            durationMillis = 1000,
                            easing = FastOutSlowInEasing
                        )
                    ) { value: Float, _: Float ->
                        scale = value
                    }

                    animate(
                        initialValue = 0f,
                        targetValue = 180f,
                        animationSpec = tween(
                            durationMillis = 540,
                            easing = FastOutSlowInEasing
                        )
                    ) { value, _ ->
                        rotation = value
                    }
                }
            }
        }
    } else {
        LaunchedEffect(key1 = Unit) {
            coroutineScope {
                launch {
                    animate(
                        initialValue = 180f,
                        targetValue = 0f,
                        animationSpec = tween(
                            durationMillis = 540,
                            easing = FastOutSlowInEasing
                        )
                    ) { value, _ ->
                        rotation = value
                    }

                    animate(
                        initialValue = 1.3f,
                        targetValue = 1f,
                        animationSpec = tween(
                            durationMillis = 1000,
                            easing = FastOutSlowInEasing
                        )
                    ) { value: Float, _: Float ->
                        scale = value
                    }
                    onClickToList()
                }
            }
        }
    }
}

@Composable
fun FrontCardFace(id: Int) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(TopicLevel.valueOf(level).backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        val cardImage = painterResource(id = R.drawable.bg_card_large)
        val category = painterResource(id = R.drawable.ic_tag_love)

        Image(
            painter = cardImage,
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
        Image(
            painter = category,
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = dimensionResource(id = R.dimen.horizontal_padding),
                    vertical = 32.dp
                )
        )
    }
}

@Composable
fun BackCardFace(
    item: TopicItem,
    onClickBookmark: (Boolean) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val cardImage = painterResource(id = R.drawable.card_back)

        Image(painter = cardImage, contentDescription = null, modifier = Modifier.fillMaxSize())
        Column(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 15.dp)
        ) {
            Topic(item, onClickBookmark)
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(Color.White)
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Gray03)
            )
            Starter()
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Gray03)
            )
            ShareBottom()
        }
    }
}

@Composable
fun Topic(
    item: TopicItem,
    onClickBookmark: (Boolean) -> Unit
) {
    var toggleBookmark by remember { mutableStateOf(item.isBookmark) }
    Column(
        modifier = Modifier
            .padding(24.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier.align(Alignment.CenterStart),
                text = stringResource(R.string.detail_topic),
                color = Gray05
            )
            Icon(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .size(24.dp)
                    .padding(2.dp)
                    .clickable {
                        toggleBookmark = !toggleBookmark
                        onClickBookmark(toggleBookmark)
                    },
                painter = painterResource(
                    id = if (toggleBookmark) R.drawable.ic_star_fill else R.drawable.ic_star_empty
                ),
                tint = Gray05,
                contentDescription = null
            )
        }
        Spacer(
            modifier = Modifier
                .height(8.dp)
                .fillMaxWidth()
        )
        Text(
            text = "천사처럼 자고 있던 내 연인, 슬며시 다가가 머리를 쓰다듬는데 나도 몰랐던 가발이 벗겨졌다. 모르는척 다시 씌워준다 vs 왜 날 속였냐고 따진다.",
            style = TalkbbokkiTypography.b2_bold
        )
    }
}

@Composable
fun Starter() {
    Column(
        modifier = Modifier
            .height(114.dp)
            .padding(24.dp)
    ) {
        Row(modifier = Modifier.height(24.dp)) {
            Text(
                text = stringResource(R.string.detail_starter),
                color = Gray05
            )
            Spacer(
                modifier = Modifier
                    .width(4.dp)
                    .fillMaxHeight()
            )
            Icon(
                modifier = Modifier
                    .size(18.dp)
                    .padding(2.dp)
                    .clickable { },
                painter = painterResource(id = R.drawable.ic_refresh),
                tint = Gray05,
                contentDescription = null
            )
        }
        Spacer(
            modifier = Modifier
                .height(8.dp)
                .fillMaxWidth()
        )
        Text(
            text = "1인 1닭 가능할 것 같은 사람",
            style = TalkbbokkiTypography.b2_bold
        )
    }
}

@Composable
fun ShareBottom() {
    Row(modifier = Modifier.height(62.dp)) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .clickable { }
        ) {
            Image(
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.Center),
                painter = painterResource(id = R.drawable.ic_share),
                contentDescription = null
            )
        }
        Spacer(
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp)
                .background(Gray03)
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .clickable { }
        ) {
            Image(
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.Center),
                painter = painterResource(id = R.drawable.ic_download),
                contentDescription = null
            )
        }
    }
}

enum class CardFace {
    FRONT, BACK
}
