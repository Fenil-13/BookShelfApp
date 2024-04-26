package com.fenil.bookshelfapp.di

import com.fenil.bookshelfapp.data.local.CountryImpl
import com.fenil.bookshelfapp.data.local.UserRepositoryImpl
import com.fenil.bookshelfapp.domain.repository.CountryRepository
import com.fenil.bookshelfapp.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class BindModules {

    @Binds
    abstract fun provideUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    @Binds
    abstract fun provideCountryRepository(countryImpl: CountryImpl): CountryRepository
}