package com.example.currentnews.ui.screen

import android.widget.TextView
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.currentnews.R
import com.example.currentnews.ui.viewmodel.NewsDetailsViewModel

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun NewsDetailsScreen(
    newsId: String?,
    newsDetailsViewModel: NewsDetailsViewModel = hiltViewModel()
) {
    newsId?.let { newsDetailsViewModel.getSingleNewsById(it) }
    val news = newsDetailsViewModel.news.value
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth()
            .padding(top = 10.dp, bottom = 30.dp)
    ) {
        item {
            Column {
                Text(
                    text = news.sourceName,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.Start)
                        .padding(start = 20.dp, top = 15.dp, bottom = 10.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,
                    textAlign = TextAlign.Start,
                    style = TextStyle(textDecoration = TextDecoration.Underline)
                )
                Text(
                    text = news.newsTitle,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.Start)
                        .padding(start = 20.dp, end = 20.dp, top = 15.dp, bottom = 15.dp),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    textAlign = TextAlign.Start
                )
                Text(
                    text = stringResource(id = R.string.IDS_PUBLISH).plus(" ").plus(news.newsPublishedAt),
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.Start)
                        .padding(start = 20.dp, end = 20.dp, bottom = 15.dp),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Gray,
                    textAlign = TextAlign.Start
                )
                GlideImage(
                    model = news.newsImage,
                    contentDescription = "News Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .padding(top = 10.dp, bottom = 10.dp),
                    contentScale = ContentScale.Crop
                )

                AndroidView(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.Start)
                        .padding(start = 20.dp, end = 20.dp, top = 15.dp, bottom = 15.dp),
                    factory = { context -> TextView(context) },
                    update = {
                        it.text =
                            HtmlCompat.fromHtml(news.newsDescription, HtmlCompat.FROM_HTML_MODE_COMPACT)
                        it.textSize = 18f
                        it.setTextColor(android.graphics.Color.BLACK)},
                )
            }
        }

    }
}