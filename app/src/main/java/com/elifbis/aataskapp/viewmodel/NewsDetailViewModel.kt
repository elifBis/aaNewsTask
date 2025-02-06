package com.elifbis.aataskapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elifbis.aataskapp.model.News
import com.elifbis.aataskapp.model.NewsDetailModel
import com.elifbis.aataskapp.model.toNewsDetailModel
import com.elifbis.aataskapp.service.NewsApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewsDetailViewModel : ViewModel() {
    private val BASE_URL = "https://raw.githubusercontent.com/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(NewsApi::class.java)

    // Haber listesi için StateFlow
    private val _newsList = MutableStateFlow<List<News>>(emptyList())
    val newsList: StateFlow<List<News>> = _newsList.asStateFlow()

    // Haber detayları için StateFlow
    private val _newsDetailList = MutableStateFlow<List<NewsDetailModel>>(emptyList())
    val newsDetailList: StateFlow<List<NewsDetailModel>> = _newsDetailList.asStateFlow()

    // Haber Listesini Çekme
    fun getNews() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val data = retrofit.getData()
                println("API'den Gelen Haber Listesi: ${data.newsList}")
                _newsList.value = data.newsList
            } catch (e: Exception) {
                e.printStackTrace()
                println("Hata: ${e.message}")
                _newsList.value = emptyList()
            }
        }
    }

    fun getNewsDetails() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                Log.d("NewsDetailViewModel", "getNewsDetails() çağrıldı!") // Çağrılıyor mu?
                val response = retrofit.getNewsDetails()
                Log.d("NewsDetailViewModel", "API Yanıtı: ${response.items}")

                // ✅ Dönüştürmeden önce veri kontrolü yapalım:
                if (response.items.isEmpty()) {
                    Log.e("NewsDetailViewModel", "Hata: API'den boş liste geldi!")
                    return@launch
                }

                response.items.forEach {
                    Log.d("NewsDetailViewModel", "Dönüştürme Öncesi - newsId: ${it.newsId}")
                }

                val newsDetailItems = response.items.map { it.toNewsDetailModel() }
                _newsDetailList.value = newsDetailItems

                Log.d("NewsDetailViewModel", "Haber detayları alındı: ${newsDetailItems.size}")
            } catch (e: Exception) {
                Log.e("NewsDetailViewModel", "Hata: ${e.message}")
                _newsDetailList.value = emptyList()
            }
        }
    }



    // ID'ye Göre Tekil Haber Detayı Alma
    fun getNewsDetailById(newsID: Long?): NewsDetailModel? {
        if (newsID == null) return null
        return _newsDetailList.value.firstOrNull { it.newsID == newsID }
    }
}
