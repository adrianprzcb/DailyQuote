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
import androidx.compose.runtime.remember
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import com.adrian.dailyquote.APIService
import com.adrian.dailyquote.QuoteResponse
import retrofit2.Retrofit

@Composable
fun DailyQuoteScreen(modifier: Modifier = Modifier) {
    val quoteResponse: QuoteResponse? = remember(currentMode) {
        // Fetch quote using Retrofit
        val apiService = Retrofit.Builder()
            .baseUrl("https://api.viewbits.com/v1/")
            .build()
            .create(APIService::class.java)
        val response = apiService.getQuote("zenquotes/?mode=$currentMode")
        response.body()
    }

    if (quoteResponse != null) {
        Column(modifier = modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
            Text(text = quoteResponse.quote, style = MaterialTheme.typography.h5)
            Text(text = "- ${quoteResponse.author}", style = MaterialTheme.typography.subtitle1)
            Spacer(modifier = Modifier.height(16.dp))
            ShareButton(text = quoteResponse.quote) // Pass quote for sharing
        }
    } else {
        // Show loading indicator while fetching quote
        CircularProgressIndicator()
    }
}