package com.fenil.bookshelfapp.data.remote.interfaces

import com.fenil.bookshelfapp.data.remote.data.CountryResponse
import com.fenil.bookshelfapp.data.remote.data.LocationResponse
import retrofit2.Call
import retrofit2.http.GET

interface CountryService {
    @GET("data/v1/countries")
    fun getCountries(): Call<CountryResponse?>

    @GET("json")
    fun getCurrentCurrentIP() : Call<LocationResponse?>
}