package com.fenil.bookshelfapp.data.remote.implementation

import com.fenil.bookshelfapp.data.remote.interfaces.CountryService
import com.fenil.bookshelfapp.data.remote.data.CountryResponse
import com.fenil.bookshelfapp.data.remote.data.LocationResponse
import com.fenil.bookshelfapp.di.CountryServiceApi
import com.fenil.bookshelfapp.di.LocationServiceApi
import com.fenil.bookshelfapp.domain.repository.CountryRepository
import com.fenil.bookshelfapp.DelayAwareClickListener.Resource
import com.fenil.bookshelfapp.DelayAwareClickListener.asResource
import com.fenil.bookshelfapp.di.NetworkHelper
import javax.inject.Inject

class CountryImpl @Inject constructor(
    @CountryServiceApi private val countryService: CountryService,
    @LocationServiceApi private val locationService: CountryService,
    private val networkHelper: NetworkHelper,
) : CountryRepository {

    override suspend fun getCountryList(): Resource<CountryResponse?> {
        if (!networkHelper.isNetworkConnected()){
            return Resource.noInternet()
        }
        return countryService.getCountries().asResource()
    }

    override suspend fun getCurrentCountryLocation(): Resource<LocationResponse?> {
        if (!networkHelper.isNetworkConnected()){
            return Resource.noInternet()
        }
        return locationService.getCurrentCurrentIP().asResource()
    }

}
