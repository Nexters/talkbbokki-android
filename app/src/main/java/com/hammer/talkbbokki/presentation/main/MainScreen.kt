package com.hammer.talkbbokki.presentation.main

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hammer.talkbbokki.R
import com.hammer.talkbbokki.ui.theme.MainBackgroundColor
import com.hammer.talkbbokki.ui.theme.talkbbokkiTypography

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
            .padding(20.dp),
        verticalArrangement = Arrangement.SpaceEvenly
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
    Column {
//        Divider(modifier = Modifier.padding(top = 90.dp))
        Text(
            text = stringResource(id = R.string.main_sub_title),
            style = talkbbokkiTypography.b3_regular,
            color = Color.White
        )
        Divider(modifier = Modifier.padding(top = 6.dp))
        Text(
            text = stringResource(id = R.string.main_title),
            style = talkbbokkiTypography.h1,
            color = Color.White
        )
        Divider(modifier = Modifier.padding(top = 20.dp))
    }
}

@Composable
fun CategoryLevels(
    categoryLevel: List<CategoryLevel>,
    onClickLevel: (String) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
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
    Card(
        modifier = Modifier
            .aspectRatio(0.8f),
        onClick = {
            onClickLevel(level.level)
        }
    ) {
        Box(
            modifier = Modifier
                .background(level.backgroundColor)
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(id = level.title),
                    style = talkbbokkiTypography.b2_regular,
                    color = Color.White
                )
                Icon(
                    modifier = Modifier.padding(top = 10.dp),
                    painter = painterResource(id = R.drawable.icon_arrow),
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
    }
}
