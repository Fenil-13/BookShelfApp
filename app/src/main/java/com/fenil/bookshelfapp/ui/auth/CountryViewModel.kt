package com.fenil.bookshelfapp.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fenil.bookshelfapp.data.remote.data.CountryResponse
import com.fenil.bookshelfapp.data.remote.data.LocationResponse
import com.fenil.bookshelfapp.domain.repository.CountryRepository
import com.fenil.bookshelfapp.DelayAwareClickListener.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountryViewModel @Inject constructor(
    private val countryRepository: CountryRepository
) : ViewModel() {

    private val _countryData = MutableLiveData<Resource<CountryResponse?>>()
    val countryData: LiveData<Resource<CountryResponse?>> = _countryData

    private val _currentCountryLocationCode = MutableLiveData<Resource<LocationResponse?>>()
    val currentCountryLocationCode: LiveData<Resource<LocationResponse?>> = _currentCountryLocationCode

    init {
        getCountryList()
    }

    private fun getCountryList() {
        viewModelScope.launch {
            val response = countryRepository.getCountryList()
            _countryData.value = response.map {
                it?.data = it?.data?.entries?.sortedBy { it.value.country }?.associate{ it.toPair() }
                it
            }
        }
    }

    fun getCurrentCountyLocation(){
        viewModelScope.launch {
            val currentCountryCode = countryRepository.getCurrentCountryLocation()
            _currentCountryLocationCode.value = currentCountryCode
        }
    }

    fun getCurrentCountryPositionInList(countryCode: String?): Int{
        val countryList = countryData.value?.data?.data?.toList()
        return countryList?.indexOfFirst { it.first == countryCode } ?: 0
    }

    fun getCountryCodeByName(countryName: String?): String{
        val countryList = countryData.value?.data?.data?.toList()
        return countryList?.find { it.second.country == countryName }?.first.orEmpty()
    }
}