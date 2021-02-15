package com.greesh.advice.di

import com.greesh.advice.api.ApiInterface
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object AdviceModule {
  @Provides
  fun adviceApi(): ApiInterface {
    return Retrofit.Builder().baseUrl("https://api.adviceslip.com/").client(getOkHttpClient())
      .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder().build()))
      .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io())).build()
      .create(ApiInterface::class.java)
  }
}

internal var builder: OkHttpClient.Builder = OkHttpClient().newBuilder()
fun getOkHttpClient(): OkHttpClient {
  return builder
    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS))
    .build()
}