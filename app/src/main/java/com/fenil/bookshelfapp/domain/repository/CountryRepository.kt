package com.fenil.bookshelfapp.domain.repository

import com.fenil.bookshelfapp.data.remote.data.CountryResponse
import com.fenil.bookshelfapp.data.remote.data.LocationResponse
import com.fenil.bookshelfapp.DelayAwareClickListener.Resource

interface CountryRepository {
    suspend fun getCountryList(): Resource<CountryResponse?>

    suspend fun getCurrentCountryLocation(): Resource<LocationResponse?>
}