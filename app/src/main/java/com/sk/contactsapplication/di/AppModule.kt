package com.sk.contactsapplication.di

import android.content.Context
import android.net.ConnectivityManager
import com.google.gson.Gson
import com.sk.contactsapplication.BuildConfig
import com.sk.contactsapplication.data.error.ErrorHandler
import com.sk.contactsapplication.data.error.ErrorHandlerImpl
import com.sk.contactsapplication.data.network.ApiService
import com.sk.contactsapplication.data.repository.Repository
import com.sk.contactsapplication.data.repository.RepositoryImpl
import com.sk.contactsapplication.helpers.Constants
import com.sk.contactsapplication.helpers.NetInfo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        }
        return OkHttpClient().newBuilder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun providesNewBeApiService(okHttpClient: OkHttpClient): ApiService {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun providesRepositoryInstance(
        newBeService: ApiService,
        errorHandler: ErrorHandler
    ): Repository {
        return RepositoryImpl(newBeService, errorHandler)
    }


    @Singleton
    @Provides
    fun providesConnectivityManager(@ApplicationContext context: Context): ConnectivityManager {
        val connectivityService = context.getSystemService(Context.CONNECTIVITY_SERVICE)
        return connectivityService as ConnectivityManager
    }

    @Singleton
    @Provides
    fun providesNetInfo(connectivityManager: ConnectivityManager) = NetInfo(connectivityManager)

    @Singleton
    @Provides
    fun providesErrorHandler(gson: Gson): ErrorHandler {
        return ErrorHandlerImpl(gson)
    }

    @Singleton
    @Provides
    fun providesGson() = Gson()
}