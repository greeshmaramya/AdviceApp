package com.greesh.advice.api.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Slip (
  @Json(name = "id")val id: Long,
  @Json(name="advice")val advice: String
)