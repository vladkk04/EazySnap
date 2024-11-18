package com.arkul.eazysnap.data

import com.arkul.eazysnap.domain.model.Country

expect class CountryManager {
    fun getCountries(): Array<Country>
}