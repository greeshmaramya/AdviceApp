package com.greesh.advice.api

import com.greesh.advice.api.model.AdviceModel
import io.reactivex.Single
import retrofit2.http.GET

interface ApiInterface {
  @GET("/advice")
  fun getAdvice() : Single<AdviceModel>
}