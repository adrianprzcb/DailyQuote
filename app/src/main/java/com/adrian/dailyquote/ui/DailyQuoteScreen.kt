package com.adrian.dailyquote.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import com.adrian.dailyquote.APIService
import com.adrian.dailyquote.QuoteResponse
import retrofit2.Retrofit


@Composable
fun DailyQuoteScreen(modifier: Modifier = Modifier, currentMode: String) {
    var quoteResponse by remember { mutableStateOf<QuoteResponse?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(currentMode) {
        val apiService = Retrofit.Builder()
            .baseUrl("https://api.viewbits.com/v1/")
            .build()
            .create(APIService::class.java)
        val response = apiService.getQuote("zenquotes/?mode=$currentMode")
        quoteResponse = response.body()
        isLoading = false
    }

    if (isLoading) {
        CircularProgressIndicator()
    } else {
        quoteResponse?.let {
            Column(modifier = modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
                Text(text = it.quote, style = MaterialTheme.typography.bodyLarge)
                Text(text = "- ${it.author}", style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(16.dp))
                ShareButton(text = it.quote) // Pass quote for sharing
            }
        }
    }
}