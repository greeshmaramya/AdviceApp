package com.greesh.advice.roomDb

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [AdviceEntity::class],version = 1)
abstract class AdviceDataBase : RoomDatabase() {
  abstract fun adviceDao():AdviceDao
}