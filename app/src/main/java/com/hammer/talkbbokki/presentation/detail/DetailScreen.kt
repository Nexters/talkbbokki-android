package com.hammer.talkbbokki.presentation.detail

import android.annotation.SuppressLint
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hammer.talkbbokki.R
import com.hammer.talkbbokki.domain.model.TopicItem
import com.hammer.talkbbokki.presentation.shareLink
import com.hammer.talkbbokki.presentation.shareScreenShot
import com.hammer.talkbbokki.presentation.topics.TopicLevel
import com.hammer.talkbbokki.ui.theme.Gray03
import com.hammer.talkbbokki.ui.theme.Gray04
import com.hammer.talkbbokki.ui.theme.Gray05
import com.hammer.talkbbokki.ui.theme.MainColor01
import com.hammer.talkbbokki.ui.theme.TalkbbokkiTypography
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun DetailRoute(
    onClickToList: () -> Unit,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val viewCntSuccess by viewModel.viewCntSuccess.collectAsState()
    if (viewCntSuccess){
        Toast.makeText(
            LocalContext.current,
            "viewCnt +1",
            Toast.LENGTH_SHORT
        ).show()
    }

    val toastMessage by viewModel.toastMessage.collectAsState()
    val item by viewModel.item.collectAsState()
    val starter by viewModel.talkOrder.collectAsState()

    DetailScreen(
        onClickToList = onClickToList,
        item = item,
        onClickBookmark = {
            if (it) viewModel.addBookmark() else viewModel.removeBookmark()
        },
        onClickStarter = {},
        updateViewCnt = { viewModel.postViewCnt(item.id) },
        starter = starter ?: ""
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
    onClickBookmark: (Boolean) -> Unit,
    onClickStarter: () -> Unit,
    updateViewCnt: () -> Unit,
    starter: String
) {
    var cardFace by remember { mutableStateOf(CardFace.FRONT) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(TopicLevel.valueOf(item.category.uppercase()).backgroundColor)
    ) {
        DetailHeader(cardFace = cardFace, onBackClick = { cardFace = CardFace.BACK })
        Box(modifier = modifier.fillMaxSize()) {
            DetailFlipCard(
                Modifier.align(Alignment.Center),
                cardFace,
                item,
                starter,
                onClickToList,
                onClickBookmark,
                onClickStarter,
                updateViewCnt
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
    starter: String,
    onClickToList: () -> Unit,
    onClickBookmark: (Boolean) -> Unit,
    onClickStarter: () -> Unit,
    updateViewCnt: () -> Unit,
) {
    var scale by remember { mutableStateOf(1f) }
    var rotation by remember { mutableStateOf(1f) }

    FlipCard(rotation = rotation,
        modifier = modifier
            .width(dimensionResource(id = R.dimen.selected_card_width))
            .aspectRatio(0.7f)
            .graphicsLayer(
                scaleX = scale, scaleY = scale, cameraDistance = 12f
            )
            .background(TopicLevel.valueOf(item.category.uppercase()).backgroundColor),
        front = {
            FrontCardFace(item)
        },
        back = {
            BackCardFace(item, starter, onClickBookmark, updateViewCnt)
        })

    if (cardFace == CardFace.FRONT) {
        LaunchedEffect(key1 = Unit) {
            coroutineScope {
                launch {
                    animate(
                        initialValue = 1f, targetValue = 1.3f, animationSpec = tween(
                            durationMillis = 1000, easing = FastOutSlowInEasing
                        )
                    ) { value: Float, _: Float ->
                        scale = value
                    }

                    animate(
                        initialValue = 0f, targetValue = 180f, animationSpec = tween(
                            durationMillis = 540, easing = FastOutSlowInEasing
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
                        initialValue = 180f, targetValue = 0f, animationSpec = tween(
                            durationMillis = 540, easing = FastOutSlowInEasing
                        )
                    ) { value, _ ->
                        rotation = value
                    }

                    animate(
                        initialValue = 1.3f, targetValue = 1f, animationSpec = tween(
                            durationMillis = 1000, easing = FastOutSlowInEasing
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
fun FrontCardFace(item: TopicItem) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(TopicLevel.valueOf(item.category.toUpperCase()).backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        val cardImage = painterResource(id = R.drawable.bg_card_large)
        val tag = when (item.tag) {
            "LOVE" -> {
                painterResource(id = R.drawable.ic_tag_love)
            }
            "DAILY" -> {
                painterResource(id = R.drawable.ic_tag_daily)
            }
            else -> {
                painterResource(id = R.drawable.ic_tag_if)
            }
        }

        Image(
            painter = cardImage, contentDescription = null, modifier = Modifier.fillMaxSize()
        )
        Image(
            painter = tag,
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
    item: TopicItem, starter: String, onClickBookmark: (Boolean) -> Unit, updateViewCnt: () -> Unit,
) {
    val context = LocalContext.current
    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
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
            Starter(starter)
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Gray03)
            )
            ShareBottom(onClickShareLink = {
                updateViewCnt()
                shareLink(context, item.shareLink)
            }, onClickScreenShot = {
                updateViewCnt()
                shareScreenShot(context)
            })
        }
    }
}

@Composable
fun Topic(
    item: TopicItem, onClickBookmark: (Boolean) -> Unit
) {
    var toggleBookmark by remember { mutableStateOf(item.isBookmark) }
    Column(
        modifier = Modifier.padding(24.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier.align(Alignment.CenterStart),
                text = stringResource(R.string.detail_topic),
                color = Gray05,
                style = TalkbbokkiTypography.card_title
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
                tint = if (toggleBookmark) MainColor01 else Gray04, contentDescription = null
            )
        }
        Spacer(
            modifier = Modifier
                .height(8.dp)
                .fillMaxWidth()
        )
        Text(
            text = item.name,
            style = TalkbbokkiTypography.b2_bold
        )
    }
}

@Composable
fun Starter(starter: String) {
    Column(
        modifier = Modifier
            .height(114.dp)
            .padding(24.dp)
    ) {
        Row(modifier = Modifier.height(24.dp)) {
            Text(
                modifier = Modifier.align(Alignment.CenterVertically),
                text = stringResource(R.string.detail_starter),
                color = Gray05,
                style = TalkbbokkiTypography.card_title,
                textAlign = TextAlign.Center,
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
                    .clickable {

                    }
                    .align(Alignment.CenterVertically),
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
            text = starter, style = TalkbbokkiTypography.b2_bold
        )
    }
}

@Composable
fun ShareBottom(
    onClickShareLink: () -> Unit, onClickScreenShot: () -> Unit
) {
    Row(modifier = Modifier.height(62.dp)) {
        Box(modifier = Modifier
            .fillMaxSize()
            .weight(1f)
            .clickable { onClickShareLink() }) {
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
        Box(modifier = Modifier
            .fillMaxSize()
            .weight(1f)
            .clickable { onClickScreenShot() }) {
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
