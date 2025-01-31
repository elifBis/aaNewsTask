package com.elifbis.aataskapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.elifbis.aataskapp.screens.NewsDetail
import com.elifbis.aataskapp.screens.NewsList
import com.elifbis.aataskapp.ui.theme.AATaskAppTheme
import com.elifbis.aataskapp.viewmodel.NewsViewModel

class MainActivity : ComponentActivity() {
    private val viewmodel : NewsViewModel by viewModels<NewsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            AATaskAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()
                .background(MaterialTheme.colorScheme.primary)
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)){
                        NavHost(navController = navController,
                            startDestination = "news_list_screen"){
                            composable("news_list_screen") {
                                viewmodel.getNews()
                                NewsList(newsList = viewmodel.newsList.value, navController = navController )
                            }
                            composable("news_detail_screen/{news.newsId}") { backStackEntry ->
                                val newsID = backStackEntry.arguments?.getString("news.newsId")?.toLongOrNull()

                                if (newsID != null) {
                                    val selectedNews = viewmodel.getNewsById(newsID)
                                    selectedNews?.let {
                                        NewsDetail(news = it)
                                    }
                                } else {
                                    Column(modifier = Modifier.align(Alignment.Center)
                                    ) {
                                        Text(text = "Haber bulunamadı.", style = MaterialTheme.typography.bodySmall)
                                        Image(
                                            painter = painterResource(id = R.drawable.maymun),
                                            contentDescription = ("maymun")
                                        )
                                    }

                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


