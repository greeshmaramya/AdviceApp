package com.greesh.advice.roomDb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Maybe

@Dao
interface AdviceDao {
  @Query("SELECT * FROM advice_table")
  fun getAll(): Maybe<List<AdviceEntity>>

  @Query("SELECT * FROM  advice_table WHERE id = :id")
  fun getById(id: Long): Maybe<AdviceEntity>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insert(vararg advice: AdviceEntity): Completable

  @Delete
  fun delete(adviceEntity: AdviceEntity)
}