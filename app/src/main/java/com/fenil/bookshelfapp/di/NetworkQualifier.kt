package com.fenil.bookshelfapp.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class CountryRetrofitClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LocationRetrofitClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BookRetrofitClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class CountryServiceApi

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LocationServiceApi

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class PlainOkHTTPClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class UnsafeOkHTTPClient