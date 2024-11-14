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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.adrian.dailyquote.APIService
import com.adrian.dailyquote.QuoteResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Response
import androidx.compose.foundation.layout.padding

@Composable
fun DailyQuoteScreen(modifier: Modifier = Modifier) {
    // State to hold the quote data and loading status
    val quoteResponseState = remember { mutableStateOf<Result<List<QuoteResponse>>>(Result.loading()) }

    // Fetching the quote when the Composable is first launched
    LaunchedEffect(Unit) {
        // Create Retrofit instance
        val apiService = Retrofit.Builder()
            .baseUrl("https://api.viewbits.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(APIService::class.java)

        try {
            // Make the network request (suspend function)
            val response: Response<List<QuoteResponse>> = apiService.getQuote("zenquotes/?mode=today")

            if (response.isSuccessful) {
                // Set the result with the data
                quoteResponseState.value = Result.success(response.body() ?: emptyList())
            } else {
                // If response is not successful, set the result with an error
                quoteResponseState.value = Result.error("Failed to fetch quote")
            }
        } catch (e: Exception) {
            // Handle exceptions (network failure, etc.)
            quoteResponseState.value = Result.error("Error: ${e.localizedMessage}")
        }
    }

    // Extract data from state
    when (val result = quoteResponseState.value) {
        is Result.Loading -> {
            // Show a loading indicator
            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        }
        is Result.Success -> {
            // Display the quote
            val firstQuote = result.data?.firstOrNull()
            Column(modifier = modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                if (firstQuote != null) {
                    Text(
                        text = firstQuote.q ?: "No quote found",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = "- ${firstQuote.a ?: "Unknown Author"}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                } else {
                    Text(text = "No quotes available", style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
        is Result.Error -> {
            // Handle the error
            Text(text = result.message ?: "Unknown error", style = MaterialTheme.typography.bodyLarge)
        }
    }
}

// Utility Result class to handle different states
sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val message: String?) : Result<Nothing>()
    object Loading : Result<Nothing>()

    companion object {
        fun <T> success(data: T): Result<T> = Success(data)
        fun error(message: String?): Result<Nothing> = Error(message)
        fun loading(): Result<Nothing> = Loading
    }
}
