package com.hammer.talkbbokki.presentation.bookmark

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hammer.talkbbokki.R
import com.hammer.talkbbokki.domain.model.TopicItem
import com.hammer.talkbbokki.ui.theme.Black
import com.hammer.talkbbokki.ui.theme.Gray04
import com.hammer.talkbbokki.ui.theme.Gray06
import com.hammer.talkbbokki.ui.theme.MainColor02
import com.hammer.talkbbokki.ui.theme.TalkbbokkiTypography
import com.hammer.talkbbokki.ui.theme.White

@Composable
fun BookMarkRoute(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    viewModel: BookmarkViewModel = hiltViewModel()
) {
    val bookmarkList by viewModel.bookmarkList.collectAsState()
    BookMarkScreen(bookmarkList, {}, { onBackClick() })
}

@Composable
fun BookMarkScreen(
    bookmarks: List<TopicItem>,
    onClickItem: (TopicItem) -> Unit,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier.background(MainColor02)
            .padding(start = 20.dp, end = 20.dp)
    ) {
        BookmarkTopAppBar { onBackClick() }
        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(bottom = 12.dp)
        ) {
            item(span = { GridItemSpan(2) }) {
                BookmarkHeader(bookmarks.count())
            }

            if (bookmarks.isEmpty()) {
                item(span = { GridItemSpan(2) }) {
                    BookmarkEmpty()
                }
            } else {
                items(bookmarks) {
                    BookmarkItem(it) { item -> onClickItem(item) }
                }
            }
        }
    }
}

@Composable
fun BookmarkTopAppBar(
    onClickBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(58.dp)
            .padding(
                top = 16.dp,
                bottom = 16.dp
            )
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_left),
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.clickable {
                onClickBack()
            }
        )
    }
}

@Composable
fun BookmarkHeader(totalCount: Int) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
    ) {
        Text(
            text = stringResource(id = R.string.bookmark_header_title),
            style = TalkbbokkiTypography.h2_bold,
            color = White
        )
        Spacer(modifier = Modifier.height(36.dp))
        Text(
            text = stringResource(id = R.string.bookmark_total_count, totalCount),
            style = TalkbbokkiTypography.b2_regular,
            color = Gray04
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BookmarkItem(
    item: TopicItem,
    onClickItem: (TopicItem) -> Unit
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        onClick = {
            onClickItem(item)
        }
    ) {
        Column(
            modifier = Modifier
                .width(154.dp)
                .height(233.dp)
                .background(Color.White)
                .padding(16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = item.tag,
                    style = TalkbbokkiTypography.b1_bold,
                    color = Black
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_star_fill),
                    tint = Gray06,
                    contentDescription = null
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = item.name,
                style = TalkbbokkiTypography.b3_regular,
                color = Black
            )
        }
    }
}

@Composable
fun BookmarkEmpty() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(113.dp))
        Icon(
            painter = painterResource(id = R.drawable.icon_empty_balloon),
            contentDescription = null,
            tint = Color.White
        )
        Spacer(modifier = Modifier.height(35.dp))
        Text(
            text = stringResource(id = R.string.bookmark_empty_list),
            color = Color.White
        )
        Spacer(modifier = Modifier.height(166.dp))
    }
}
