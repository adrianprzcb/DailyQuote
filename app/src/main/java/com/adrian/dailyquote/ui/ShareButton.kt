package com.adrian.dailyquote.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable

@Composable
fun ShareButton(text: String) {
    IconButton(onClick = { /* Share intent code */ }) {
        Icon(Icons.Default.Share, contentDescription = "Share quote")
    }
}