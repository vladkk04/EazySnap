package com.arkul.eazysnap

import androidx.compose.ui.window.ComposeUIViewController
import com.arkul.eazysnap.data.CountryManager

fun MainViewController() = ComposeUIViewController { App(CountryManager()) }