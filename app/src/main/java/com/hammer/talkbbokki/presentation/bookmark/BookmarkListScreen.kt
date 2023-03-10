package com.hammer.talkbbokki.presentation.bookmark

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hammer.talkbbokki.R
import com.hammer.talkbbokki.domain.model.TopicItem
import com.hammer.talkbbokki.ui.dialog.BookmarkCancelDialog
import com.hammer.talkbbokki.ui.theme.*

@Composable
fun BookMarkRoute(
    modifier: Modifier = Modifier,
    navigateToDetail: (TopicItem) -> Unit,
    onBackClick: () -> Unit,
    viewModel: BookmarkViewModel = hiltViewModel()
) {
    val bookmarkList by viewModel.bookmarkList.collectAsState()
    val showCancelDialog by viewModel.showCancelDialog.collectAsState()
    BookMarkScreen(
        bookmarkList,
        showCancelDialog,
        onClickItem = { navigateToDetail(it) },
        removeBookmark = { viewModel.removeBookmark(it) },
        onBackClick = { onBackClick() },
        onCloseDialog = { viewModel.closeDialog() }
    )
}

@Composable
fun BookMarkScreen(
    bookmarks: List<TopicItem>,
    showCancelDialog: Boolean,
    onClickItem: (TopicItem) -> Unit,
    removeBookmark: (Int) -> Unit,
    onBackClick: () -> Unit,
    onCloseDialog: () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    var selectItem by remember { mutableStateOf(-1) }

    if (showCancelDialog && showDialog) {
        BookmarkCancelDialog(
            textRes = R.string.bookmark_cancel_dialog_text,
            subTextRes = R.string.bookmark_cancel_dialog_subtext,
            agreeTextRes = R.string.bookmark_cancel_dialog_agree,
            agreeAction = {
                removeBookmark(selectItem)
                showDialog = false
                onCloseDialog()
            },
            disagreeTextRes = R.string.bookmark_cancel_dialog_disagree,
            disagreeAction = {
                showDialog = false
                onCloseDialog()
            }
        )
    }
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
                    BookmarkItem(
                        it,
                        onClickItem = { item -> onClickItem(item) },
                        onToggleBookmark = { id ->
                            selectItem = id
                            if (showCancelDialog) {
                                showDialog = !showDialog
                            } else {
                                removeBookmark(id)
                            }
                        }
                    )
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
            tint = White,
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
    onClickItem: (TopicItem) -> Unit,
    onToggleBookmark: (Int) -> Unit
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
                .background(White)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = item.tag,
                    style = TalkbbokkiTypography.b1_bold,
                    color = Black
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_star_fill),
                    tint = MainColor01,
                    contentDescription = null,
                    modifier = Modifier.clickable { onToggleBookmark(item.id) }
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
            tint = White
        )
        Spacer(modifier = Modifier.height(35.dp))
        Text(
            text = stringResource(id = R.string.bookmark_empty_list),
            color = White
        )
        Spacer(modifier = Modifier.height(166.dp))
    }
}
