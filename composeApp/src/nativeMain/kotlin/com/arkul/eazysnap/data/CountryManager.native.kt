package com.arkul.eazysnap.data

import com.arkul.eazysnap.domain.model.Country

actual class CountryManager {
    actual fun getCountries(): Array<Country> {
        return emptyArray()
    }
}