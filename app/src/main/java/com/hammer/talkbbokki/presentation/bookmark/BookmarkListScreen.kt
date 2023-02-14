package com.hammer.talkbbokki.presentation.bookmark

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hammer.talkbbokki.R
import com.hammer.talkbbokki.domain.model.TopicItem
import com.hammer.talkbbokki.ui.theme.Black
import com.hammer.talkbbokki.ui.theme.Gray06
import com.hammer.talkbbokki.ui.theme.MainColor02
import com.hammer.talkbbokki.ui.theme.TalkbbokkiTypography

@Composable
fun BookMarkRoute(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    viewModel: BookmarkViewModel = hiltViewModel()
) {
    val bookmarkUiState by viewModel.bookmarkUiState.collectAsState()
    BookMarkScreen(bookmarkUiState) { onBackClick() }
}

@Composable
fun BookMarkScreen(
    bookmarkUiState: BookmarkUiState,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier.background(MainColor02)
    ) {
        BookmarkTopAppBar { onBackClick() }
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            BookmarkHeader()
            when (bookmarkUiState) {
                is BookmarkUiState.Success -> BookmarkList(bookmarkUiState.list) { /* 상세로 이동 */ }
                is BookmarkUiState.Empty -> BookmarkEmpty()
                is BookmarkUiState.Error -> Toast.makeText(
                    context,
                    "error : ${bookmarkUiState.message}",
                    Toast.LENGTH_SHORT
                ).show()
                is BookmarkUiState.Loading -> CircularProgressIndicator()
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
                start = 20.dp,
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
fun BookmarkHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 8.dp,
                start = 20.dp,
                end = 20.dp
            )
    ) {
        Text(
            text = stringResource(id = R.string.bookmark_header_title),
            style = TalkbbokkiTypography.h2_bold,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(36.dp))
    }
}

@Composable
fun BookmarkList(
    bookmarks: List<TopicItem>,
    onClickItem: (TopicItem) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp)
    ) {
        BookmarkTotalCount(bookmarks.count())
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(top = 12.dp)
        ) {
            items(bookmarks) {
                BookmarkItem(it, onClickItem)
            }
        }
    }
}

@Composable
fun BookmarkTotalCount(totalCount: Int) {
    Text(
        text = stringResource(id = R.string.bookmark_total_count, totalCount),
        style = TalkbbokkiTypography.b2_regular,
        color = Color.White
    )
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
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp)
    ) {
        BookmarkTotalCount(0)
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
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
    }
}
