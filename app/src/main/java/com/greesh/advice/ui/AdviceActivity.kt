package com.greesh.advice.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.greesh.advice.AdviceState
import com.greesh.advice.AdviceViewModel
import com.greesh.advice.R
import com.greesh.advice.ViewModelFactory
import com.greesh.advice.api.model.AdviceModel
import com.greesh.advice.databinding.ActivityAdviceBinding
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@AndroidEntryPoint
class AdviceActivity : AppCompatActivity() {

  @Inject
  lateinit var viewModelFactory: ViewModelFactory

  private var adapter = AdviceAdapter()

  private lateinit var viewModel: AdviceViewModel

  private lateinit var binding: ActivityAdviceBinding
  private val compositeDisposable = CompositeDisposable()
  private lateinit var advice: AdviceModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityAdviceBinding.inflate(layoutInflater)
    setContentView(binding.root)

    viewModel = viewModelFactory.getViewModel(this)
    viewModel.getAdvice()

    binding.recyclerView.layoutManager = LinearLayoutManager(this)
    binding.recyclerView.adapter = adapter

    compositeDisposable.add(
      viewModel.state().observeOn(AndroidSchedulers.mainThread()).subscribe { showAdviceState(it) })

    binding.oneMore.setOnClickListener { viewModel.getAdvice() }
    binding.save.setOnClickListener {
      viewModel.storeAdvice(advice)
    }

    binding.savedList.setOnClickListener { viewModel.getStoredAdvice() }
  }

  private fun showAdviceState(state: AdviceState) {
    when (state) {
      is AdviceState.Loading -> {
        binding.advice.isVisible = true
        binding.title.isVisible = true
        binding.recyclerView.isVisible = false
        binding.advice.text = getString(R.string.loading)
      }
      is AdviceState.Error -> {
        binding.advice.isVisible = true
        binding.title.isVisible = true
        binding.recyclerView.isVisible = false
        binding.advice.text = getString(R.string.error)
      }
      is AdviceState.Advice -> {
        binding.advice.isVisible = true
        binding.title.isVisible = true
        binding.recyclerView.isVisible = false
        binding.advice.text = state.advice.slip.advice
        advice = state.advice
      }
      is AdviceState.Advices -> binding.apply {
        advice.isVisible = false
        title.isVisible = false
        recyclerView.isVisible = true
        adapter.advices = state.advices
      }
    }
  }
}