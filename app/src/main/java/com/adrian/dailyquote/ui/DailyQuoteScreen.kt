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
import retrofit2.converter.gson.GsonConverterFactory



@Composable
fun DailyQuoteScreen(modifier: Modifier = Modifier) {
    var quoteResponse by remember { mutableStateOf<List<QuoteResponse>?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) { // Fetches quote on launch
        val apiService = Retrofit.Builder()
            .baseUrl("https://api.viewbits.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(APIService::class.java)

        try {
            val response = apiService.getQuote("zenquotes/?mode=today")
            quoteResponse = response
            isLoading = false
        } catch (e: Exception) {
            isLoading = false
            // Handle error, e.g., display an error message
        }
    }

    if (isLoading) {
        CircularProgressIndicator()
    } else {
        quoteResponse?.let { quotes ->
            if (quotes.isNotEmpty()) {
                val firstQuote = quotes.firstOrNull()
                Column(modifier = modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
                    Text(text = firstQuote?.q ?: "", style = MaterialTheme.typography.titleLarge)
                    Text(text = "- ${firstQuote?.a ?: ""}", style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.height(16.dp))
                    // Add a ShareButton here if needed
                }
            } else {
                Text(text = "No quotes available", style = MaterialTheme.typography.bodyLarge)
            }
        } ?: run { // Handle no quote received
            Text(text = "Error fetching quote", style = MaterialTheme.typography.bodyLarge)
        }
    }
}
