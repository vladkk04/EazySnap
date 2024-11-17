package com.arkul.eazysnap.domain.model

import org.jetbrains.compose.resources.DrawableResource

data class Country(
    val name: String,
    val code: String,
    val flag: DrawableResource,
)
