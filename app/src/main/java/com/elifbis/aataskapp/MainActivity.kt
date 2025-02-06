package com.elifbis.aataskapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.elifbis.aataskapp.model.NewsDetailModel
import com.elifbis.aataskapp.screens.NewsDetail
import com.elifbis.aataskapp.screens.NewsDetailScreen
import com.elifbis.aataskapp.screens.NewsList
import com.elifbis.aataskapp.ui.theme.AATaskAppTheme
import com.elifbis.aataskapp.viewmodel.NewsDetailViewModel
import com.elifbis.aataskapp.viewmodel.NewsViewModel

class MainActivity : ComponentActivity() {
    private val viewmodel: NewsViewModel by viewModels()
    private val detailviewmodel: NewsDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            AATaskAppTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.primary)
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        NavHost(
                            navController = navController,
                            startDestination = "news_list_screen"
                        ) {
                            // ✅ Haber Listesi Ekranı
                            composable("news_list_screen") {
                                viewmodel.getNews()
                                detailviewmodel.getNewsDetails()

                                val newsList = viewmodel.newsList.value
                                val newsDetailListState = detailviewmodel.newsDetailList.collectAsStateWithLifecycle()
                                val newsDetailList = newsDetailListState.value

                                NewsList(
                                    newsList = newsList,
                                    navController = navController,
                                    newsDetailList = newsDetailList
                                )
                            }

                            // ✅ Haber Detay Ekranı
                            composable("news_detail_screen/{newsId}") { backStackEntry ->
                                val newsID = backStackEntry.arguments?.getString("newsId")?.toLongOrNull()
                                Log.d("NavigationDebug", "Gelen newsId: $newsID")

                                if (newsID != null) {
                                    NewsDetailScreen(navController = navController, newsId = newsID, viewModel = detailviewmodel)
                                } else {
                                    Column(
                                        modifier = Modifier.fillMaxSize(),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        Text(text = "Haber bulunamadı.", style = MaterialTheme.typography.bodyLarge)
                                        Image(
                                            painter = painterResource(id = R.drawable.maymun),
                                            contentDescription = "Hata Resmi"
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

