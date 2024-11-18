package com.arkul.eazysnap

import androidx.compose.runtime.Composable
import com.arkul.eazysnap.data.CountryManager
import com.arkul.eazysnap.presentation.screen.auth.AuthScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(
    countryManager: CountryManager
) {
    AuthScreen(countryManager)
}