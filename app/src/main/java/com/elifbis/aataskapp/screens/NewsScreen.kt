package com.elifbis.aataskapp.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elifbis.aataskapp.model.News
import com.elifbis.aataskapp.model.Priority
import com.elifbis.aataskapp.ui.theme.aa_blue
import com.elifbis.aataskapp.ui.theme.aa_red
import com.elifbis.aataskapp.ui.theme.aa_yellow


@Composable
fun NewsList(newsList: List<News>) {
    if (newsList.isEmpty()) {
        Text(
            text = "Henüz bir haber bulunmuyor.",
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            style = MaterialTheme.typography.displaySmall
        )
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(newsList) { news ->
                NewsCard(news = news)
            }
        }
    }
}

//fun OutlinedCardExample() {
//    OutlinedCard(
//        colors = CardDefaults.cardColors(
//            containerColor = MaterialTheme.colorScheme.surface,
//        ),
//        border = BorderStroke(1.dp, Color.Black),
//        modifier = Modifier
//            .size(width = 240.dp, height = 100.dp)
//    ) {
//        Text(
//            text = "Outlined",
//            modifier = Modifier
//                .padding(16.dp),
//            textAlign = TextAlign.Center,
//        )
//    }
//}

@Composable
fun NewsCard(news: News) {
    Column {

        val priorityColor = news.priority.color
        val priorityNumber = news.priority.number.toString()

        OutlinedCard(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondary
            ),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.tertiary),
            modifier = Modifier.fillMaxWidth().padding(3.dp)
        ) {
            Column {
                HorizontalDivider(
                    modifier = Modifier.padding(1.dp),
                    thickness = 3.dp,
                    color = priorityColor
                )

                Row(modifier = Modifier.align(Alignment.Start)) {
                    Text(
                        text = priorityNumber,
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(2.dp)
                            .background(color = priorityColor),
                        fontWeight = FontWeight.Normal
                    )
                    Text(
                        text = "TR",//DÜZELTMEN GEREK
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(2.dp)
                            .background(color = priorityColor),
                        fontWeight = FontWeight.Normal
                    )
                    VerticalDivider(
                        modifier = Modifier.padding(1.dp),
                        thickness = 1.dp,
                        color = MaterialTheme.colorScheme.tertiary
                    )
                    if (news.newsType.toInt() == 2) {//
                        Icon(Icons.Outlined.Star, "yıldız", tint = aa_blue)
                    }
                    else{
                        Icon(Icons.Rounded.AddCircle, "circle", tint = aa_blue)
                    }
                    Icon(Icons.Outlined.Share, "yıldız", tint = aa_blue)


                }

                Text(
                    text = news.title,
                    color = priorityColor,
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.padding(2.dp),
                    fontWeight = FontWeight.Normal
                )

                HorizontalDivider(thickness = 1.dp)

                Row(modifier = Modifier.fillMaxWidth()) {
                    Icon(
                        Icons.Outlined.LocationOn,
                        "location icon",
                        tint = aa_blue,
                        modifier = Modifier.padding(3.dp)
                    )
                    Text(
                        text = news.location,
                        color = Color.White,
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier.padding(2.dp),
                        fontWeight = FontWeight.Normal
                    )
                }
            }
        }
    }
}



