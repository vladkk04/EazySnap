package com.arkul.eazysnap.presentation.screen.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAbsoluteAlignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.unit.dp
import com.arkul.eazysnap.data.CountryManager
import com.arkul.eazysnap.domain.model.Country
import com.arkul.eazysnap.presentation.screen.auth.components.PhoneNumberTextField
import eazysnap.composeapp.generated.resources.Res
import eazysnap.composeapp.generated.resources.ic_ua

@Composable
fun AuthScreen(
    countryManager: CountryManager,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = BiasAlignment(0f, -0.4f),
        modifier = Modifier
            .fillMaxSize()
    ) {
        PhoneNumberTextField(
            countryManager.getCountries(),
            modifier = Modifier
                .padding(horizontal = 32.dp)
                .fillMaxWidth()
        )

        Button(
            onClick = {

            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(32.dp)
                .fillMaxWidth()
        ) {
            Text("Continue")
        }
    }
}