package com.hammer.talkbbokki.presentation.main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.hammer.talkbbokki.R
import com.hammer.talkbbokki.domain.model.CategoryLevel
import com.hammer.talkbbokki.ui.dialog.CommonDialog
import com.hammer.talkbbokki.ui.theme.Gray04
import com.hammer.talkbbokki.ui.theme.Gray07
import com.hammer.talkbbokki.ui.theme.MainBackgroundColor
import com.hammer.talkbbokki.ui.theme.TalkbbokkiTypography
import com.hammer.talkbbokki.ui.theme.White
import com.hammer.talkbbokki.ui.theme.suggestionButtonColor
import com.hammer.talkbbokki.ui.util.toHexColor

@Composable
fun MainRoute(
    modifier: Modifier = Modifier,
    onClickBookmarkMenu: () -> Unit,
    onClickLevel: (String, String) -> Unit,
    onClickSuggestion: () -> Unit,
    viewModel: MainViewModel = hiltViewModel()
) {
    val categoryLevel by viewModel.categoryLevel.collectAsState()
    MainScreen(
        categoryLevel = categoryLevel,
        onClickBookmarkMenu = { onClickBookmarkMenu() },
        onClickLevel = onClickLevel,
        onClickSuggestion = { onClickSuggestion() }
    )
}

@Composable
fun MainScreen(
    categoryLevel: List<CategoryLevel>,
    onClickBookmarkMenu: () -> Unit,
    onClickLevel: (String, String) -> Unit,
    onClickSuggestion: () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    if (showDialog) {
        CommonDialog(
            text = stringResource(id = R.string.main_coming_soon_dialog_text),
            subText = null,
            agreeText = stringResource(id = R.string.main_coming_soon_dialog_close_button),
            agreeAction = { showDialog = false }
        )
    }
    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize()
            .background(MainBackgroundColor)
            .padding(start = 20.dp, end = 20.dp, bottom = 22.dp),
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item(span = { GridItemSpan(2) }) {
            MainHeader { onClickBookmarkMenu() }
        }

        items(categoryLevel) {
            LevelItem(it, onClickLevel) { showDialog = true }
        }

        item(span = { GridItemSpan(2) }) {
            SuggestionButton { onClickSuggestion() }
        }
    }
}

@Composable
fun MainHeader(
    onClickBookmarkMenu: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
                .padding(top = 24.dp),
            horizontalAlignment = Alignment.End
        ) {
            Icon(
                modifier = Modifier.clickable { onClickBookmarkMenu() },
                painter = painterResource(id = R.drawable.ic_like_list),
                contentDescription = null,
                tint = White
            )
        }
        Box(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(id = R.drawable.image_main_graphic),
                contentDescription = null,
                modifier = Modifier.align(Alignment.BottomEnd).offset(x = 20.dp)
            )
            Column(
                modifier = Modifier.padding(top = 32.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.main_title),
                    style = TalkbbokkiTypography.h1,
                    color = White
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = stringResource(id = R.string.main_sub_title),
                    style = TalkbbokkiTypography.b3_regular,
                    color = Gray04
                )
                Spacer(modifier = Modifier.height(48.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LevelItem(
    level: CategoryLevel,
    onClickLevel: (String, String) -> Unit,
    showDialog: () -> Unit
) {
    Box(modifier = Modifier.aspectRatio(1f)) {
        Card(
            modifier = Modifier.fillMaxSize(),
            shape = RoundedCornerShape(8.dp),
            onClick = {
                if (level.isActive) onClickLevel(level.id, (level.title).replace("\n","**")) else showDialog()
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(level.bgColor.toHexColor()),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = level.image
                    ),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(fraction = 0.58f)
                )
                Text(
                    text = level.title,
                    style = TalkbbokkiTypography.b2_bold,
                    color = White,
                    textAlign = TextAlign.Center
                )
            }
        }
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.image_main_bling_effect),
            contentScale = ContentScale.FillBounds,
            contentDescription = null
        )
    }
}

@Composable
fun SuggestionButton(
    onClickButton: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .wrapContentSize()
                .background(suggestionButtonColor, RoundedCornerShape(24.dp))
                .border(BorderStroke(1.dp, Gray07), RoundedCornerShape(24.dp))
                .padding(top = 10.dp, bottom = 10.dp, start = 24.dp, end = 24.dp)
                .clickable { onClickButton() },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = stringResource(id = R.string.main_suggestion_topic),
                style = TalkbbokkiTypography.button_small_regular,
                color = Gray04
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(id = R.string.main_suggestion_topic_button),
                style = TalkbbokkiTypography.button_small_bold,
                color = White
            )
            Spacer(modifier = Modifier.width(2.dp))
            Icon(
                modifier = Modifier.width(14.dp).height(14.dp),
                painter = painterResource(id = R.drawable.ic_arrow_next),
                tint = White,
                contentDescription = null
            )
        }
    }
}
