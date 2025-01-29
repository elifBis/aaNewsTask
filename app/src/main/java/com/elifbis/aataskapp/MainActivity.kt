package com.elifbis.aataskapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.elifbis.aataskapp.screens.NewsList
import com.elifbis.aataskapp.ui.theme.AATaskAppTheme
import com.elifbis.aataskapp.viewmodel.NewsViewModel

class MainActivity : ComponentActivity() {
    private val viewmodel : NewsViewModel by viewModels<NewsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AATaskAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()
                .background(MaterialTheme.colorScheme.primary)
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)){
                        viewmodel.getNews()
                        NewsList(newsList = viewmodel.newsList.value )
                    }
                }
            }
        }
    }
}


