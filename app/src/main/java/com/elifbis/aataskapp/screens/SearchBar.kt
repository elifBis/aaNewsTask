package com.elifbis.aataskapp.screens
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarSample(searchQuery: String, onQueryChange: (String) -> Unit) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    Box(Modifier.fillMaxWidth()) {
        SearchBar(
            modifier = Modifier.align(Alignment.TopCenter),
            inputField = {
                SearchBarDefaults.InputField(
                    modifier = Modifier.border(BorderStroke(1.dp, MaterialTheme.colorScheme.tertiary)),
                    query = searchQuery,
                    onQueryChange = onQueryChange,
                    onSearch = { expanded = false }, // Burada expanded kapatıyoruz
                    expanded = false,
                    onExpandedChange = { expanded = false },
                    placeholder = { Text("Haberlerde ara...") }
                )
            },
            expanded = expanded,
            onExpandedChange = { expanded = it },
        ) {}
    }
}
