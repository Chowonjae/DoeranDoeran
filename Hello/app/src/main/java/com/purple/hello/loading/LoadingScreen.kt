package com.purple.hello.loading

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun LoadingScreen() {
    Text(
        modifier = Modifier.fillMaxSize(),
        text = "로딩중...",
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.titleLarge,
    )
}
