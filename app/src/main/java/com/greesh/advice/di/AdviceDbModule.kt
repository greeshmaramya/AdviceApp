package com.greesh.advice.di

import android.app.Application
import androidx.room.Room
import com.greesh.advice.roomDb.AdviceDao
import com.greesh.advice.roomDb.AdviceDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AdviceDbModule {
  @Singleton
  @Provides
  fun getDao(context: Application): AdviceDao {
    return Room.databaseBuilder(context, AdviceDataBase::class.java, AdviceDataBase::class.java.name).build().adviceDao()
  }
}