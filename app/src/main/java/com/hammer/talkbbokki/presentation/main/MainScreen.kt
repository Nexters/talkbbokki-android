package com.hammer.talkbbokki.presentation.main

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hammer.talkbbokki.R
import com.hammer.talkbbokki.ui.theme.MainBackgroundColor
import com.hammer.talkbbokki.ui.theme.TalkbbokkiTypography
import com.hammer.talkbbokki.ui.theme.White

@Composable
fun MainRoute(
    modifier: Modifier = Modifier,
    onClickLevel: (String) -> Unit,
    viewModel: MainViewModel = hiltViewModel()
) {
    val categoryLevel by viewModel.categoryLevel.collectAsState()
    MainScreen(categoryLevel) { onClickLevel(it) }
}

@Composable
fun MainScreen(
    categoryLevel: List<CategoryLevel>,
    onClickLevel: (String) -> Unit
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MainBackgroundColor)
    ) {
        MainHeader()
        CategoryLevels(categoryLevel) {
            onClickLevel(it)
            Toast.makeText(context, "click $it", Toast.LENGTH_SHORT).show()
        }
    }
}

@Composable
fun MainHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
                .padding(top = 24.dp, end = 20.dp, bottom = 12.dp),
            horizontalAlignment = Alignment.End
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_like_list),
                contentDescription = null,
                tint = White
            )
        }
        Box(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.padding(top = 40.dp, start = 20.dp)
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
                    color = White
                )
                Spacer(modifier = Modifier.height(52.dp))
            }
            Image(
                painter = painterResource(id = R.drawable.image_main_graphic),
                contentDescription = null,
                modifier = Modifier.align(Alignment.TopEnd)
            )
        }
    }
}

@Composable
fun CategoryLevels(
    categoryLevel: List<CategoryLevel>,
    onClickLevel: (String) -> Unit
) {
    LazyVerticalGrid(
        modifier = Modifier.padding(start = 22.dp, end = 22.dp),
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        userScrollEnabled = false
    ) {
        items(categoryLevel) {
            LevelItem(it, onClickLevel)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LevelItem(
    level: CategoryLevel,
    onClickLevel: (String) -> Unit
) {
    Box(modifier = Modifier.aspectRatio(.8f)) {
        Card(
            modifier = Modifier.fillMaxSize(),
            shape = RoundedCornerShape(8.dp),
            onClick = {
                onClickLevel(level.level)
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(level.backgroundColor),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = level.icon),
                    contentDescription = null
                )
                Text(
                    text = stringResource(id = level.title),
                    style = TalkbbokkiTypography.b2_bold,
                    color = White,
                    textAlign = TextAlign.Center
                )
            }
        }
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.bling_effect),
            contentScale = ContentScale.FillBounds,
            contentDescription = null
        )
    }
}
