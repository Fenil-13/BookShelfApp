package com.fenil.bookshelfapp.di

import android.content.Context
import androidx.room.Room
import com.fenil.bookshelfapp.data.remote.implementation.CountryImpl
import com.fenil.bookshelfapp.data.local.DATABASE_NAME
import com.fenil.bookshelfapp.data.local.UserDao
import com.fenil.bookshelfapp.data.local.UserDatabase
import com.fenil.bookshelfapp.data.local.UserRepositoryImpl
import com.fenil.bookshelfapp.data.remote.BOOK_BASE_URL
import com.fenil.bookshelfapp.data.remote.interfaces.BookService
import com.fenil.bookshelfapp.data.remote.COUNTRY_BASE_URL
import com.fenil.bookshelfapp.data.remote.CURRENT_LOCATION_BASE_URL
import com.fenil.bookshelfapp.data.remote.implementation.BookImpl
import com.fenil.bookshelfapp.data.remote.interfaces.CountryService
import com.fenil.bookshelfapp.data.remote.utils.UnsafeOkHttpClient.getUnsafeRetrofitOkHttpClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideGsonConvertorFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @CountryRetrofitClient
    @Provides
    fun getCountryRetrofitClient(gsonConverterFactory: GsonConverterFactory): Retrofit {
        return Retrofit.Builder()
            .baseUrl(COUNTRY_BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @CountryServiceApi
    @Provides
    fun getCountryService(@CountryRetrofitClient retrofit: Retrofit): CountryService {
        return retrofit.create(CountryService::class.java)
    }

    @LocationRetrofitClient
    @Provides
    fun getCurrentLocationRetrofitClient(gsonConverterFactory: GsonConverterFactory): Retrofit {
        return Retrofit.Builder()
            .baseUrl(CURRENT_LOCATION_BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @LocationServiceApi
    @Provides
    fun getLocationService(@LocationRetrofitClient retrofit: Retrofit): CountryService {
        return retrofit.create(CountryService::class.java)
    }

    @BookRetrofitClient
    @Provides
    fun getBookRetrofitClient(gsonConverterFactory: GsonConverterFactory): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BOOK_BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .client(getUnsafeRetrofitOkHttpClient())
            .build()
    }

    @Provides
    fun getBookService(@BookRetrofitClient retrofit: Retrofit): BookService {
        return retrofit.create(BookService::class.java)
    }

    @Provides
    fun provideUserDatabase(@ApplicationContext context: Context): UserDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            UserDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    @Provides
    fun provideUserDao(userDatabase: UserDatabase): UserDao {
        return userDatabase.userDao()
    }

    @Provides
    fun provideUserImplementation(userDao: UserDao): UserRepositoryImpl {
        return UserRepositoryImpl(userDao)
    }

    @Provides
    fun provideCountryImplementation(@CountryServiceApi countryService: CountryService, @LocationServiceApi locationServiceApi: CountryService): CountryImpl {
        return CountryImpl(countryService, locationServiceApi)
    }

    @Provides
    fun provideBookImplementation(bookService: BookService, userDao: UserDao): BookImpl {
        return BookImpl(bookService, userDao)
    }
}