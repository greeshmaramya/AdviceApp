package com.greesh.advice.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.greesh.advice.databinding.AdviceItemBinding
import com.greesh.advice.roomDb.AdviceEntity

class AdviceAdapter : RecyclerView.Adapter<AdviceViewHolder>() {

  var advices: List<AdviceEntity> = listOf()
    set(value) {
      field = value
      notifyDataSetChanged()
    }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdviceViewHolder {
    return AdviceViewHolder(AdviceItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
  }

  override fun onBindViewHolder(holder: AdviceViewHolder, position: Int) {
    holder.bind(advices[position].advice,position)
  }

  override fun getItemCount(): Int {
    return advices.size
  }
}
class AdviceViewHolder(private val binding: AdviceItemBinding) : RecyclerView.ViewHolder(binding.root){

  fun bind(advice: String, position: Int){
    binding.advice.text = advice
    binding.adviceNumber.text = position.toString()
  }
}