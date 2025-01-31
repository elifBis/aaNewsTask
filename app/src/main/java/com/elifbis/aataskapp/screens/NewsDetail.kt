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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.elifbis.aataskapp.R
import com.elifbis.aataskapp.model.News
import com.elifbis.aataskapp.ui.theme.aa_blue

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
            Spacer(Modifier
                .weight(1f)
                .fillMaxHeight())
            Column(
                horizontalAlignment = AbsoluteAlignment.Right
            ){
                DateTimeText(news = news)
                LocationText(news = news)
            }
        }
        NewsTitle(news = news)
        ReusableHorizantalDivider()
    }
}
@Composable
fun ImageGallery(news: News) {
    val imageUrls = news.photos.split(",").map { it.trim() }.filter { it.isNotEmpty() }
    val pagerState = rememberPagerState(pageCount = { imageUrls.size })

    if (imageUrls.isNotEmpty()) {
        HorizontalPager(state = pagerState) { page ->
            Column(
//                modifier = Modifier
//                    .size(350.dp)
//                    .background(Color.Black),
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
                )}


                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Sayfa: ${page + 1} / ${imageUrls.size}",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    } else {
        Text(
            text = "Görüntü bulunamadı",
            color = Color.Gray,
            fontSize = 16.sp,
            modifier = Modifier.padding(16.dp)
        )
    }
}
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
    )
}