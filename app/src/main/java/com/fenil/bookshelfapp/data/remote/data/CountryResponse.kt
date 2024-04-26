package com.fenil.bookshelfapp.data.remote.data

data class CountryResponse(
    val status: String?,
    val statusCode: Int?,
    val version: String?,
    val access: String?,
    val data: Map<String, CountryInfo>?
)

data class CountryInfo(
    val country: String?,
    val region: String?
)