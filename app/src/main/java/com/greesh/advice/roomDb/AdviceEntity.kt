package com.greesh.advice.roomDb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "advice_table")
data class AdviceEntity(
  @PrimaryKey
  val id: Long,
  val advice: String
)