package com.greesh.advice

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.greesh.advice.api.model.AdviceModel
import com.greesh.advice.roomDb.AdviceEntity
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class AdviceViewModel @Inject constructor(private val manager: AdviceManager) : ViewModel() {

  private val compositeDisposable = CompositeDisposable()

  private val state = BehaviorSubject.create<AdviceState>()
  fun state(): Observable<AdviceState> = state.hide()

  fun getAdvice() {
    compositeDisposable.add(
      manager.getAdvice().doOnSubscribe {
        state.onNext(AdviceState.Loading)
      }.subscribe(
        {
          state.onNext(AdviceState.Advice(it))
        },
        {
          state.onNext(AdviceState.Error)
        }
      )
    )
  }

  fun storeAdvice(advice: AdviceModel) {
    manager.storeAdvice(advice)
  }

  fun getStoredAdvice() {
    compositeDisposable.add(
      manager.getSavedAdvices().doOnSubscribe {
        state.onNext(AdviceState.Loading)
      }.subscribeOn(Schedulers.io()).subscribe(
        {
          state.onNext(AdviceState.Advices(it))
        },
        {
          state.onNext(AdviceState.Error)
        }
      )
    )
  }
}

class ViewModelFactory @Inject constructor(val manager: AdviceManager) {

  private val factory = object : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
      return AdviceViewModel(manager) as T
    }
  }

  fun getViewModel(fragmentActivity: FragmentActivity): AdviceViewModel {
    return ViewModelProvider(fragmentActivity, factory).get(AdviceViewModel::class.java)
  }
}

sealed class AdviceState() {
  object Loading : AdviceState()
  object Error : AdviceState()
  data class Advice(val advice: AdviceModel) : AdviceState()
  data class Advices(val advices: List<AdviceEntity>) : AdviceState()
}