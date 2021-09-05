package com.example.yellow.network

import com.example.yellow.InternetConnectionChecker
import com.example.yellow.mapper.JogDataMapper
import com.example.yellow.mapper.JogUpdateBodyMapper
import com.example.yellow.repo.JogRepository
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideCallFactory(httpLoggingInterceptor: HttpLoggingInterceptor): Call.Factory {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder().build()
    }

    @Provides
    @Singleton
    fun provideMoshiConverterFactory(): MoshiConverterFactory {
        return MoshiConverterFactory.create()
    }

    @Provides
    @Singleton
    fun provideRxJava3CallAdapterFactory(): RxJava3CallAdapterFactory {
        return RxJava3CallAdapterFactory.create()
    }

    @Provides
    @Singleton
    fun provideBaseUrl(): String = "https://jogtracker.herokuapp.com/api/v1/"

    @Provides
    @Singleton
    fun provideRetrofit(
        callFactory: Call.Factory,
        moshiConverterFactory: MoshiConverterFactory,
        rxJava3CallAdapterFactory: RxJava3CallAdapterFactory,
        baseUrl: String
    ): Retrofit {
        return Retrofit.Builder()
            .callFactory(callFactory)
            .addCallAdapterFactory(rxJava3CallAdapterFactory)
            .addConverterFactory(moshiConverterFactory)
            .baseUrl(baseUrl)
            .build()
    }

    @Provides
    @Singleton
    fun provideJogService(retrofit: Retrofit) : JogService {
        return retrofit.create(JogService::class.java)
    }

    @Provides
    @Singleton
    fun provideJogRepository(jogService: JogService) : JogRepository {
        return JogRepository(jogService)
    }

    @Provides
    @Singleton
    fun provideJogDataMapper(): JogDataMapper = JogDataMapper()

    @Provides
    @Singleton
    fun provideJogUpdateBodyMapper(): JogUpdateBodyMapper = JogUpdateBodyMapper()

    @Provides
    @Singleton
    fun internetConnectionChecker(): InternetConnectionChecker = InternetConnectionChecker()
}
