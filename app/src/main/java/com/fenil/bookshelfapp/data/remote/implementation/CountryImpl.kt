package com.fenil.bookshelfapp.data.remote.implementation

import com.fenil.bookshelfapp.data.remote.interfaces.CountryService
import com.fenil.bookshelfapp.data.remote.data.CountryResponse
import com.fenil.bookshelfapp.data.remote.data.LocationResponse
import com.fenil.bookshelfapp.di.CountryServiceApi
import com.fenil.bookshelfapp.di.LocationServiceApi
import com.fenil.bookshelfapp.domain.repository.CountryRepository
import com.fenil.bookshelfapp.DelayAwareClickListener.Resource
import com.fenil.bookshelfapp.DelayAwareClickListener.asResource

class CountryImpl(
    @CountryServiceApi private val countryService: CountryService,
    @LocationServiceApi private val locationService: CountryService,
) : CountryRepository {

    override suspend fun getCountryList(): Resource<CountryResponse?> {
        return countryService.getCountries().asResource()
    }

    override suspend fun getCurrentCountryLocation(): Resource<LocationResponse?> {
        return locationService.getCurrentCurrentIP().asResource()
    }

}
