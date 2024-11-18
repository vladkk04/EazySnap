package com.arkul.eazysnap.domain.model

import androidx.compose.ui.graphics.painter.Painter
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.Resource

data class Country(
    val name: String,
    val code: Int,
    val flag: DrawableResource,
)
