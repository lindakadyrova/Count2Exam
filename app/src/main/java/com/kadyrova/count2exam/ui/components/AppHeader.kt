package com.kadyrova.count2exam.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AppHeader() {
    Row(
        modifier = Modifier

            .padding(
                top = 50.dp,
                start = 20.dp
            ),

    ) {
        Logo(
            modifier = Modifier.size(100.dp)
        )
    }
}