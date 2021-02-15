package com.greesh.advice.api.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class AdviceModel (
  @Json(name = "slip")val slip: Slip
)