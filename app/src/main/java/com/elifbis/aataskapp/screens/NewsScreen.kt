package com.elifbis.aataskapp.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.elifbis.aataskapp.R
import com.elifbis.aataskapp.model.News
import com.elifbis.aataskapp.model.Priority
import com.elifbis.aataskapp.ui.theme.aa_blue
import com.elifbis.aataskapp.ui.theme.aa_red
import com.elifbis.aataskapp.ui.theme.aa_yellow
import java.util.Locale.Category


@Composable
fun NewsList(newsList: List<News>, navController: NavController) {
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
                NewsCard(news = news, navController = navController)
            }
        }
    }
}

@Composable
fun NewsCard(news: News, navController :NavController) {
    Column {
        val priorityColor = news.priority.color
        val priorityNumber = news.priority.number.toString()

        OutlinedCard(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondary
            ),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.tertiary),
            modifier = Modifier
                .fillMaxWidth()
                .padding(3.dp)
                .clickable {
                    navController.navigate(
                        "news_detail_screen/${news.newsId}"
                    )
                }
        ) {
            Column {
                HorizontalDivider(
                    modifier = Modifier.padding(1.dp),
                    thickness = 3.dp,
                    color = priorityColor
                )
                Row(
                    modifier = Modifier
                        .align(Alignment.Start)
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .background(
                                color = priorityColor,
                                shape = RoundedCornerShape(2.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = priorityNumber,
                            color = Color.White,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier
                                .background(color = priorityColor),
                            fontWeight = FontWeight.Normal
                        )
                    }

                    Spacer(modifier = Modifier.size(10.dp))

                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .border(
                                shape = RoundedCornerShape(2.dp),
                                border = BorderStroke(1.dp, MaterialTheme.colorScheme.tertiary),
                            )
                            .background(
                                color = MaterialTheme.colorScheme.secondary,
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = news.languageUI.toString(),
                            color = Color.White,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Normal
                        )}

                        VerticalDivider(
                            modifier = Modifier.padding(10.dp),
                            thickness = 10.dp,
                            color = MaterialTheme.colorScheme.tertiary
                        )


                }

                    Text(
                        text = news.title,
                        color = priorityColor,
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier.padding(2.dp),
                        fontWeight = FontWeight.Normal
                    )
                NewsImagesRow(news = news)
                CategoryBox(news = news)


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
@Composable
fun NewsImagesRow(news: News) {
    val imageUrls = news.photos.split(",").filter { it.isNotBlank() }

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 8.dp)
    ) {
        items(imageUrls) { url ->
            Card(
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .padding(4.dp)
                    .size(60.dp)
            ) {
                AsyncImage(
                    model = "https:$url",
                    //model = R.drawable.camera,
                    contentDescription = "News Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Composable
fun CategoryBox(news: News) {
    val categoryList: List<String> = news.categories
    val hasGeneral = categoryList.contains("Genel")

    val displayedText: String = if (hasGeneral) {
        "Genel" + if (categoryList.size > 1) " +${categoryList.size - 1}" else ""
    } else {
        val visibleCategories = categoryList.take(3).joinToString(", ")
        val additionalCount = categoryList.size - 3
        visibleCategories + if (additionalCount > 0) " +$additionalCount" else ""
    }

    Box(
        modifier = Modifier
            .background(aa_blue, shape = RoundedCornerShape(8.dp))
            .padding(6.dp)
    ) {
        Text(
            text = displayedText,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(2.dp),
            fontWeight = FontWeight.Normal
        )
    }
}






