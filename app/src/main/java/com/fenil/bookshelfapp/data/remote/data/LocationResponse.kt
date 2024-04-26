package com.fenil.bookshelfapp.data.remote.data

import com.google.gson.annotations.SerializedName

data class LocationResponse(
    @SerializedName("countryCode")
    val countryCode: String? = null
)
