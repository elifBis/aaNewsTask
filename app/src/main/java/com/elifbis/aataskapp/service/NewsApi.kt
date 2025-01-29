package com.elifbis.aataskapp.service

import com.elifbis.aataskapp.model.News // NewsListModel'in içinde ilk böyle denedim.
import com.elifbis.aataskapp.model.NewsListModel
import retrofit2.http.GET

interface NewsApi {
    @GET("elifBis/aanewsfakeapi/refs/heads/main/news_list.json")
    suspend fun getData(): NewsListModel
}