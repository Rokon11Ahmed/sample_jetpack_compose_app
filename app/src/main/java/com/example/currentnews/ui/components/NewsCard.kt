package com.example.currentnews.ui.components



import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.currentnews.R
import com.example.currentnews.domain.model.News

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun NewsCard(news: News, onClick: () -> Unit) {
    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .padding(bottom = 6.dp, top = 6.dp, start = 6.dp, end = 6.dp)
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Column {
            news.newsImage?.let { url ->
                GlideImage(
                    model  =  url ,
                    contentDescription = "News Image",
                    modifier = Modifier.fillMaxSize()
                        .height(225.dp),
                    contentScale = ContentScale.Crop
                )
            }
            news.newsTitle?.let { title ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top=12.dp, bottom=12.dp, start = 8.dp, end=8.dp)
                ){
                    Text(
                        text = title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.Start)
                        ,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        color = Color.Black,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Start
                    )
                    /*val rank = news.newsPublishedAt.toString()
                    Text(
                        text = rank,
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.End)
                            .align(Alignment.CenterVertically)
                        ,
                        style = MaterialTheme.typography.titleSmall
                    )*/
                }
            }
        }
    }
}