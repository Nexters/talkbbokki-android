package com.hammer.talkbbokki.presentation.comment

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hammer.talkbbokki.R

@Composable
fun CommentsRoute(
    onBackClick: () -> Unit,
    viewModel: CommentsViewModel = hiltViewModel()
) {
    CommentsScreen()
}

@Composable
fun CommentsScreen() {
    Column() {
        CommentsHeader()
        Comments()
        CommentInputArea()
    }
}

@Composable
fun CommentsHeader() {
    Box(
        Modifier
            .height(56.dp)
            .fillMaxWidth()
    ) {
        Button(onClick = { /*TODO*/ }) {
            Image(
                painter = painterResource(id = R.drawable.ic_arrow_left),
                contentDescription = null,
                alignment = Alignment.Center
            )
        }
        Text(
            text = "댓글()",
            Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun Comments() {

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentInputArea() {
    val commentText = remember { mutableStateOf("") }
    Column(
        modifier = Modifier.run {fillMaxWidth()
                .background(Color.LightGray, RoundedCornerShape(8.dp))
                .padding(8.dp)}
    ) {
        Text(
            text = "이 대화 주제 어땠나요?",
            style = MaterialTheme.typography.titleSmall,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(8.dp)
        )
        TextField(
            value = commentText.value,
            onValueChange = { commentText.value = it },
            modifier = Modifier.padding(8.dp)
        )
        Button(
            onClick = { /* 댓글 등록 로직 */ },
            modifier = Modifier
                .align(Alignment.End)
                .padding(8.dp)
        ) {
            Text(text = "등록")
        }
    }
}
