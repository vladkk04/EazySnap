package com.arkul.eazysnap.data

import android.content.Context
import android.util.Log
import com.arkul.eazysnap.domain.model.Country
import com.google.i18n.phonenumbers.PhoneNumberUtil
import eazysnap.composeapp.generated.resources.Res
import eazysnap.composeapp.generated.resources.allDrawableResources
import eazysnap.composeapp.generated.resources.ic_ae
import eazysnap.composeapp.generated.resources.ic_ar
import eazysnap.composeapp.generated.resources.ic_arab
import eazysnap.composeapp.generated.resources.ic_ua
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.InternalResourceApi
import org.jetbrains.compose.resources.Resource
import org.jetbrains.compose.resources.ResourceItem
import org.jetbrains.compose.resources.getResourceUri
import org.jetbrains.compose.resources.painterResource
import java.util.Locale

actual class CountryManager(
    private val context: Context
) {
    @OptIn(ExperimentalResourceApi::class)
    actual fun getCountries(): Array<Country> {
        val instance = PhoneNumberUtil.getInstance()
        val allRegions = instance.supportedRegions

        val flagFallback = Res.drawable.ic_ua

        return allRegions.sortedBy { it }.map { region ->
            val countryCode = instance.getCountryCodeForRegion(region)
            val countryName = Locale("", region).displayCountry

            val flagResource = Res.allDrawableResources["ic_${region.lowercase()}"] ?: flagFallback

            Country(
                name = countryName,
                code = countryCode,
                flag = flagResource
            )
        }.toTypedArray()
    }
}
