package com.jelly.ai.ui.screen

import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MeScreen(
    paddingValues: PaddingValues,
) {
    val refreshState = rememberPullToRefreshState()
    val context = LocalContext.current
    val customTabsIntent = CustomTabsIntent.Builder().build()

}