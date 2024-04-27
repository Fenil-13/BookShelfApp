package com.fenil.bookshelfapp.data.remote.data

import com.google.gson.annotations.SerializedName

data class CountryResponse(
    @SerializedName("status")
    val status: String?,
    @SerializedName("statusCode")
    val statusCode: Int?,
    @SerializedName("version")
    val version: String?,
    @SerializedName("access")
    val access: String?,
    @SerializedName("data")
    val data: Map<String, CountryInfo>?
)

data class CountryInfo(
    @SerializedName("country")
    val country: String?,
    @SerializedName("region")
    val region: String?
)