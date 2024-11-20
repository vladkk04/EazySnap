package com.arkul.eazysnap.data

import com.arkul.eazysnap.domain.model.Country
import eazysnap.composeapp.generated.resources.Res
import eazysnap.composeapp.generated.resources.ic_ua

actual class CountryManager {
    actual fun getCountries(): Array<Country> {
        return Array(10) {Country("$it", 32, Res.drawable.ic_ua)}
    }
}