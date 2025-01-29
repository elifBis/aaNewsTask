package com.elifbis.aataskapp.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elifbis.aataskapp.model.News
import com.elifbis.aataskapp.service.NewsApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewsViewModel: ViewModel() {
    //https://raw.githubusercontent.com/elifBis/aanewsfakeapi/refs/heads/main/news_list.json
    private val BASE_URL = "https://raw.githubusercontent.com/"

    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(NewsApi::class.java)
    val newsList = mutableStateOf<List<News>>(listOf())

    fun getNews(){
        viewModelScope.launch(Dispatchers.IO) {
            println(newsList.value)
            try {
                val data = retrofit.getData() // NewsListModel geliyor
                println("API'den Gelen Veri: ${data.newsList}") // Debug için
                newsList.value = data.newsList // List<News> set ediliyor
            } catch (e: Exception) {
                e.printStackTrace()
                println("Hata Mesajı: ${e.message}")
                newsList.value = emptyList()
            }
        }
    }

}