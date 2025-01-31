package com.elifbis.aataskapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.elifbis.aataskapp.R
import com.elifbis.aataskapp.model.News
import com.elifbis.aataskapp.ui.theme.aa_blue
import com.google.accompanist.pager.HorizontalPagerIndicator

@Composable
fun NewsDetail(news: News) {
    Column(
       modifier = Modifier
           .fillMaxSize()
            //padding(16.dp)
    ) {
        ImageGallery(news = news)
        Row(
            modifier = Modifier.fillMaxWidth()
                .height(IntrinsicSize.Min)
                .padding(10.dp),
        ){
            ReusableRowList(
                listOf(
                    {PriorityBox(news = news) },
                    {LanguageUIBox(news = news) },
                    {VerticalDivider(
                        modifier = Modifier.height(20.dp),
                        thickness = 1.dp,
                        color = MaterialTheme.colorScheme.tertiary
                    )
                    },
                    {NewsTypeIcon(news = news)},
                    {IsRelatableIcon(news = news)},
                    {IsLockedIcon(news = news)},
                    {IsApproved(news = news)}
                )
            )
            Column(
                horizontalAlignment = AbsoluteAlignment.Right,
                modifier = Modifier.fillMaxSize()
            ){
                DateTimeText(news = news)
                LocationText(news = news)
            }
        }
        ReusableHorizontalDivider()
        ReusableSpacer()
        Row(){
            ReusableSpacer()
            ReusableSpacer()
            NewsTitle(news = news)
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
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val uri = imageUrls[page]
                    Box(
                        modifier = Modifier.height(250.dp)
                    ){
                        AsyncImage(
                            model = "https:$uri",
                            contentDescription = "Haber Fotoğrafı",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .fillMaxSize(),
                            //.height(250.dp)
                            error = painterResource(R.drawable.graph),
                            placeholder = painterResource(R.drawable.approved)
                        )

                        Spacer(modifier = Modifier.height(8.dp))
                    }

                }
        }
            ReusableSpacer()
            Row(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                if (imageUrls.size > 1) {
                    repeat(pagerState.pageCount) { iteration ->
                        val color = if (pagerState.currentPage == iteration) aa_blue
                        else Color.Black
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
}}
@Composable
fun IsLockedIcon(news: News){
//    val color : Color
//    if (news.?){
//        color = aa_blue
//    }else{
//        color = MaterialTheme.colorScheme.tertiary
//    }
    Icon(
        modifier = Modifier.size(20.dp),
        painter = painterResource(R.drawable.locked),
        contentDescription = "bağlantılı",
        tint = MaterialTheme.colorScheme.tertiary
    )
}
@Composable
fun IsApproved(news: News){
//    val color : Color
//    if (news.?){
//        color = aa_blue
//    }else{
//        color = MaterialTheme.colorScheme.tertiary
//    }
    Icon(
        modifier = Modifier.size(20.dp),
        painter = painterResource(R.drawable.approved),
        contentDescription = "bağlantılı",
        tint = MaterialTheme.colorScheme.tertiary
    )}


