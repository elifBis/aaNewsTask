package com.elifbis.aataskapp.model

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import com.elifbis.aataskapp.ui.theme.aa_blue
import com.elifbis.aataskapp.ui.theme.aa_red
import com.elifbis.aataskapp.ui.theme.aa_yellow
import com.google.gson.annotations.SerializedName

data class NewsListModel (
    val total: Long,
    val facets: Facets,
    val newsList: List<News>
)

class Facets()

data class News (
    val newsId: Long,
    val newsType: Long,
    val title: String,
    val spot: String,
    val categories: List<String>,
    val priority: Priority,
    val language: Language,
    val languageUI: LanguageUI,
    val location: String,
    val datetime: String,
    val relations: Boolean,
    val packages: List<String>,
    val photos: String,
    val isPublic: Boolean,
    val photoIDS: String
)

enum class Priority(val number: Int, val color: Color) {
    @SerializedName("1 Flaş") Flaş(1, aa_red),
    @SerializedName("2 Acil") Acil(2, aa_blue),
    @SerializedName("3 Önemli") Önemli(3, aa_yellow),
    @SerializedName("4 Rutin") Rutin(4, Color.Gray),
    @SerializedName("5 Özel") Özel(5, Color.Green)
}

enum class Language {
    Arapça,
    Türkçe
}

enum class LanguageUI {
    Ar,
    Tr
}
