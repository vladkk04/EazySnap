package com.arkul.eazysnap.data

import com.arkul.eazysnap.domain.model.Country

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class CountryManager {
    fun getCountries(): Array<Country>
}