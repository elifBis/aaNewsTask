package com.elifbis.aataskapp.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.elifbis.aataskapp.R
import com.elifbis.aataskapp.model.News
import com.elifbis.aataskapp.model.NewsDetailModel
import com.elifbis.aataskapp.viewmodel.NewsDetailViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.collectAsState
import kotlinx.coroutines.flow.StateFlow



@Composable
fun NewsDetailScreen(navController: NavController, newsId: Long?, viewModel: NewsDetailViewModel = viewModel()) {

    LaunchedEffect(Unit) {
        viewModel.getNews()
        viewModel.getNewsDetails() // Ekran yüklendiğinde detayları getir
    }

    val newsList by viewModel.newsList.collectAsState()
    val newsDetailList by viewModel.newsDetailList.collectAsState()

    val news = newsList.find { it.newsId == newsId }
    val newsDetail = newsDetailList.find { it.newsID == newsId }


    Log.d("NewsDetailScreen", "Gelen newsId: $newsId")
    Log.d("NewsDetailScreen", "newsDetailList boyutu: ${newsDetailList.size}")
    newsDetailList.forEach { Log.d("NewsDetailScreen", "newsID: ${it.newsID}") }

    if (news == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "Haber bulunamadı.", style = MaterialTheme.typography.bodyLarge)
        }
    } else

        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Text(text = news.title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Text(text = news.spot, style = MaterialTheme.typography.bodyLarge)

            if (newsDetail != null) {
                NewsDetail(news = news, newsDetail = newsDetail)
            } else {
                Text(
                    text = "Haber detayları yüklenemedi.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Red
                )

            }
        }

}


@Composable
fun NewsDetail(news: News, newsDetail: NewsDetailModel) {
    Column(modifier = Modifier.fillMaxSize()) {
        ImageGallery(news = news)

        Row(modifier = Modifier.fillMaxWidth().padding(10.dp)) {
            ReusableRowList(
                listOf(
                    { PriorityBox(news = news) },
                    { LanguageUIBox(news = news) },
                    {
                        VerticalDivider(
                            modifier = Modifier.height(20.dp),
                            thickness = 1.dp,
                            color = MaterialTheme.colorScheme.tertiary
                        )
                    },
                    { NewsTypeIcon(news = news) },
                    { IsRelatableIcon(news = news) },
                    { IsLockedIcon(newsDetail.isPublic) },
                    { IsApprovedIcon(newsDetail.approved) }
                )
            )
            Column(
                horizontalAlignment = AbsoluteAlignment.Right,
                modifier = Modifier.fillMaxSize()
            ) {
                DateTimeText(news = news)
                LocationText(news = news)
            }
        }

        ReusableHorizontalDivider()
        ReusableSpacer()
        NewsSpot(news = news)
        newsDetail.content?.let {
            Text(
                text = it,
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun ImageGallery(news: News) {
    val imageUrls = news.photos.split(",").map { it.trim() }.filter { it.isNotEmpty() }
    val pagerState = rememberPagerState(pageCount = { imageUrls.size })

    if (imageUrls.isNotEmpty()) {
        Column {
            HorizontalPager(state = pagerState) { page ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    val uri = imageUrls[page]
                    Box(modifier = Modifier.height(250.dp)) {
                        AsyncImage(
                            model = "https:$uri",
                            contentDescription = "Haber Fotoğrafı",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.fillMaxSize(),
                            error = painterResource(R.drawable.graph),
                            placeholder = painterResource(R.drawable.approved)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                if (imageUrls.size > 1) {
                    repeat(pagerState.pageCount) { iteration ->
                        val color = if (pagerState.currentPage == iteration) Color.Blue else Color.Black
                        Box(
                            modifier = Modifier
                                .padding(4.dp)
                                .clip(CircleShape)
                                .background(color)
                                .size(8.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun IsLockedIcon(isPublic: Boolean) {
    val color = if (isPublic) Color.Green else Color.Red
    Icon(
        modifier = Modifier.size(20.dp),
        painter = painterResource(R.drawable.locked),
        contentDescription = "Haber Durumu",
        tint = color
    )
}

@Composable
fun IsApprovedIcon(isApproved: Boolean) {
    val color = if (isApproved) Color.Green else Color.Red
    Icon(
        modifier = Modifier.size(20.dp),
        painter = painterResource(R.drawable.approved),
        contentDescription = "Onay Durumu",
        tint = color
    )
}

@Composable
fun NewsSpot(news: News) {
    val priorityColor = news.priority.color
    Text(
        text = news.spot,
        color = priorityColor,
        style = MaterialTheme.typography.titleSmall,
        fontWeight = FontWeight.Bold
    )
}