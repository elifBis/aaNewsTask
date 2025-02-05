package com.elifbis.aataskapp.screens

import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Icon
import android.inputmethodservice.Keyboard.Row
import android.media.Image
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.twotone.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
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
import com.elifbis.aataskapp.model.NewsType
import com.elifbis.aataskapp.model.Priority
import com.elifbis.aataskapp.ui.theme.aa_blue
import com.elifbis.aataskapp.ui.theme.aa_red
import com.elifbis.aataskapp.ui.theme.aa_yellow
import java.util.Locale.Category


@Composable
fun NewsList(newsList: List<News>, navController: NavController) {
    if (newsList.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize().padding(50.dp),
                horizontalAlignment = Alignment.CenterHorizontally            ) {
                Text(text = "Henüz bir haber bulunamıyor.", style = MaterialTheme.typography.bodySmall)
                Image(
                    painter = painterResource(id = R.drawable.maymun),
                    contentDescription = ("maymun")
                )
            }

    } else {
        Column {
            SearchBarSample(newsList, navController = navController)
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(newsList) { news ->
                    NewsCard(news = news, navController = navController)
                }
            }
        }

    }
}
@Composable
fun NewsCard(news: News, navController :NavController) {
    Column {

        OutlinedCard(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondary
            ),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.tertiary),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 5.dp)
                .clickable {
                    navController.navigate(
                        "news_detail_screen/${news.newsId}"
                    )
                }
        ) {
            Column {
                PriorityColorLine(news = news)
                ReusableSpacer()
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
                        { IsRelatableIcon(news = news) }
                    )
                )
                ReusableSpacer()
                Row (){
                    ReusableSpacer()
                    ReusableSpacer()
                    NewsTitle(news = news)
                }
                ReusableSpacer()
                NewsImagesRow(news = news) // Resimlerın liste satırı
                Row (modifier = Modifier.padding(10.dp)){
                    CategoryBox(news = news)
                    Spacer(Modifier
                        .weight(1f)
                        .fillMaxHeight())
                    OtherBox(news = news)
                }// Kategori, Bülten
                ReusableHorizontalDivider()
                ReusableSpacer()
                Row(modifier = Modifier.fillMaxWidth()) {
                        ReusableSpacer()
                        Icon(
                            Icons.Outlined.LocationOn,
                            "location icon",
                            tint = MaterialTheme.colorScheme.tertiary,
                            modifier = Modifier.size(20.dp)
                        )
                        LocationText(news = news)
                        Spacer(Modifier
                            .weight(1f)
                            .fillMaxHeight())
                        DateTimeText(news = news)
                        ReusableSpacer()
                        ReusableSpacer()

                    }// Konum, Saat
                }
            ReusableSpacer()
            }
        }
    }
@Composable
fun PriorityColorLine(news: News){
    val priorityColor = news.priority.color
    HorizontalDivider(
        modifier = Modifier.padding(horizontal = 10.dp).
        fillMaxSize(),
        thickness = 4.dp,
        color = priorityColor
    )
}
@Composable
fun PriorityBox(news: News){
    val priorityColor = news.priority.color
    val priorityNumber = news.priority.number.toString()

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
}
@Composable
fun LanguageUIBox(news: News){
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
}
@Composable
fun NewsTypeIcon(news: News) {
    val newsTypeIcons = mapOf(
        NewsType.Text to R.drawable.text,
        NewsType.Video to R.drawable.video,
        NewsType.Photo to R.drawable.camera,
        NewsType.Graphic to R.drawable.graph
    )

    val newsType = NewsType.fromInt(news.newsType)
    val iconRes = newsTypeIcons[newsType] ?: R.drawable.graph

    Icon(
        modifier = Modifier.size(20.dp),
        painter = painterResource(id = iconRes),
        contentDescription = "Haber tipi ikonu",
        tint = aa_blue
    )
}
@Composable
fun IsRelatableIcon (news : News){
    val color : Color
    if (news.relations){
        color = aa_blue
    }else{
        color = MaterialTheme.colorScheme.tertiary
    }
    Icon(
        modifier = Modifier.size(20.dp),
        painter = painterResource(R.drawable.link),
        contentDescription = "bağlantılı",
        tint = color
    )
}
@Composable
fun NewsTitle(news : News){
    val priorityColor = news.priority.color
    Text(
        text = news.title,
        color = priorityColor,
        style = MaterialTheme.typography.titleSmall,
        //modifier = Modifier.padding(10.dp),
        fontWeight = FontWeight.Bold
    )
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
            .padding(4.dp)
    ) {
        Text(
            text = displayedText,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(1.dp),
            fontWeight = FontWeight.Normal
        )
    }
}
@Composable
fun OtherBox(news: News) {
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.tertiary, shape = RoundedCornerShape(8.dp))
            .padding(4.dp)
    ) {
        Text(
            text = "Genel",
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(1.dp),
            fontWeight = FontWeight.Normal
        )
    }
}
@Composable
fun LocationText(news : News){
    Text(
        text = news.location,
        color = Color.White,
        style = MaterialTheme.typography.titleSmall,
        //modifier = Modifier.padding(5.dp),
        fontWeight = FontWeight.Normal
    )
}
@Composable
fun DateTimeText(news : News){
    Text(
        text = news.datetime,
        color = Color.White,
        style = MaterialTheme.typography.titleSmall,
        //modifier = Modifier.padding(5.dp),
        fontWeight = FontWeight.Normal
    )
}
@Composable
fun ReusableSpacer(){
    Spacer(modifier = Modifier.size(7.dp))
}
@Composable
fun ReusableHorizontalDivider(){
    HorizontalDivider(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 10.dp), thickness = 1.dp)
}
@Composable
fun ReusableRowList(rowItems: List<@Composable () -> Unit>) {
    Row(
        modifier = Modifier
            .padding(8.dp),
        //verticalAlignment = Alignment.CenterVertically
    ) {
        rowItems.forEachIndexed { index, composable ->
            composable()
            if (index != rowItems.lastIndex) {
                ReusableSpacer()
            }
        }
    }
}






