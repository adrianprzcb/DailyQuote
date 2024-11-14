package com.adrian.dailyquote.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    var currentMode by remember { mutableStateOf("daily") }

    Column(modifier = Modifier.fillMaxSize()) {
        when (currentMode) {
            "daily" -> DailyQuoteScreen(currentMode = currentMode)
            // Add other mode composables here
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            Switch(
                checked = currentMode == "daily",
                onCheckedChange = { currentMode = if (it) "daily" else "random" } // Change "otherMode" to your actual mode name
            )
        }
    }
}
