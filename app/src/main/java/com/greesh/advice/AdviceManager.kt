package com.greesh.advice

import com.greesh.advice.api.ApiInterface
import com.greesh.advice.api.model.AdviceModel
import com.greesh.advice.roomDb.AdviceDao
import com.greesh.advice.roomDb.AdviceEntity
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AdviceManager @Inject constructor(private val apiInterface: ApiInterface, private val dao: AdviceDao) {

  fun getAdvice(): Single<AdviceModel> {
    return apiInterface.getAdvice()
  }

  fun storeAdvice(adviceModel: AdviceModel) {
    dao.insert(AdviceEntity(adviceModel.slip.id, adviceModel.slip.advice))
      .subscribeOn(Schedulers.io()).subscribe()
  }

  fun getSavedAdvices(): Maybe<List<AdviceEntity>> {
    return dao.getAll()
  }
}
