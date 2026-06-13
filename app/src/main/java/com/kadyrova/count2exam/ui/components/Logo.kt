package com.kadyrova.count2exam.ui.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.kadyrova.count2exam.R

@Composable
fun Logo(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(R.drawable.count2exam),
        contentDescription = "Logo",
        modifier = modifier
    )
}